package eu.europa.ec.simpl.contract.consumption.controller.v1;

import eu.europa.ec.simpl.contract.consumption.model.transfer.EdrDto;
import eu.europa.ec.simpl.contract.consumption.service.transferprocess.TransferProcessService;
import eu.europa.ec.simpl.contract.consumption.service.validation.ValidationService;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcess;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcessId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferRequest;
import eu.europa.ec.simpl.data1.common.controller.AbstractController;
import eu.europa.ec.simpl.data1.common.logging.LogRequest;
import eu.europa.ec.simpl.data1.common.util.AuthBearerUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController("transferProcessControllerV1")
@Log4j2
@RequiredArgsConstructor
public class TransferProcessControllerImpl extends AbstractController implements TransferProcessController {

    private final TransferProcessService transferProcessService;
    private final ValidationService validationService;

    @LogRequest(businessOperation = "START_TRANSFER")
    @Override
    public ResponseEntity<TransferProcessId> startTransfer(
            TransferRequest transferRequest, HttpServletRequest httpRequest) throws IOException {
        String tier1BearerToken = AuthBearerUtil.getBearerValue(httpRequest);

        validationService.validateResourceAddress(transferRequest, transferRequest.getTemplateId());

        log.debug("startTransfer(): invoking transferProcessService.startTransfer()");
        TransferProcessId transferProcessIdResponse =
                transferProcessService.startTransfer(tier1BearerToken, transferRequest);

        log.debug("startTransfer(): returning response OK");
        return ResponseEntity.ok(transferProcessIdResponse);
    }

    @LogRequest
    @Override
    public ResponseEntity<TransferProcess> getTransferStatus(String transferProcessId, HttpServletRequest httpRequest) {
        String tier1BearerToken = AuthBearerUtil.getBearerValue(httpRequest);

        log.debug("getTransferStatus(): invoking transferProcessService.getTransferProcess()");
        TransferProcess transferProcess =
                transferProcessService.getTransferProcess(tier1BearerToken, transferProcessId);

        log.debug("getTransferStatus(): returning response OK");
        return ResponseEntity.ok(transferProcess);
    }

    @LogRequest(businessOperation = "GET_TRANSFER_EDR")
    @Override
    public ResponseEntity<EdrDto> getTransferEdr(String transferProcessId, HttpServletRequest httpRequest) {
        String tier1BearerToken = AuthBearerUtil.getBearerValue(httpRequest);

        log.debug("getTransferEdr(): invoking transferProcessService.getTransferEdr()");
        EdrDto edrDto = transferProcessService.getTransferEdr(tier1BearerToken, transferProcessId);

        log.debug("getTransferEdr(): returning response OK");
        return ResponseEntity.ok(edrDto);
    }
}
