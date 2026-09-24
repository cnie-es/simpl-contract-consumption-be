package eu.europa.ec.simpl.contract.consumption.constant;

public final class RequestMappingV1 {

    public static final String VERSION = "/v1";

    public static final String BASE = VERSION;

    public static final String CONTRACT_NEGOTIATION = BASE;
    public static final String RESOURCE_ADDRESS = BASE + "/resourceAddresses";
    public static final String TRANSFER_PROCESS = BASE + "/transfers";

    private RequestMappingV1() {}
}
