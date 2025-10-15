package com.css.ApiCSS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.css") 
@EnableJpaRepositories(basePackages = "com.css.repository") 
@EntityScan(basePackages = "com.css.entity") 
public class ApiCssApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCssApplication.class, args);
	}

}
