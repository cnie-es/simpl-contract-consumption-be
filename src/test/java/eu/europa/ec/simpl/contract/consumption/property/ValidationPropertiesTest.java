package eu.europa.ec.simpl.contract.consumption.property;

import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.europa.ec.simpl.data1.common.properties.ValidationProperties;
import eu.europa.ec.simpl.data1.common.properties.factory.YamlPropertySourceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {ValidationProperties.class, YamlPropertySourceFactory.class})
@TestPropertySource(
        properties = {
            "validation.config.enabled=true",
            "validation.config.domain=https://example.com",
            "validation.config.endpoints.resource-addresses-validation-v1=/api/v1/validate"
        })
class ValidationPropertiesTest {

    @Autowired
    private ValidationProperties validationProperties;

    @Test
    void testIsEnabled() {
        assertTrue(validationProperties.isEnabled());
    }
}
