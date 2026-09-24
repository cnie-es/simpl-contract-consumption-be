package eu.europa.ec.simpl.contract.consumption.service.contractnegotiation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import eu.europa.ec.simpl.contract.consumption.client.ConnectorAdapterClient;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.catalog.CatalogSearchResult;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiation;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationRequest;
import eu.europa.ec.simpl.data1.common.exception.RemoteServiceErrorException;
import eu.europa.ec.simpl.data1.common.util.AuthBearerUtil;
import feign.FeignException;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ContractNegotiationServiceTest {

    private static final String BEARER_TOKEN = AuthBearerUtil.toBearerString("testToken");

    private static final String TEST_ASSET_ID = "test-asset-id";
    private static final String TEST_CONTRACT_NEGOTIATION_ID = "test-contract-negotiation-id";

    private ContractNegotiationService contractNegotiationService;

    @Mock
    private ConnectorAdapterClient connectorAdapterClient;

    @BeforeEach
    void setUpEach() {
        contractNegotiationService = new ContractNegotiationServiceImpl(connectorAdapterClient);
    }

    @Test
    void testGetCatalog() throws Exception {
        CatalogSearchResult expectedResult = CatalogSearchResult.builder().build();
        ResponseEntity<CatalogSearchResult> expectedResponse = new ResponseEntity<>(expectedResult, HttpStatus.OK);

        when(connectorAdapterClient.searchCatalogOffers(anyString(), any())).thenReturn(expectedResponse);

        ContractNegotiationRequest request =
                ContractNegotiationRequest.builder().assetId(TEST_ASSET_ID).build();

        CatalogSearchResult result = contractNegotiationService.getCatalog(BEARER_TOKEN, request);
        assertNotNull(result);
    }

    @Test
    void testGetCatalogWithConnectorAdapterException() {
        when(connectorAdapterClient.searchCatalogOffers(anyString(), any())).thenThrow(FeignException.class);

        ContractNegotiationRequest request =
                ContractNegotiationRequest.builder().assetId(TEST_ASSET_ID).build();

        assertThrows(
                RemoteServiceErrorException.class, () -> contractNegotiationService.getCatalog(BEARER_TOKEN, request));
    }

    @Test
    void testInitiateContractNegotiation() throws Exception {
        ContractNegotiationId expectedResult = ContractNegotiationId.builder().build();
        ResponseEntity<ContractNegotiationId> expectedResponse = new ResponseEntity<>(expectedResult, HttpStatus.OK);

        when(connectorAdapterClient.startContractNegotiation(anyString(), any()))
                .thenReturn(expectedResponse);

        ContractNegotiationRequest request =
                ContractNegotiationRequest.builder().assetId(TEST_ASSET_ID).build();

        ContractNegotiationId result = contractNegotiationService.initiateContractNegotiation(BEARER_TOKEN, request);
        assertNotNull(result);
    }

    @Test
    void testInitiateContractNegotiationWithConnectorAdapterException() {
        when(connectorAdapterClient.startContractNegotiation(anyString(), any()))
                .thenThrow(FeignException.class);

        ContractNegotiationRequest request =
                ContractNegotiationRequest.builder().assetId(TEST_ASSET_ID).build();

        assertThrows(
                RemoteServiceErrorException.class,
                () -> contractNegotiationService.initiateContractNegotiation(BEARER_TOKEN, request));
    }

    @Test
    void testGetContractNegotiationStatus() throws Exception {
        ContractNegotiation expectedResult = ContractNegotiation.builder().build();
        ResponseEntity<ContractNegotiation> expectedResponse = new ResponseEntity<>(expectedResult, HttpStatus.OK);

        when(connectorAdapterClient.getContractNegotiationStatus(anyString(), any()))
                .thenReturn(expectedResponse);

        ContractNegotiation result =
                contractNegotiationService.getContractNegotiationStatus(BEARER_TOKEN, TEST_CONTRACT_NEGOTIATION_ID);
        assertNotNull(result);
    }

    @Test
    void testGetContractNegotiationStatusWithConnectorAdapterException() throws IOException {
        when(connectorAdapterClient.getContractNegotiationStatus(anyString(), any()))
                .thenThrow(FeignException.class);

        assertThrows(
                RemoteServiceErrorException.class,
                () -> contractNegotiationService.getContractNegotiationStatus(
                        BEARER_TOKEN, TEST_CONTRACT_NEGOTIATION_ID));
    }
}
