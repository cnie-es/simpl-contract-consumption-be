package eu.europa.ec.simpl.contract.consumption.controller.old;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europa.ec.simpl.contract.consumption.service.contractnegotiation.ContractNegotiationService;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.catalog.CatalogSearchResult;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.catalog.Dataset;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiation;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationRequest;
import java.util.List;
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
@WebMvcTest(ContractNegotiationController.class)
@TestPropertySource(properties = "web.mvc.bearer-token.required=false")
class ContractNegotiationControllerTest {

    private static final String PATH = "/contract-negotiation";

    // Test data constants
    private static final String TEST_ASSET_ID = "test-asset-id";
    private static final String TEST_CONTRACT_DEFINITION_ID = "test-contract-definition-id";
    private static final String TEST_PROVIDER_ENDPOINT = "http://provider-endpoint";

    private static final String TEST_PROVIDER_ID = "test-provider-id";
    private static final String TEST_OFFER_ID = "test-offer-id";

    private static final String TEST_NEGOTIATION_ID = "test-negotiation-id";
    private static final String TEST_STATE_REQUESTED = "REQUESTED";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ContractNegotiationService contractNegotiationService;

    @Test
    void testSearchCatalogOffers() throws Exception {
        ContractNegotiationRequest request = ContractNegotiationRequest.builder()
                .assetId(TEST_ASSET_ID)
                .contractDefinitionId(TEST_CONTRACT_DEFINITION_ID)
                .providerEndpoint(TEST_PROVIDER_ENDPOINT)
                .build();

        List<Dataset> datasets = List.of(Dataset.builder()
                .assetId(TEST_ASSET_ID)
                .offerId(TEST_OFFER_ID)
                .providerParticipantId(TEST_PROVIDER_ID)
                .build());
        CatalogSearchResult expectedResult =
                CatalogSearchResult.builder().datasets(datasets).build();

        when(contractNegotiationService.getCatalog(any(), any())).thenReturn(expectedResult);

        mockMvc.perform(post(PATH + "/catalog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testStartContractNegotiation() throws Exception {
        ContractNegotiationRequest request = ContractNegotiationRequest.builder()
                .assetId(TEST_ASSET_ID)
                .contractDefinitionId(TEST_CONTRACT_DEFINITION_ID)
                .providerEndpoint(TEST_PROVIDER_ENDPOINT)
                .build();

        ContractNegotiationId expectedResult = ContractNegotiationId.builder()
                .negotiationId(TEST_NEGOTIATION_ID)
                .build();

        when(contractNegotiationService.initiateContractNegotiation(any(), any()))
                .thenReturn(expectedResult);

        mockMvc.perform(post(PATH + "/negotiate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetContractNegotiationStatus() throws Exception {
        ContractNegotiation expectedResult =
                ContractNegotiation.builder().state(TEST_STATE_REQUESTED).build();

        when(contractNegotiationService.getContractNegotiationStatus(any(), any()))
                .thenReturn(expectedResult);

        mockMvc.perform(get(PATH + "/negotiate/" + TEST_NEGOTIATION_ID)).andExpect(status().isOk());
    }
}
