package eu.europa.ec.simpl.contract.consumption.controller.v1;

import eu.europa.ec.simpl.contract.consumption.constant.RequestMappingV1;
import eu.europa.ec.simpl.contract.consumption.model.transfer.EdrDto;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcess;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcessId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferRequest;
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
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(RequestMappingV1.TRANSFER_PROCESS)
@Tag(name = "Transfer Process")
public interface TransferProcessController {

    /**
     * Initiates a transfer process for a given contract agreement
     *
     * @param transferRequest contains the contract agreement and data consumer destination where to transfer data
     * @return the transfer process ID
     */
    @Operation(
            summary = "Initiate a new transfer process",
            description =
                    "Starts a data transfer process using a previously negotiated contract. This operation enables the actual movement of data from provider to consumer.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransferProcessId.class),
                                    examples =
                                            @ExampleObject(
                                                    name = "successfulTransferStart",
                                                    value =
                                                            """
                                        {
                                          "transferProcessId": "c76e83a1-2271-4143-a3f5-4f64d4cb5ecc"
                                        }
                                        """))
                        })
            })
    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TransferRequest.class),
                            examples =
                                    @ExampleObject(
                                            name = "transferRequest",
                                            value =
                                                    """
                {
                    "providerEndpoint": "https://edc-provider.dev.simpl-europe.eu/protocol",
                    "contractId": "4a7f28e5-33a9-4f9e-9de9-a4f687502bce",
                    "templateId": "1",
                    "dataDestination": {
                        "type": "IonosS3",
                        "region": "de",
                        "storage": "s3-eu-central-1.ionoscloud.com",
                        "bucketName": "simpl-cons",
                        "objectName": "european_health_data.csv",
                        "path": "folder1/",
                        "keyName": "test-key-name"
                    }
                }
                """)))
    ResponseEntity<TransferProcessId> startTransfer(
            @Valid @RequestBody TransferRequest transferRequest, HttpServletRequest httpRequest) throws IOException;

    @Operation(
            summary = "Returns the transfer process status",
            description =
                    "Returns the current status of a data transfer process. Allows consumers to monitor the progress and completion of data delivery.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransferProcess.class),
                                    examples =
                                            @ExampleObject(
                                                    name = "transferProcessStatus",
                                                    value =
                                                            """
                                        {
                                          "@id": "c76e83a1-2271-4143-a3f5-4f64d4cb5ecc",
                                          "state": "COMPLETED",
                                          "finalState": "COMPLETED",
                                          "assetId": "asset-123456",
                                          "contractId": "contract-789012",
                                          "correlationId": "corr-345678",
                                          "transferType": "HttpData",
                                          "type": "CONSUMER",
                                          "stateTimestamp": 1716825600000
                                        }
                                        """))
                        })
            })
    @GetMapping("/{transferProcessId}")
    ResponseEntity<TransferProcess> getTransferStatus(
            @Parameter(description = "The ID of the transfer process to retrieve the status for", required = true)
                    @PathVariable("transferProcessId")
                    @NotBlank
                    String transferProcessId,
            HttpServletRequest httpRequest);

    @Operation(
            summary = "Returns the EDR (Endpoint Data Reference) for an HTTP_DATA_PROXY transfer",
            description =
                    "Returns the EDR for a transfer process that is in STARTED state with type HTTP_DATA_PROXY. "
                    + "The EDR contains the proxy endpoint URL (baseUrl) and a short-lived authorization token. "
                    + "Use these to call the provider's API directly through the consumer Data Plane: "
                    + "POST/GET {baseUrl} with header 'Authorization: {authorizationHeaderValue}'. "
                    + "Re-fetch the EDR before expiresAt to avoid authentication failures.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EdrDto.class),
                                    examples =
                                            @ExampleObject(
                                                    name = "edrResponse",
                                                    value =
                                                            """
                                        {
                                          "baseUrl": "https://consumer-data-plane.example.com/proxy/chat-app",
                                          "authorizationHeaderValue": "eyJhbGciOiJSUzI1NiJ9...",
                                          "expiresAt": "2025-06-01T12:05:00Z"
                                        }
                                        """))
                        })
            })
    @GetMapping("/{transferProcessId}/edr")
    ResponseEntity<EdrDto> getTransferEdr(
            @Parameter(description = "The ID of the HTTP_DATA_PROXY transfer process", required = true)
                    @PathVariable("transferProcessId")
                    @NotBlank
                    String transferProcessId,
            HttpServletRequest httpRequest);
}
