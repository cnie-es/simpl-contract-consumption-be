package eu.europa.ec.simpl.contract.consumption.service.transferprocess;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import eu.europa.ec.simpl.contract.consumption.client.ConnectorAdapterClient;
import eu.europa.ec.simpl.contract.consumption.model.transfer.EdrDto;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcess;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcessId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferRequest;
import eu.europa.ec.simpl.data1.common.exception.RemoteServiceErrorException;
import eu.europa.ec.simpl.data1.common.util.AuthBearerUtil;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TransferProcessServiceTest {

    private static final String BEARER_TOKEN = AuthBearerUtil.toBearerString("testToken");

    private static final String TEST_TRANSFER_PROCESS_ID = "test-transfer-process-id";

    private TransferProcessService transferProcessService;

    @Mock
    private ConnectorAdapterClient connectorAdapterClient;

    @BeforeEach
    void setUpEach() {
        transferProcessService = new TransferProcessServiceImpl(connectorAdapterClient);
    }

    @Test
    void testStartTransfer() throws Exception {
        TransferProcessId expectedResult = TransferProcessId.builder().build();
        ResponseEntity<TransferProcessId> expectedResponse = new ResponseEntity<>(expectedResult, HttpStatus.OK);

        when(connectorAdapterClient.startTransfer(anyString(), any())).thenReturn(expectedResponse);

        TransferRequest request = TransferRequest.builder().build();

        TransferProcessId result = transferProcessService.startTransfer(BEARER_TOKEN, request);
        assertNotNull(result);
    }

    @Test
    void testStartTransferWithConnectorAdapterException() {
        when(connectorAdapterClient.startTransfer(anyString(), any())).thenThrow(FeignException.class);

        TransferRequest request = TransferRequest.builder().build();

        assertThrows(
                RemoteServiceErrorException.class, () -> transferProcessService.startTransfer(BEARER_TOKEN, request));
    }

    @Test
    void testGetTransferProcess() throws Exception {
        TransferProcess expectedResult = TransferProcess.builder().build();
        ResponseEntity<TransferProcess> expectedResponse = new ResponseEntity<>(expectedResult, HttpStatus.OK);

        when(connectorAdapterClient.getTransferStatus(anyString(), any())).thenReturn(expectedResponse);

        TransferProcess result = transferProcessService.getTransferProcess(BEARER_TOKEN, TEST_TRANSFER_PROCESS_ID);
        assertNotNull(result);
    }

    @Test
    void testGetTransferProcessWithConnectorAdapterException() {
        when(connectorAdapterClient.getTransferStatus(anyString(), any())).thenThrow(FeignException.class);

        assertThrows(
                RemoteServiceErrorException.class,
                () -> transferProcessService.getTransferProcess(BEARER_TOKEN, TEST_TRANSFER_PROCESS_ID));
    }

    @Test
    void testGetTransferEdr() {
        EdrDto expectedResult = EdrDto.builder()
                .baseUrl("https://consumer-data-plane.example.com/proxy")
                .authorizationHeaderValue("eyJhbGciOiJSUzI1NiJ9...")
                .build();
        ResponseEntity<EdrDto> expectedResponse = new ResponseEntity<>(expectedResult, HttpStatus.OK);

        when(connectorAdapterClient.getTransferEdr(anyString(), anyString())).thenReturn(expectedResponse);

        EdrDto result = transferProcessService.getTransferEdr(BEARER_TOKEN, TEST_TRANSFER_PROCESS_ID);
        assertNotNull(result);
    }

    @Test
    void testGetTransferEdrWithConnectorAdapterException() {
        when(connectorAdapterClient.getTransferEdr(anyString(), anyString())).thenThrow(FeignException.class);

        assertThrows(
                RemoteServiceErrorException.class,
                () -> transferProcessService.getTransferEdr(BEARER_TOKEN, TEST_TRANSFER_PROCESS_ID));
    }
}
