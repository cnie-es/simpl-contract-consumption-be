package eu.europa.ec.simpl.contract.consumption.service.resourceaddress;

import eu.europa.ec.simpl.contract.consumption.model.resourceaddress.Template;
import eu.europa.ec.simpl.data1.common.enumeration.OfferType;
import java.io.IOException;
import java.util.List;

public interface ResourceAddressService {

    List<Template> getDestinationAddressTemplates(OfferType offerType, String sharingMethodId);

    String getDestinationAddressSchema(String templateId) throws IOException;

    String getDestinationAddressUiSchema(String templateId) throws IOException;
}
