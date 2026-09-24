package eu.europa.ec.simpl.contract.consumption.service.validation;

import eu.europa.ec.simpl.data1.common.adapter.connector.model.transfer.TransferRequest;
import java.io.IOException;

public interface ValidationService {

    void validateResourceAddress(TransferRequest transferRequest, String templateId) throws IOException;
}
