package eu.europa.ec.simpl.contract.consumption.service.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europa.ec.simpl.contract.consumption.service.resourceaddress.ResourceAddressService;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferRequest;
import eu.europa.ec.simpl.data1.common.client.validation.ValidationClientBuilder;
import eu.europa.ec.simpl.data1.common.custom.CustomMultipartFile;
import eu.europa.ec.simpl.data1.common.enumeration.CommonErrorType;
import eu.europa.ec.simpl.data1.common.exception.ResourceAddressValidationException;
import eu.europa.ec.simpl.data1.common.properties.ValidationProperties;
import eu.europa.ec.simpl.data1.common.util.JsonUtil;
import eu.europa.ec.simpl.data1.common.util.RemoteServiceUtil;
import feign.FeignException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private static final String VALIDATION_DISABLED_MESSAGE = "The validation service call is disabled";

    private final ValidationClientBuilder validationClient;
    private final ValidationProperties validationProperties;
    private final ResourceAddressService resourceAddressService;
    private final ObjectMapper objectMapper;

    @Override
    public void validateResourceAddress(TransferRequest transferRequest, String templateId) throws IOException {
        if (!validationProperties.isEnabled()) {
            log.warn(VALIDATION_DISABLED_MESSAGE);
            return;
        }

        if (StringUtils.isBlank(templateId)) {
            log.error("validateResourceAddress() faild cause templateId not valorized");
            throw new ResourceAddressValidationException("templateId not valorized");
        }

        try {
            String jsonSchemaString = resourceAddressService.getDestinationAddressSchema(templateId);

            Object dataDestination = transferRequest.getDataDestination();
            String jsonValueString = objectMapper.writeValueAsString(dataDestination);

            MultipartFile jsonSchemaFile = CustomMultipartFile.convertStringWriterToMultipartFile(
                    jsonSchemaString, "jsonSchemaString.json", StandardCharsets.UTF_8);
            MultipartFile jsonValueFile = CustomMultipartFile.convertStringWriterToMultipartFile(
                    jsonValueString, "jsonValueString.json", StandardCharsets.UTF_8);

            ResponseEntity<String> response = validationClient.validateResourceAddress(jsonSchemaFile, jsonValueFile);

            handleValidationServiceResponseForResourceAddress(response);

        } catch (FeignException e) {
            log.error("validateResourceAddress() failed cause {}", e.getMessage());
            throw RemoteServiceUtil.toRemoteServiceErrorException(CommonErrorType.REMOTE_VALIDATION_ERROR, e);
        }
    }

    private void handleValidationServiceResponseForResourceAddress(ResponseEntity<String> response) {
        if (response != null) {
            String responseString = response.getBody();
            if (responseString != null) {
                handleValidationServiceResponseForResourceAddress(responseString);
            }
        }
    }

    private void handleValidationServiceResponseForResourceAddress(String responseString) {
        log.info("handleValidationServiceResponseForResourceAddress() for response '{}'", responseString);

        JsonNode responseJsonNode = JsonUtil.createJsonNodeFromRemoteServiceResponse(
                responseString, objectMapper, CommonErrorType.REMOTE_VALIDATION_ERROR);
        if (responseJsonNode.has("valid")) {
            JsonNode validNode = responseJsonNode.get("valid");
            log.debug("handleValidationServiceResponseForResourceAddress(): response validNode {}", validNode);

            if (!validNode.asBoolean()) {
                String problemIssue = "";
                if (responseJsonNode.has("errors")) {
                    JsonNode errorsNode = responseJsonNode.get("errors");
                    log.debug(
                            "handleValidationServiceResponseForResourceAddress(): found response errorsNode {}",
                            errorsNode);
                    problemIssue = errorsNode.toString();
                }
                throw new ResourceAddressValidationException(problemIssue);
            }
        }
    }
}
