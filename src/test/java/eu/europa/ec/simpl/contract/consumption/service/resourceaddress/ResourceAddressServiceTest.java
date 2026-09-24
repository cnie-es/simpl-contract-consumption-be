package eu.europa.ec.simpl.contract.consumption.service.resourceaddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import eu.europa.ec.simpl.contract.consumption.model.resourceaddress.Template;
import eu.europa.ec.simpl.contract.consumption.property.ResourceAddressProperties;
import eu.europa.ec.simpl.data1.common.enumeration.OfferType;
import eu.europa.ec.simpl.data1.common.exception.ResourceAddressNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResourceAddressServiceTest {

    // private static final String TEMPLATE_RESOURCE_PATH = "resourceaddress/template/TEMPLATE_";
    // private static final String SCHEMA_RESOURCE_PATH = "resourceaddress/ui-schema/UI_SCHEMA_";
    // private static final String RESOURCE_NAME_SUFFIX = "SOURCE";
    // private static final String RESOURCE_EXT = ".json";

    private static final String RESOURCE_SHARING_METHOD_IONOS_S3 = "IONOS_S3";
    // private static final String RESOURCE_SHARING_METHOD_HTTPDATA_PUSH = "HTTPDATA_PUSH";

    @Mock
    private ResourceAddressProperties resourceAddressProperties;

    private ResourceAddressServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ResourceAddressServiceImpl(resourceAddressProperties);
    }

    @Test
    void testGetDestinationAddressTemplates() {
        // Prepare test data
        List<Template> templates = Arrays.asList(
                Template.builder().id("1").label("S3 Template id 1").build(),
                Template.builder().id("2").label("S3 Template id 2").build());

        Map<String, List<Template>> sharingMethodMap = new HashMap<>();
        sharingMethodMap.put(RESOURCE_SHARING_METHOD_IONOS_S3, templates);

        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        templateMap.put(OfferType.DATA, sharingMethodMap);

        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        // Execute
        List<Template> result =
                service.getDestinationAddressTemplates(OfferType.DATA, RESOURCE_SHARING_METHOD_IONOS_S3);

        // Verify
        assertEquals(2, result.size());
    }

    @Test
    void testGetDestinationAddressTemplatesForNonExistentMethod() {
        // Prepare test data
        Map<String, List<Template>> sharingMethodMap = new HashMap<>();
        sharingMethodMap.put(
                RESOURCE_SHARING_METHOD_IONOS_S3,
                List.of(Template.builder().id("1").label("S3 Template id 1").build()));

        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        templateMap.put(OfferType.DATA, sharingMethodMap);

        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        // Execute
        List<Template> result = service.getDestinationAddressTemplates(OfferType.DATA, "NON_EXISTENT");

        // Verify
        assertEquals(0, result.size());
    }

    @Test
    void testGetDestinationAddressTemplatesForNonExistentOfferType() {
        // Prepare test data
        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        templateMap.put(OfferType.DATA, Map.of(RESOURCE_SHARING_METHOD_IONOS_S3, List.of()));

        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        // Execute
        List<Template> result =
                service.getDestinationAddressTemplates(OfferType.INFRASTRUCTURE, RESOURCE_SHARING_METHOD_IONOS_S3);

        // Verify
        assertEquals(0, result.size());
    }

    @Test
    void testGetDestinationAddressSchema() throws ResourceAddressNotFoundException, IOException {
        // Prepare test data
        String templateId = "1";

        Map<String, List<Template>> sharingMethodMap = new HashMap<>();
        sharingMethodMap.put(
                RESOURCE_SHARING_METHOD_IONOS_S3,
                List.of(Template.builder()
                        .id(templateId)
                        .label("S3 Template id " + templateId)
                        .build()));

        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        templateMap.put(OfferType.DATA, sharingMethodMap);

        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        // Mock the static method
        try (MockedStatic<IOUtils> ioUtilsMock = Mockito.mockStatic(IOUtils.class)) {
            ioUtilsMock
                    .when(() -> IOUtils.toString(Mockito.any(InputStream.class), Mockito.eq(StandardCharsets.UTF_8)))
                    .thenReturn("template content");

            // Execute
            String result = service.getDestinationAddressSchema(templateId);

            // Verify
            assertEquals("template content", result);
        }
    }

    @Test
    void testGetDestinationAddressSchemaNotFound() {
        // Prepare test data
        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        Map<String, List<Template>> sharingMethodMap = new HashMap<>();
        sharingMethodMap.put(
                RESOURCE_SHARING_METHOD_IONOS_S3,
                List.of(Template.builder().id("1").label("S3 Template id 1").build()));
        templateMap.put(OfferType.DATA, sharingMethodMap);

        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        // Execute and verify
        assertThrows(ResourceAddressNotFoundException.class, () -> service.getDestinationAddressSchema("non-existent"));
    }

    @Test
    void testGetDestinationAddressSchemaWithIllegalStateException() {
        // Prepare test data
        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        Map<String, List<Template>> sharingMethodMap = new HashMap<>();
        sharingMethodMap.put(
                RESOURCE_SHARING_METHOD_IONOS_S3,
                List.of(Template.builder().id("99").label("S3 Template id 99").build()));
        templateMap.put(OfferType.DATA, sharingMethodMap);

        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        // Execute and verify
        assertThrows(IllegalStateException.class, () -> service.getDestinationAddressSchema("99"));
    }

    @Test
    void testGetDestinationAddressUiSchema() throws ResourceAddressNotFoundException, IOException {
        // Prepare test data
        String templateId = "1";

        Map<String, List<Template>> sharingMethodMap = new HashMap<>();
        sharingMethodMap.put(
                RESOURCE_SHARING_METHOD_IONOS_S3,
                List.of(Template.builder()
                        .id(templateId)
                        .label("S3 Template id " + templateId)
                        .build()));

        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        templateMap.put(OfferType.DATA, sharingMethodMap);

        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        // Mock the static method
        try (MockedStatic<IOUtils> ioUtilsMock = Mockito.mockStatic(IOUtils.class)) {
            ioUtilsMock
                    .when(() -> IOUtils.toString(Mockito.any(InputStream.class), Mockito.eq(StandardCharsets.UTF_8)))
                    .thenReturn("ui schema content");

            // Execute
            String result = service.getDestinationAddressUiSchema(templateId);

            // Verify
            assertEquals("ui schema content", result);
        }
    }

    @Test
    void testGetDestinationAddressUiSchemaWithIllegalStateException() {
        // Prepare test data
        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        Map<String, List<Template>> sharingMethodMap = new HashMap<>();
        sharingMethodMap.put(
                RESOURCE_SHARING_METHOD_IONOS_S3,
                List.of(Template.builder().id("99").label("S3 Template id 99").build()));
        templateMap.put(OfferType.DATA, sharingMethodMap);

        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        // Execute and verify
        assertThrows(IllegalStateException.class, () -> service.getDestinationAddressUiSchema("99"));
    }

    @Test
    void testGetDestinationAddressUiSchemaNotFound() {
        // Prepare test data
        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        Map<String, List<Template>> sharingMethodMap = new HashMap<>();
        sharingMethodMap.put(
                RESOURCE_SHARING_METHOD_IONOS_S3,
                List.of(Template.builder().id("1").label("S3 Template id 1").build()));
        templateMap.put(OfferType.DATA, sharingMethodMap);

        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        // Execute and verify
        assertThrows(
                ResourceAddressNotFoundException.class, () -> service.getDestinationAddressUiSchema("non-existent"));
    }

    @Test
    void testRestApiTemplateHasHttpProxyType() throws ResourceAddressNotFoundException, IOException {
        String templateId = "14";

        Map<String, List<Template>> sharingMethodMap = new HashMap<>();
        sharingMethodMap.put(
                "REST_API",
                List.of(Template.builder().id(templateId).label("Application Template REST API").build()));

        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        templateMap.put(OfferType.DATA, sharingMethodMap);

        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        String schema = service.getDestinationAddressSchema(templateId);

        assertTrue(schema.contains("HttpData-PULL"), "type must be HttpData-PULL for HTTP_DATA_PROXY transfers");
        assertFalse(schema.contains("RestApi"), "legacy RestApi type must not be present");
        assertFalse(schema.contains("baseUrl"), "push destination field baseUrl must not be present");
        assertFalse(schema.contains("authType"), "push destination field authType must not be present");
        assertFalse(schema.contains("apiKey"), "push destination field apiKey must not be present");
        assertFalse(schema.contains("bearerToken"), "push destination field bearerToken must not be present");
    }

    @Test
    void testRestApiUiSchemaDoesNotContainPushFields() throws ResourceAddressNotFoundException, IOException {
        String templateId = "14";

        Map<String, List<Template>> sharingMethodMap = new HashMap<>();
        sharingMethodMap.put(
                "REST_API",
                List.of(Template.builder().id(templateId).label("Application Template REST API").build()));

        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        templateMap.put(OfferType.DATA, sharingMethodMap);

        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        String uiSchema = service.getDestinationAddressUiSchema(templateId);

        assertFalse(uiSchema.contains("baseUrl"), "push field baseUrl must not appear in ui-schema");
        assertFalse(uiSchema.contains("authType"), "push field authType must not appear in ui-schema");
        assertFalse(uiSchema.contains("apiKey"), "push field apiKey must not appear in ui-schema");
        assertFalse(uiSchema.contains("bearerToken"), "push field bearerToken must not appear in ui-schema");
    }

    @Test
    void testGetDestinationAddressSchemaThrowsIOException() {
        // Prepare test data
        String templateId = "1";
        Map<String, List<Template>> sharingMethodMap = new HashMap<>();
        sharingMethodMap.put(
                RESOURCE_SHARING_METHOD_IONOS_S3,
                List.of(Template.builder()
                        .id(templateId)
                        .label("S3 Template id 1")
                        .build()));
        Map<OfferType, Map<String, List<Template>>> templateMap = new HashMap<>();
        templateMap.put(OfferType.DATA, sharingMethodMap);
        when(resourceAddressProperties.getTemplateMap()).thenReturn(templateMap);

        // Mock IOUtils to throw IOException
        try (MockedStatic<IOUtils> ioUtilsMock = Mockito.mockStatic(IOUtils.class)) {
            ioUtilsMock
                    .when(() -> IOUtils.toString(Mockito.any(InputStream.class), Mockito.eq(StandardCharsets.UTF_8)))
                    .thenThrow(new IOException("Simulated IO error"));

            // Execute and verify
            assertThrows(IOException.class, () -> service.getDestinationAddressSchema(templateId));
        }
    }
}
