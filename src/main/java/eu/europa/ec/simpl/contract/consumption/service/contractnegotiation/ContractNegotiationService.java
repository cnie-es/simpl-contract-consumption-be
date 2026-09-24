package eu.europa.ec.simpl.contract.consumption.service.contractnegotiation;

import eu.europa.ec.simpl.data1.common.adapter.connector.model.catalog.CatalogSearchResult;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiation;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationRequest;

public interface ContractNegotiationService {

    CatalogSearchResult getCatalog(String bearerToken, ContractNegotiationRequest request);

    ContractNegotiationId initiateContractNegotiation(String bearerToken, ContractNegotiationRequest contractRequest);

    ContractNegotiation getContractNegotiationStatus(String bearerToken, String contractNegotiationId);
}
