package eu.europa.ec.simpl.contract.consumption.model.resourceaddress;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Template {

    private String id;
    private String label;
}
