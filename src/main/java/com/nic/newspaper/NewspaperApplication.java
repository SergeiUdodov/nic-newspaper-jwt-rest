package com.nic.newspaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class NewspaperApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewspaperApplication.class, args);
	}

}
