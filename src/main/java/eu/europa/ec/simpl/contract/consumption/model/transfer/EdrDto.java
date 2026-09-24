package eu.europa.ec.simpl.contract.consumption.model.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Endpoint Data Reference issued for a single HTTP_DATA_PROXY transfer. " +
        "Use baseUrl and authorizationHeaderValue to call the provider API directly through the EDC Data Plane. " +
        "The token has a limited lifetime; re-fetch before expiresAt.")
public class EdrDto {

    @Schema(description = "Proxy endpoint URL provided by the consumer Data Plane. Append sub-paths or query params as needed.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String baseUrl;

    @Schema(description = "Value for the 'Authorization' request header when calling baseUrl.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorizationHeaderValue;

    @Schema(description = "Expiration timestamp of the EDR token. Null means the token does not expire. " +
            "Re-fetch the EDR before this time to avoid authentication failures.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private OffsetDateTime expiresAt;
}
