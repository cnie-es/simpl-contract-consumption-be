package eu.europa.ec.simpl.contract.consumption.service.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europa.ec.simpl.contract.consumption.service.resourceaddress.ResourceAddressService;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferRequest;
import eu.europa.ec.simpl.data1.common.client.validation.ValidationClientBuilder;
import eu.europa.ec.simpl.data1.common.exception.RemoteServiceErrorException;
import eu.europa.ec.simpl.data1.common.exception.ResourceAddressValidationException;
import eu.europa.ec.simpl.data1.common.properties.ValidationProperties;
import feign.FeignException;
import feign.Request;
import feign.RetryableException;
import java.io.IOException;
import java.net.URI;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    private static final String TEST_CONTRACT_ID = "test-contract-id";
    private static final String TEST_TEMPLATE_ID = "test-template-id";

    @Mock
    private ValidationClientBuilder validationClient;

    @Mock
    private ValidationProperties validationProperties;

    @Mock
    private ResourceAddressService resourceAddressService;

    @Mock
    private static Request feignRequest;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ValidationServiceImpl validationService;

    @BeforeEach
    public void setUp() {
        when(validationProperties.isEnabled()).thenReturn(true);
        lenient()
                .when(validationProperties.getResourceAddressesUri())
                .thenReturn(URI.create("http://example.com/resource-address"));
        validationService =
                new ValidationServiceImpl(validationClient, validationProperties, resourceAddressService, objectMapper);
    }

    @BeforeAll
    static void staticSetUp() {
        feignRequest = Mockito.mock(Request.class);
    }

    private static Stream<Arguments> GenerateResourceAddressSuccessAndErrors() {
        TransferRequest transferRequest = TransferRequest.builder()
                .contractId(TEST_CONTRACT_ID)
                .templateId(TEST_TEMPLATE_ID)
                .build();
        String validationApiResponseSuccess = "{\"valid\":true}";
        String validationApiResponseErrors =
                "{\"valid\":false,\"errors\":[{\"pointer\":\"name\",\"message\":\"required property 'name' not found\",\"type\":\"required\"},{\"pointer\":\"baseUrl\",\"message\":\"required property 'baseUrl' not found\",\"type\":\"required\"},{\"pointer\":\"proxyPath\",\"message\":\"required property 'proxyPath' not found\",\"type\":\"required\"}]}";

        return Stream.of(
                Arguments.of(transferRequest, validationApiResponseSuccess, null),
                Arguments.of(transferRequest, validationApiResponseErrors, ResourceAddressValidationException.class));
    }

    @ParameterizedTest
    @MethodSource("GenerateResourceAddressSuccessAndErrors")
    <T extends Throwable> void testValidateResourceAddressSuccessAndErrors(
            TransferRequest transferRequest, String validationApiResponse, Class<T> exceptionClass) throws IOException {

        ResponseEntity<String> responseEntity = new ResponseEntity<>(validationApiResponse, HttpStatus.OK);

        when(validationClient.validateResourceAddress(any(MultipartFile.class), any(MultipartFile.class)))
                .thenReturn(responseEntity);

        if (exceptionClass != null) {
            assertThrows(
                    exceptionClass,
                    () -> {
                        validationService.validateResourceAddress(transferRequest, "1");
                    },
                    "Expected exception of type " + exceptionClass.getName()
                            + " was not thrown during the validation process.");
        } else {
            validationService.validateResourceAddress(transferRequest, "1");
        }

        verify(validationClient).validateResourceAddress(any(MultipartFile.class), any(MultipartFile.class));
    }

    private static Stream<Arguments> GenerateResourceAddressFeignExceptionParameters() {
        return Stream.of(
                Arguments.of(new FeignException.BadRequest("Test Feign exception", feignRequest, null, null)),
                Arguments.of(new RetryableException(
                        HttpStatus.BAD_REQUEST.value(), "Test Retryable exception", null, 0L, feignRequest)));
    }

    @ParameterizedTest
    @MethodSource("GenerateResourceAddressFeignExceptionParameters")
    void testValidateResourceAddressFeignException(Throwable exception) {

        when(validationClient.validateResourceAddress(any(), any())).thenThrow(exception);

        TransferRequest transferRequest = TransferRequest.builder()
                .contractId(TEST_CONTRACT_ID)
                .templateId(TEST_TEMPLATE_ID)
                .build();

        // Call the method and assert that a RemoteServiceErrorException is thrown
        assertThrows(
                RemoteServiceErrorException.class,
                () -> validationService.validateResourceAddress(transferRequest, "1"),
                "Expected exception of type " + RemoteServiceErrorException.class.getName()
                        + " was not thrown during the validation process.");
    }

    @Test
    void testValidateResourceAddressIOException() throws IOException {

        when(resourceAddressService.getDestinationAddressSchema("1")).thenThrow(new IOException("Test IOException"));

        TransferRequest transferRequest = TransferRequest.builder()
                .contractId(TEST_CONTRACT_ID)
                .templateId(TEST_TEMPLATE_ID)
                .build();

        // Call the method and assert that a IOException is thrown
        assertThrows(
                IOException.class,
                () -> validationService.validateResourceAddress(transferRequest, "1"),
                "Expected exception of type " + IOException.class.getName()
                        + " was not thrown during the validation process.");
    }
}
