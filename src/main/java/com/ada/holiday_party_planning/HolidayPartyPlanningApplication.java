package com.ada.holiday_party_planning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class HolidayPartyPlanningApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolidayPartyPlanningApplication.class, args);
	}

}
