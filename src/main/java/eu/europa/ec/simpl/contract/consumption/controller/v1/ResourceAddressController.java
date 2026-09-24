package eu.europa.ec.simpl.contract.consumption.controller.v1;

import eu.europa.ec.simpl.contract.consumption.constant.RequestMappingV1;
import eu.europa.ec.simpl.contract.consumption.model.resourceaddress.Template;
import eu.europa.ec.simpl.data1.common.controller.AbstractController;
import eu.europa.ec.simpl.data1.common.enumeration.OfferType;
import eu.europa.ec.simpl.data1.common.model.response.problem.NotFoundProblem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(RequestMappingV1.RESOURCE_ADDRESS)
@Tag(name = "Resource Address")
public interface ResourceAddressController {

    @Operation(
            summary = "Returns the list of destination address templates by sharing method and offering type.",
            description =
                    "Fetches a list of address templates associated with a specific sharing method and offering type. Helps consumers understand how to configure data destinations.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "A list of templates",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(type = "array", implementation = Template.class),
                                        examples =
                                                @ExampleObject(
                                                        name = "templates",
                                                        summary = "List of templates",
                                                        value =
                                                                """
                                                            [
                                                                {"id":"1", "label":"Template 1"},
                                                                {"id":"2", "label":"Template 2"},
                                                                {"id":"3", "label":"Template 3"}
                                                            ]
                                                            """)))
            })
    @GetMapping(path = "/sharingMethods/{sharingMethodId}/templates", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Template>> getDestinationAddressTemplates(
            @Parameter(description = "The offering type identifier", required = true) @RequestParam
                    OfferType offeringType,
            @Parameter(
                            description = "The resource sharing method identifier",
                            required = true,
                            schema =
                                    @Schema(
                                            type = "string",
                                            allowableValues = {
                                                "HTTPDATA_PUSH",
                                                "IONOS_S3",
                                                "REST_API"
                                            }))
                    @PathVariable("sharingMethodId")
                    String sharingMethodId);

    @Operation(
            summary = "Returns a destination address template schema based on templateId",
            description =
                    "Returns the JSON schema template for a specific resource address. This schema defines the structure and constraints for configuring a data destination.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Template (JSON schema) for the sourceAddress",
                        content =
                                @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(type = "object"),
                                        examples = {
                                            @ExampleObject(
                                                    name = "IonosS3",
                                                    summary = "IonosS3 template example",
                                                    value =
                                                            """
                                                            {
                                                                "type": "object",
                                                                "properties": {
                                                                    "bucketName": {
                                                                        "type": "string"
                                                                    },
                                                                    "region": {
                                                                        "type": "string"
                                                                    }
                                                                },
                                                                "required": ["bucketName"]
                                                            }
                                                            """)
                                        })),
                @ApiResponse(
                        responseCode = AbstractController.CODE_NOT_FOUND,
                        content =
                                @Content(
                                        mediaType = "application/problem+json",
                                        schema = @Schema(implementation = NotFoundProblem.class),
                                        examples =
                                                @ExampleObject(
                                                        name = "Not Found Error Example",
                                                        value =
                                                                """
                                                            {
                                                                "type": "urn:problem-type:simpl:resourceAddressNotFound",
                                                                "title": "Resource Address Not Found",
                                                                "status": 404,
                                                                "detail": "No template found for templateId 1"
                                                            }
                                                            """)))
            })
    @GetMapping(path = "/templates/{templateId}/schema", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getDestinationAddressTemplate(
            @Parameter(
                            description = "The template id",
                            required = true,
                            schema = @Schema(type = "string", example = "TPL-1"))
                    @PathVariable("templateId")
                    String templateId)
            throws IOException;

    @Operation(
            summary = "Returns a destination address UI schema based on templateId.",
            description =
                    "Returns a UI schema for rendering the resource address form. This helps front-end applications generate user-friendly forms for data destination configuration.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "UI schema for the sourceAddress",
                        content =
                                @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(type = "object"),
                                        examples = {
                                            @ExampleObject(
                                                    name = "amazonS3UI",
                                                    summary = "UI schema example",
                                                    value =
                                                            """
                                                            {
                                                                "ui:order": [
                                                                    "bucketName",
                                                                    "region"
                                                                ]
                                                            }
                                                            """)
                                        })),
                @ApiResponse(
                        responseCode = AbstractController.CODE_NOT_FOUND,
                        content =
                                @Content(
                                        mediaType = "application/problem+json",
                                        schema = @Schema(implementation = NotFoundProblem.class),
                                        examples =
                                                @ExampleObject(
                                                        name = "Not Found Error Example",
                                                        value =
                                                                """
                                                            {
                                                                "type": "urn:problem-type:simpl:resourceAddressNotFound",
                                                                "title": "Resource Address Not Found",
                                                                "status": 404,
                                                                "detail": "No UI schema found for templateId 1"
                                                            }
                                                            """)))
            })
    @GetMapping(path = "/templates/{templateId}/uiSchema", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getDestinationAddressUiSchema(
            @Parameter(
                            description = "The template id",
                            required = true,
                            schema = @Schema(type = "string", example = "TPL-1"))
                    @PathVariable("templateId")
                    String templateId)
            throws IOException;
}
