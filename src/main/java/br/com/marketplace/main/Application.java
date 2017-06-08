package br.com.marketplace.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@Configuration
@ComponentScan(basePackages = "br.com.marketplace.*")
public class Application {

	private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
	
	public static void main(String[] args) {
		LOGGER.log(Level.INFO, "Starting Application...");
		SpringApplication.run(Application.class, args);

	}

}
