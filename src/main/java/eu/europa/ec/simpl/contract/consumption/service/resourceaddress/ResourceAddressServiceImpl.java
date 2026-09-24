package eu.europa.ec.simpl.contract.consumption.service.resourceaddress;

import eu.europa.ec.simpl.contract.consumption.model.resourceaddress.Template;
import eu.europa.ec.simpl.contract.consumption.property.ResourceAddressProperties;
import eu.europa.ec.simpl.data1.common.enumeration.OfferType;
import eu.europa.ec.simpl.data1.common.exception.ResourceAddressNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ResourceAddressServiceImpl implements ResourceAddressService {

    private static final String RESOURCE_PATH_TEMPLATE = "resourceaddress/template/TEMPLATE_";

    private static final String RESOURCE_PATH_UI_SCHEMA = "resourceaddress/ui-schema/UI_SCHEMA_";

    private static final String RESOURCE_NAME_SUFFIX = "DESTINATION";

    private static final String RESOURCE_EXT = ".json";

    private final ResourceAddressProperties resourceAddressProperties;

    @Override
    public List<Template> getDestinationAddressTemplates(OfferType offerType, String sharingMethodId) {
        log.debug(
                "getDestinationAddressTemplates() for offerType {} and sharingMethodId '{}'",
                offerType,
                sharingMethodId);
        Map<String, List<Template>> map =
                resourceAddressProperties.getTemplateMap().getOrDefault(offerType, Map.of());
        return map.getOrDefault(sharingMethodId, List.of()).stream().toList();
    }

    @Override
    public String getDestinationAddressSchema(String templateId) throws ResourceAddressNotFoundException, IOException {
        log.debug("getDestinationAddressSchema() for templateId '{}'", templateId);
        return getResourceContent(RESOURCE_PATH_TEMPLATE, templateId);
    }

    @Override
    public String getDestinationAddressUiSchema(String templateId)
            throws ResourceAddressNotFoundException, IOException {
        log.debug("getDestinationAddressUiSchema() for templateId '{}'", templateId);
        return getResourceContent(RESOURCE_PATH_UI_SCHEMA, templateId);
    }

    private String getResourceContent(String resourcePathPrefix, String templateId)
            throws ResourceAddressNotFoundException, IOException {
        Set<OfferType> offerTypes = resourceAddressProperties.getTemplateMap().keySet();
        Set<String> sharingMethods;
        List<Template> templates;
        Template template;
        for (OfferType offerType : offerTypes) {
            sharingMethods =
                    resourceAddressProperties.getTemplateMap().get(offerType).keySet();
            for (String sharingMethod : sharingMethods) {
                templates = resourceAddressProperties
                        .getTemplateMap()
                        .get(offerType)
                        .get(sharingMethod);
                template = findTemplateById(templateId, templates);
                if (template != null) {
                    return getResourceContent(resourcePathPrefix, offerType, sharingMethod, template);
                }
            }
        }
        throw new ResourceAddressNotFoundException(
                "Template UI schema not found", "No template UI schema found for templateId " + templateId);
    }

    private static String getResourceContent(
            String resourcePathPrefix, OfferType offerType, String sharingMethod, Template template)
            throws IOException {
        String resourcePath = resourcePathPrefix + offerType.name() + "_" + sharingMethod + "_" + RESOURCE_NAME_SUFFIX
                + "_" + template.getId() + RESOURCE_EXT;
        return getResourceContent(resourcePath);
    }

    private static String getResourceContent(String resourcePath) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("no resource found with path '" + resourcePath + "'");
            }
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
    }

    private static Template findTemplateById(String templateId, List<Template> templates) {
        for (Template template : templates) {
            if (template.getId().equals(templateId)) {
                return template;
            }
        }
        return null;
    }
}
