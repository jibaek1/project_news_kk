package com.ssr.newskuku;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.ssr.newskuku.domain")
@SpringBootApplication
public class NewskukuApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NewskukuApplication.class, args);
	}

}
