package eu.europa.ec.simpl.contract.consumption.service.transferprocess;

import eu.europa.ec.simpl.contract.consumption.client.ConnectorAdapterClient;
import eu.europa.ec.simpl.contract.consumption.model.transfer.EdrDto;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcess;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcessId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferRequest;
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
public class TransferProcessServiceImpl implements TransferProcessService {

    private final ConnectorAdapterClient connectorAdapterClient;

    @Override
    public TransferProcessId startTransfer(String bearerToken, TransferRequest request) {
        try {
            log.debug("startTransfer(): invoking connectorAdapterClient.startTransfer() for {}", request);
            ResponseEntity<TransferProcessId> response =
                    connectorAdapterClient.startTransfer(AuthBearerUtil.toBearerString(bearerToken), request);
            log.debug("startTransfer(): received response {}", response);
            return response.getBody();
        } catch (FeignException e) {
            log.error("startTransfer() failed", e);
            throw RemoteServiceUtil.toRemoteServiceErrorException(
                    CommonErrorType.REMOTE_CONNECTOR_ADAPTER_ERROR, "startTransfer operation failed", e);
        }
    }

    @Override
    public TransferProcess getTransferProcess(String bearerToken, String transferProcessId) {
        try {
            log.debug(
                    "getTransferProcess(): invoking connectorAdapterClient.getTransferStatus() for transferProcessId {}",
                    transferProcessId);
            ResponseEntity<TransferProcess> response = connectorAdapterClient.getTransferStatus(
                    AuthBearerUtil.toBearerString(bearerToken), transferProcessId);
            log.debug("getTransferProcess(): received response {}", response);
            return response.getBody();
        } catch (FeignException e) {
            log.error("getTransferProcess() failed", e);
            throw RemoteServiceUtil.toRemoteServiceErrorException(
                    CommonErrorType.REMOTE_CONNECTOR_ADAPTER_ERROR, "getTransferStatus operation failed", e);
        }
    }

    @Override
    public EdrDto getTransferEdr(String bearerToken, String transferProcessId) {
        try {
            log.debug(
                    "getTransferEdr(): invoking connectorAdapterClient.getTransferEdr() for transferProcessId {}",
                    transferProcessId);
            ResponseEntity<EdrDto> response = connectorAdapterClient.getTransferEdr(
                    AuthBearerUtil.toBearerString(bearerToken), transferProcessId);
            log.debug("getTransferEdr(): received response {}", response);
            return response.getBody();
        } catch (FeignException e) {
            log.error("getTransferEdr() failed", e);
            throw RemoteServiceUtil.toRemoteServiceErrorException(
                    CommonErrorType.REMOTE_CONNECTOR_ADAPTER_ERROR, "getTransferEdr operation failed", e);
        }
    }
}
