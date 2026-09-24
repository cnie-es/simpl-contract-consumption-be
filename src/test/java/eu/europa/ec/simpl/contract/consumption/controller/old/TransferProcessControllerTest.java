package eu.europa.ec.simpl.contract.consumption.controller.old;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europa.ec.simpl.contract.consumption.service.transferprocess.TransferProcessService;
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

@SuppressWarnings("removal")
@ActiveProfiles("test")
@WebMvcTest(TransferProcessController.class)
@TestPropertySource(properties = "web.mvc.bearer-token.required=false")
class TransferProcessControllerTest {

    private static final String PATH = "/transfer";

    private static final String TEST_TRANSFER_ID = "test-transfer-id";
    private static final String TEST_CONTRACT_ID = "test-contract-id";
    private static final String TEST_TEMPLATE_ID = "1";

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
        mockMvc.perform(post(PATH + "/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTransferStatus() throws Exception {
        TransferProcess expectedResult =
                TransferProcess.builder().contractId(TEST_CONTRACT_ID).build();
        when(transferProcessService.getTransferProcess(any(), any())).thenReturn(expectedResult);

        mockMvc.perform(get(PATH + "/status/" + TEST_TRANSFER_ID)).andExpect(status().isOk());
    }
}
