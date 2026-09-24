package eu.europa.ec.simpl.contract.consumption.service.contractnegotiation;

import eu.europa.ec.simpl.contract.consumption.client.ConnectorAdapterClient;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.catalog.CatalogSearchResult;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiation;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationRequest;
import eu.europa.ec.simpl.data1.common.enumeration.CommonErrorType;
import eu.europa.ec.simpl.data1.common.util.AuthBearerUtil;
import eu.europa.ec.simpl.data1.common.util.RemoteServiceUtil;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ContractNegotiationServiceImpl implements ContractNegotiationService {

    private final ConnectorAdapterClient connectorAdapterClient;

    @Override
    public CatalogSearchResult getCatalog(String bearerToken, ContractNegotiationRequest request) {
        try {
            log.debug("getCatalog(): invoking connectorAdapterClient.searchCatalogOffers() for {}", request);
            ResponseEntity<CatalogSearchResult> response =
                    connectorAdapterClient.searchCatalogOffers(AuthBearerUtil.toBearerString(bearerToken), request);
            log.debug("getCatalog(): received response {}", response);
            return response.getBody();
        } catch (FeignException e) {
            log.error("getCatalog() failed cause", e);
            throw RemoteServiceUtil.toRemoteServiceErrorException(
                    CommonErrorType.REMOTE_CONNECTOR_ADAPTER_ERROR, "searchCatalogOffers operation failed", e);
        }
    }

    @Override
    public ContractNegotiationId initiateContractNegotiation(String bearerToken, ContractNegotiationRequest request) {
        try {
            log.debug(
                    "initiateContractNegotiation(): invoking connectorAdapterClient.startContractNegotiation() for {}",
                    request);
            ResponseEntity<ContractNegotiationId> response = connectorAdapterClient.startContractNegotiation(
                    AuthBearerUtil.toBearerString(bearerToken), request);
            log.debug("initiateContractNegotiation(): received response {}", response);
            return response.getBody();
        } catch (FeignException e) {
            log.error("initiateContractNegotiation() failed cause", e);
            throw RemoteServiceUtil.toRemoteServiceErrorException(
                    CommonErrorType.REMOTE_CONNECTOR_ADAPTER_ERROR, "startContractNegotiation operation failed", e);
        }
    }

    @Override
    public ContractNegotiation getContractNegotiationStatus(String bearerToken, String contractNegotiationId) {
        try {
            log.debug(
                    "getContractNegotiationStatus(): invoking connectorAdapterClient.getContractNegotiationStatus() for contractNegotiationId {}",
                    contractNegotiationId);
            ResponseEntity<ContractNegotiation> response = connectorAdapterClient.getContractNegotiationStatus(
                    AuthBearerUtil.toBearerString(bearerToken), contractNegotiationId);
            log.debug("getContractNegotiationStatus(): received response {}", response);
            return response.getBody();
        } catch (FeignException e) {
            log.error("getContractNegotiationStatus() failed cause", e);
            throw RemoteServiceUtil.toRemoteServiceErrorException(
                    CommonErrorType.REMOTE_CONNECTOR_ADAPTER_ERROR, "getContractNegotiationStatus operation failed", e);
        }
    }
}
