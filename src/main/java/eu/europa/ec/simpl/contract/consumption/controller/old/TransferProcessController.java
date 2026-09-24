package eu.europa.ec.simpl.contract.consumption.controller.old;

import eu.europa.ec.simpl.contract.consumption.service.transferprocess.TransferProcessService;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcess;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcessId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferRequest;
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
 * @deprecated(will be replaced by the v1 TransferProcessController)
 */
@RestController
@RequestMapping("/transfer")
@Log4j2
@Tag(name = "Transfer Process")
@RequiredArgsConstructor
@Deprecated(since = "latest", forRemoval = true)
public class TransferProcessController extends AbstractController {

    private final TransferProcessService transferProcessService;

    /**
     * Initiates a transfer process for a given contract agreement
     *
     * @param transferRequest contains the contract agreement and data consumer destination where to transfer data
     * @return the transfer process ID
     */
    @Operation(summary = "Initiate transfer process")
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
    @PostMapping("/start")
    @LogRequest
    public ResponseEntity<TransferProcessId> startTransfer(
            @Valid @RequestBody TransferRequest transferRequest, HttpServletRequest httpRequest) {
        String tier1BearerToken = AuthBearerUtil.getBearerValue(httpRequest);

        log.debug("startTransfer(): invoking transferProcessService.startTransfer() with {}", transferRequest);
        TransferProcessId transferProcessIdResponse =
                transferProcessService.startTransfer(tier1BearerToken, transferRequest);

        log.debug("startTransfer(): returning response OK with {}", transferProcessIdResponse);
        return ResponseEntity.ok(transferProcessIdResponse);
    }

    @Operation(summary = "Get transfer process status")
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
    @GetMapping("/status/{id}")
    @LogRequest
    public ResponseEntity<TransferProcess> getTransferStatus(
            @Parameter(description = "The ID of the transfer process to retrieve the status for", required = true)
                    @PathVariable("id")
                    @NotBlank
                    String transferProcessId,
            HttpServletRequest httpRequest) {
        String tier1BearerToken = AuthBearerUtil.getBearerValue(httpRequest);

        log.debug(
                "getTransferStatus(): invoking transferProcessService.getTransferProcess() with transferProcessId '{}'",
                transferProcessId);
        TransferProcess transferProcess =
                transferProcessService.getTransferProcess(tier1BearerToken, transferProcessId);

        log.debug("startTransfer(): returning response OK with {}", transferProcess);
        return ResponseEntity.ok(transferProcess);
    }
}
