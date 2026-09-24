package eu.europa.ec.simpl.contract.consumption.controller.old;

import eu.europa.ec.simpl.contract.consumption.service.contractnegotiation.ContractNegotiationService;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.catalog.CatalogSearchResult;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiation;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationRequest;
import eu.europa.ec.simpl.data1.common.controller.AbstractController;
import eu.europa.ec.simpl.data1.common.logging.LogRequest;
import eu.europa.ec.simpl.data1.common.util.AuthBearerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @deprecated(will be replaced by the v1 ContractNegotiationController)
 */
@RestController
@RequestMapping("/contract-negotiation")
@Log4j2
@Tag(name = "Contract Negotiation")
@RequiredArgsConstructor
@Deprecated(since = "latest", forRemoval = true)
public class ContractNegotiationController extends AbstractController {

    private final ContractNegotiationService contractNegotiationService;

    /**
     * Initiates a contract negotiation for a given asset and contract definition.
     *
     * @param contractRequest contains the asset and contract definition for which we check catalog and start negotiation
     * @return the contract negotiation details
     */
    @Operation(summary = "Get provider catalog")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                        }),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            })
    @PostMapping("/catalog")
    @LogRequest
    public ResponseEntity<CatalogSearchResult> searchCatalogOffers(
            @Valid @RequestBody ContractNegotiationRequest contractNegotiationRequest, HttpServletRequest httpRequest) {
        String tier1BearerToken = AuthBearerUtil.getBearerValue(httpRequest);

        log.debug(
                "searchCatalogOffers(): invoking contractNegotiationService.getCatalog() with {}",
                contractNegotiationRequest);
        CatalogSearchResult catalogSearchResult =
                contractNegotiationService.getCatalog(tier1BearerToken, contractNegotiationRequest);

        log.debug("searchCatalogOffers(): returning response OK with {}", catalogSearchResult);
        return ResponseEntity.ok(catalogSearchResult);
    }

    @Operation(summary = "Initiate contract negotiation")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                        }),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            })
    @PostMapping("/negotiate")
    @LogRequest
    public ResponseEntity<ContractNegotiationId> startContractNegotiation(
            @Valid @RequestBody ContractNegotiationRequest contractNegotiationRequest, HttpServletRequest httpRequest) {
        String tier1BearerToken = AuthBearerUtil.getBearerValue(httpRequest);

        log.debug("startContractNegotiation() for {}", contractNegotiationRequest);
        ContractNegotiationId negotiationIdResponse =
                contractNegotiationService.initiateContractNegotiation(tier1BearerToken, contractNegotiationRequest);

        log.debug("startContractNegotiation(): returning response OK with {}", negotiationIdResponse);
        return ResponseEntity.ok(negotiationIdResponse);
    }

    @Operation(summary = "Get contract negotiation status")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                        }),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            })
    @GetMapping("/negotiate/{id}")
    @LogRequest
    public ResponseEntity<ContractNegotiation> getContractNegotiationStatus(
            @Parameter(description = "The ID of the contract negotiation to retrieve the status for", required = true)
                    @PathVariable("id")
                    @NotBlank
                    String contractNegotiationId,
            HttpServletRequest httpRequest) {
        String tier1BearerToken = AuthBearerUtil.getBearerValue(httpRequest);

        log.debug(
                "getContractNegotiationStatus(): invoking contractNegotiationService.getContractNegotiationStatus() with  contractNegotiationId {}",
                contractNegotiationId);
        ContractNegotiation contractNegotiation =
                contractNegotiationService.getContractNegotiationStatus(tier1BearerToken, contractNegotiationId);

        log.debug("getContractNegotiationStatus(): returning response OK with {}", contractNegotiation);
        return ResponseEntity.ok(contractNegotiation);
    }
}
