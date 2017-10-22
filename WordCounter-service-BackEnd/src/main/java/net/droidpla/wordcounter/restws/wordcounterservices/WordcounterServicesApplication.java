package net.droidpla.wordcounter.restws.wordcounterservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages={"net.droidpla.wordcounter.restws"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableAsync
public class WordcounterServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordcounterServicesApplication.class, args);
	}
}
