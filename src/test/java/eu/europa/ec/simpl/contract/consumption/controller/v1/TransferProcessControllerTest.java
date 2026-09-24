package eu.europa.ec.simpl.contract.consumption.controller.v1;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europa.ec.simpl.contract.consumption.constant.RequestMappingV1;
import eu.europa.ec.simpl.contract.consumption.model.transfer.EdrDto;
import eu.europa.ec.simpl.contract.consumption.service.transferprocess.TransferProcessService;
import eu.europa.ec.simpl.contract.consumption.service.validation.ValidationService;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcess;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcessId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(TransferProcessController.class)
@TestPropertySource(properties = "web.mvc.bearer-token.required=false")
class TransferProcessControllerTest {

    private static final String PATH = RequestMappingV1.TRANSFER_PROCESS;

    private static final String TEST_TRANSFER_ID = "test-transfer-id";
    private static final String TEST_CONTRACT_ID = "test-contract-id";
    private static final String TEST_TEMPLATE_ID = "test-template-id";

    private static final String TEST_PROVIDER_ENDPOINT = "http://provider-endpoint";

    private static final JsonNode TEST_DATA_DESTINATION = new ObjectMapper()
            .createObjectNode()
            .put("type", "S3")
            .put("bucketName", "test-bucket")
            .put("keyName", "test-file.json");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransferProcessService transferProcessService;

    @MockitoBean
    private ValidationService validationService;

    @Test
    void testStartTransfer() throws Exception {
        TransferProcessId expectedResult =
                TransferProcessId.builder().transferId(TEST_TRANSFER_ID).build();

        when(transferProcessService.startTransfer(any(), any())).thenReturn(expectedResult);

        TransferRequest request = TransferRequest.builder()
                .contractId(TEST_CONTRACT_ID)
                .providerEndpoint(TEST_PROVIDER_ENDPOINT)
                .templateId(TEST_TEMPLATE_ID)
                .dataDestination(TEST_DATA_DESTINATION)
                .build();

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTransferStatus() throws Exception {
        TransferProcess expectedResult =
                TransferProcess.builder().contractId(TEST_CONTRACT_ID).build();
        when(transferProcessService.getTransferProcess(any(), any())).thenReturn(expectedResult);

        mockMvc.perform(get(PATH + "/" + TEST_TRANSFER_ID)).andExpect(status().isOk());
    }

    @Test
    void testGetTransferEdr() throws Exception {
        EdrDto expectedResult = EdrDto.builder()
                .baseUrl("https://consumer-data-plane.example.com/proxy")
                .authorizationHeaderValue("eyJhbGciOiJSUzI1NiJ9...")
                .build();
        when(transferProcessService.getTransferEdr(any(), any())).thenReturn(expectedResult);

        mockMvc.perform(get(PATH + "/" + TEST_TRANSFER_ID + "/edr")).andExpect(status().isOk());
    }
}
