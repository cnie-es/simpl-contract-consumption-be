package eu.europa.ec.simpl.contract.consumption.controller.v1;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import eu.europa.ec.simpl.contract.consumption.constant.RequestMappingV1;
import eu.europa.ec.simpl.contract.consumption.service.resourceaddress.ResourceAddressService;
import eu.europa.ec.simpl.data1.common.enumeration.OfferType;
import eu.europa.ec.simpl.data1.common.exception.ResourceAddressNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(ResourceAddressController.class)
@TestPropertySource(properties = "web.mvc.bearer-token.required=false")
class ResourceAddressControllerTest {

    private static final String PATH = RequestMappingV1.RESOURCE_ADDRESS;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResourceAddressService resourceAddressService;

    @Test
    void testGetDestinationAddressTemplates() throws Exception {
        when(resourceAddressService.getDestinationAddressTemplates(any(), any()))
                .thenReturn(List.of());

        mockMvc.perform(get(PATH + "/sharingMethods/IONOS_S3/templates").param("offeringType", OfferType.DATA.name()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDestinationAddressSchema() throws Exception {
        when(resourceAddressService.getDestinationAddressSchema(any())).thenReturn("template-content");

        mockMvc.perform(get(PATH + "/templates/1/schema")).andExpect(status().isOk());
    }

    @Test
    void testGetDestinationAddressUiSchema() throws Exception {
        when(resourceAddressService.getDestinationAddressUiSchema(any())).thenReturn("ui-schema-content");

        mockMvc.perform(get(PATH + "/templates/1/uiSchema")).andExpect(status().isOk());
    }

    @Test
    void testGetDestinationAddressTemplates_RestApi() throws Exception {
        when(resourceAddressService.getDestinationAddressTemplates(any(), any()))
                .thenReturn(List.of());

        mockMvc.perform(get(PATH + "/sharingMethods/REST_API/templates")
                        .param("offeringType", OfferType.APPLICATION.name()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDestinationAddressSchemaWithResourceAddressNotFoundException() throws Exception {
        when(resourceAddressService.getDestinationAddressSchema(any()))
                .thenThrow(new ResourceAddressNotFoundException("problem detail", "problem issue"));

        mockMvc.perform(get(PATH + "/templates/1/schema"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }
}
