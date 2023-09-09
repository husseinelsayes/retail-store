package com.example.retail;

import org.springframework.boot.SpringApplication;
public class TestRetailApplication {

	public static void main(String[] args) {
		SpringApplication.from(RetailApplication::main).with(TestRetailApplication.class).run(args);
	}

}
