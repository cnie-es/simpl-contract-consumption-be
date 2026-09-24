package eu.europa.ec.simpl.contract.consumption.controller.v1;

import eu.europa.ec.simpl.contract.consumption.constant.RequestMappingV1;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.catalog.CatalogSearchResult;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiation;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.contract.ContractNegotiationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(RequestMappingV1.CONTRACT_NEGOTIATION)
@Tag(name = "Contract")
public interface ContractNegotiationController {

    @Operation(
            summary =
                    "Search for offers registered in the provider connector catalog in order to get parameters to initiate a contract negotiation",
            description =
                    "Allows a consumer to extract detailed information about a specific asset of interest from the provider connector's catalog. This provides the preliminary information necessary to initiate the contract negotiation phase.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CatalogSearchResult.class),
                                    examples =
                                            @ExampleObject(
                                                    name = "catalogResponse",
                                                    value =
                                                            """
                                        {
                                          "offers": [
                                            {
                                              "providerParticipantId": "provider-id-123456",
                                              "providerEndpointUrl": "https://provider-edc.example.com/api/v1/dsp",
                                              "assetId": "asset-123-xyz",
                                              "offerId": "offer-456-abc",
                                              "policy": {
                                                "id": "policy-789-def",
                                                "type": "USE",
                                                "policyConstraints": [
                                                  {
                                                    "leftOperand": "PURPOSE",
                                                    "operator": "EQ",
                                                    "rightOperand": "data-sharing"
                                                  }
                                                ]
                                              }
                                            },
                                            {
                                              "providerParticipantId": "provider-id-987654",
                                              "providerEndpointUrl": "https://another-provider.example.com/api/v1/dsp",
                                              "assetId": "asset-789-uvw",
                                              "offerId": "offer-321-rst",
                                              "policy": {
                                                "id": "policy-654-ghi",
                                                "type": "ACCESS",
                                                "policyConstraints": []
                                              }
                                            }
                                          ]
                                        }
                                        """))
                        })
            })
    @PostMapping("/connectorCatalog/assets")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ContractNegotiationRequest.class),
                            examples =
                                    @ExampleObject(
                                            name = "transferRequest",
                                            value =
                                                    """
                {
                  "providerEndpoint": "http://endpoint.provider.com",
                  "contractDefinitionId": "961ead7f-d8b2-4fc6-8e94-e3efbe2127ea",
                  "assetId": "asset-id"
                }
                """)))
    ResponseEntity<CatalogSearchResult> searchCatalogAssets(
            @Valid @RequestBody ContractNegotiationRequest contractNegotiationRequest, HttpServletRequest httpRequest);

    @Operation(
            summary = "Initiate a new contract negotiation",
            description =
                    "Initiates a contract negotiation between a consumer and a provider for a selected data offer. This step is required before any data transfer can occur.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContractNegotiationId.class),
                                    examples =
                                            @ExampleObject(
                                                    name = "negotiationStarted",
                                                    value =
                                                            """
                                        {
                                          "contractNegotiationId": "9e82cdd6-4aba-4a77-8dcc-f1a9f56d82c5"
                                        }
                                        """))
                        })
            })
    @PostMapping("/contracts")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ContractNegotiationRequest.class),
                            examples =
                                    @ExampleObject(
                                            name = "transferRequest",
                                            value =
                                                    """
                {
                  "providerEndpoint": "http://endpoint.provider.com",
                  "contractDefinitionId": "961ead7f-d8b2-4fc6-8e94-e3efbe2127ea",
                  "assetId": "asset-id"
                }
                """)))
    ResponseEntity<ContractNegotiationId> startContractNegotiation(
            @Valid @RequestBody ContractNegotiationRequest contractNegotiationRequest, HttpServletRequest httpRequest);

    @Operation(
            summary = "Return the contract negotiation status",
            description =
                    "Retrieves the current status of a contract negotiation. Useful for tracking the progress and outcome of the negotiation process.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContractNegotiation.class),
                                    examples =
                                            @ExampleObject(
                                                    name = "negotiationStatus",
                                                    value =
                                                            """
                                        {
                                          "@id": "9e82cdd6-4aba-4a77-8dcc-f1a9f56d82c5",
                                          "contractAgreementId": "ca-12345-abcde",
                                          "state": "CONFIRMED",
                                          "counterPartyAddress": "https://provider-edc.example.com/api/v1/dsp",
                                          "counterPartyId": "provider-id-123456",
                                          "protocol": "dataspace-protocol-http",
                                          "type": "CONSUMER",
                                          "createdAt": 1716825600000
                                        }
                                        """))
                        })
            })
    @GetMapping("/contracts/{contractNegotiationId}")
    ResponseEntity<ContractNegotiation> getContractNegotiationStatus(
            @Parameter(description = "The ID of the contract negotiation to retrieve the status for", required = true)
                    @PathVariable("contractNegotiationId")
                    @NotBlank
                    String contractNegotiationId,
            HttpServletRequest httpRequest);
}
