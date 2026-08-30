package com.abhi.orderprocessingservice;

import org.springframework.boot.SpringApplication;

public class TestOrderprocessingserviceApplication {

	public static void main(String[] args) {
		SpringApplication.from(OrderprocessingserviceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
