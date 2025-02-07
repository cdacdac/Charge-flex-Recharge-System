package com.Charge_Flex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.Charge_Flex.services.auth", "com.Charge_Flex.service.mail", "com.Charge_Flex.controller", 
		"com.Charge_Flex.configuration", "com.Charge_Flex.services.jwt", "com.Charge_Flex.utils"})
@EntityScan(basePackages = {"com.Charge_Flex.entity"})
@EnableJpaRepositories(basePackages = "com.Charge_Flex.repository")
public class ChargeFlexApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChargeFlexApplication.class, args);
	}

}
