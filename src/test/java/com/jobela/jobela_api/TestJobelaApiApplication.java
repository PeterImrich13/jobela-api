package com.jobela.jobela_api;

import org.springframework.boot.SpringApplication;

public class TestJobelaApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(JobelaApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
