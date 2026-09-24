package eu.europa.ec.simpl.contract.consumption.controller.v1;

import eu.europa.ec.simpl.contract.consumption.service.contractnegotiation.ContractNegotiationService;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.catalog.CatalogSearchResult;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiation;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationRequest;
import eu.europa.ec.simpl.data1.common.controller.AbstractController;
import eu.europa.ec.simpl.data1.common.logging.LogRequest;
import eu.europa.ec.simpl.data1.common.util.AuthBearerUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController("contractNegotiationControllerV1")
@Log4j2
@RequiredArgsConstructor
public class ContractNegotiationControllerImpl extends AbstractController implements ContractNegotiationController {

    private final ContractNegotiationService contractNegotiationService;

    @LogRequest
    @Override
    public ResponseEntity<CatalogSearchResult> searchCatalogAssets(
            ContractNegotiationRequest contractNegotiationRequest, HttpServletRequest httpRequest) {
        String tier1BearerToken = AuthBearerUtil.getBearerValue(httpRequest);

        log.debug("searchCatalogAssets(): invoking contractNegotiationService.getCatalog()");
        CatalogSearchResult catalogSearchResult =
                contractNegotiationService.getCatalog(tier1BearerToken, contractNegotiationRequest);

        log.debug("searchCatalogAssets(): returning response OK");
        return ResponseEntity.ok(catalogSearchResult);
    }

    @LogRequest
    @Override
    public ResponseEntity<ContractNegotiationId> startContractNegotiation(
            ContractNegotiationRequest contractNegotiationRequest, HttpServletRequest httpRequest) {
        String tier1BearerToken = AuthBearerUtil.getBearerValue(httpRequest);

        log.debug("startContractNegotiation(): invoking contractNegotiationService.initiateContractNegotiation()");
        ContractNegotiationId negotiationIdResponse =
                contractNegotiationService.initiateContractNegotiation(tier1BearerToken, contractNegotiationRequest);

        log.debug("startContractNegotiation(): returning response OK");
        return ResponseEntity.ok(negotiationIdResponse);
    }

    @LogRequest
    @Override
    public ResponseEntity<ContractNegotiation> getContractNegotiationStatus(
            String contractNegotiationId, HttpServletRequest httpRequest) {
        String tier1BearerToken = AuthBearerUtil.getBearerValue(httpRequest);

        log.debug("getContractNegotiationStatus(): invoking contractNegotiationService.getContractNegotiationStatus()");
        ContractNegotiation contractNegotiation =
                contractNegotiationService.getContractNegotiationStatus(tier1BearerToken, contractNegotiationId);

        log.debug("getContractNegotiationStatus(): returning response OK");
        return ResponseEntity.ok(contractNegotiation);
    }
}
