package eu.europa.ec.simpl.contract.consumption.service.transferprocess;

import eu.europa.ec.simpl.contract.consumption.model.transfer.EdrDto;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcess;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferProcessId;
import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferRequest;

public interface TransferProcessService {

    TransferProcessId startTransfer(String bearerToken, TransferRequest request);

    TransferProcess getTransferProcess(String bearerToken, String transferProcessId);

    EdrDto getTransferEdr(String bearerToken, String transferProcessId);
}
