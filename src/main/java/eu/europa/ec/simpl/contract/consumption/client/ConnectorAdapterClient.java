package eu.europa.ec.simpl.contract.consumption.client;

import eu.europa.ec.simpl.data1.common.adapter.connector.model.catalog.CatalogSearchResult;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiation;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationRequest;
import eu.europa.ec.simpl.contract.consumption.model.transfer.EdrDto;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcess;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcessId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "connectorAdapterClient", url = "${connector-adapter.service.url}")
public interface ConnectorAdapterClient {

    String NEGOTIATION_PATH = "/v1";
    String TRANSFER_PATH = "/v1/transfers";

    @PostMapping(value = NEGOTIATION_PATH + "/connectorCatalog/assets", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CatalogSearchResult> searchCatalogOffers(
            @RequestHeader("Authorization") String bearerToken, @RequestBody ContractNegotiationRequest request);

    @PostMapping(value = NEGOTIATION_PATH + "/contracts", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ContractNegotiationId> startContractNegotiation(
            @RequestHeader("Authorization") String bearerToken, @RequestBody ContractNegotiationRequest request);

    @GetMapping(
            value = NEGOTIATION_PATH + "/contracts/{contractNegotiationId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ContractNegotiation> getContractNegotiationStatus(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("contractNegotiationId") String contractNegotiationId);

    @PostMapping(value = TRANSFER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TransferProcessId> startTransfer(
            @RequestHeader("Authorization") String bearerToken, @RequestBody TransferRequest request);

    @GetMapping(value = TRANSFER_PATH + "/{transferProcessId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TransferProcess> getTransferStatus(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("transferProcessId") String transferProcessId);

    @GetMapping(value = TRANSFER_PATH + "/{transferProcessId}/edr", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EdrDto> getTransferEdr(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("transferProcessId") String transferProcessId);
}
