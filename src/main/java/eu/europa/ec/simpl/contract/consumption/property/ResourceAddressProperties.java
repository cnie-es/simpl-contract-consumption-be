package eu.europa.ec.simpl.contract.consumption.property;

import eu.europa.ec.simpl.contract.consumption.model.resourceaddress.Template;
import eu.europa.ec.simpl.data1.common.enumeration.OfferType;
import eu.europa.ec.simpl.data1.common.properties.factory.YamlPropertySourceFactory;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "resource-address.config")
@PropertySource(value = "classpath:resourceAddress-config.yml", factory = YamlPropertySourceFactory.class)
public class ResourceAddressProperties {

    private Map<OfferType, Map<String, List<Template>>> templateMap;

    public Map<OfferType, Map<String, List<Template>>> getTemplateMap() {
        return templateMap;
    }

    public void setTemplateMap(Map<OfferType, Map<String, List<Template>>> templateMap) {
        this.templateMap = templateMap;
    }
}
