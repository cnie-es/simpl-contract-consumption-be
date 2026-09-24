package eu.europa.ec.simpl.contract.consumption.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Profile("!test")
@Configuration
@ComponentScan(
        basePackages = {
            "eu.europa.ec.simpl.contract.consumption",
            "eu.europa.ec.simpl.data1.common.client",
            "eu.europa.ec.simpl.data1.common.config.feign",
            "eu.europa.ec.simpl.data1.common.config.openapi",
            "eu.europa.ec.simpl.data1.common.config.webmvc",
            "eu.europa.ec.simpl.data1.common.controller.advice.root",
            "eu.europa.ec.simpl.data1.common.controller.status",
            "eu.europa.ec.simpl.data1.common.filter",
            "eu.europa.ec.simpl.data1.common.logging",
            "eu.europa.ec.simpl.data1.common.properties",
            "eu.europa.ec.simpl.data1.common.service.jwt"
        })
@EnableFeignClients(
        basePackages = {"eu.europa.ec.simpl.contract.consumption", "eu.europa.ec.simpl.data1.common.client.validation"})
@EnableWebMvc
public class ApplicationConfig {}
