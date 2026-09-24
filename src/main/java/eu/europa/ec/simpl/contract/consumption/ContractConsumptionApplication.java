package eu.europa.ec.simpl.contract.consumption;

import eu.europa.ec.simpl.data1.common.util.ApplicationUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContractConsumptionApplication {

    public static void main(String[] args) {
        ApplicationUtil.printBannerAndRun(
                new SpringApplication(ContractConsumptionApplication.class),
                args,
                "banner.txt",
                ContractConsumptionApplication.class);
    }
}
