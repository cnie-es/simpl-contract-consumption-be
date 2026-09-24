package eu.europa.ec.simpl.contract.consumption.controller.v1;

import eu.europa.ec.simpl.contract.consumption.model.resourceaddress.Template;
import eu.europa.ec.simpl.contract.consumption.service.resourceaddress.ResourceAddressService;
import eu.europa.ec.simpl.data1.common.controller.AbstractController;
import eu.europa.ec.simpl.data1.common.enumeration.OfferType;
import eu.europa.ec.simpl.data1.common.logging.LogRequest;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController("resourceAddressControllerV1")
@Log4j2
@RequiredArgsConstructor
public class ResourceAddressControllerImpl extends AbstractController implements ResourceAddressController {

    private final ResourceAddressService resourceAddressService;

    @LogRequest
    @Override
    public ResponseEntity<List<Template>> getDestinationAddressTemplates(
            OfferType offeringType, String sharingMethodId) {
        log.debug(
                "getDestinationAddressTemplates(): invoking resourceAddressService.getDestinationAddressTemplates() with offeringType {} and sharingMethodId '{}'",
                offeringType,
                sharingMethodId);
        return ResponseEntity.ok(resourceAddressService.getDestinationAddressTemplates(offeringType, sharingMethodId));
    }

    @LogRequest
    @Override
    public ResponseEntity<String> getDestinationAddressTemplate(String templateId) throws IOException {
        log.debug(
                "getDestinationAddressTemplate(): invoking resourceAddressService.getDestinationAddressSchema() with templateId '{}'",
                templateId);
        return ResponseEntity.ok(resourceAddressService.getDestinationAddressSchema(templateId));
    }

    @LogRequest
    @Override
    public ResponseEntity<String> getDestinationAddressUiSchema(String templateId) throws IOException {
        log.debug(
                "getDestinationAddressUiSchema(): invoking resourceAddressService.getDestinationAddressUiSchema() with templateId '{}'",
                templateId);
        return ResponseEntity.ok(resourceAddressService.getDestinationAddressUiSchema(templateId));
    }
}
