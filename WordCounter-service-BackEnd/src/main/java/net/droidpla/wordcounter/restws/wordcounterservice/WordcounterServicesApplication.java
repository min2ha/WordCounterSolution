package net.droidpla.wordcounter.restws.wordcounterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by min2ha on 21/10/2017.
 */
@SpringBootApplication
//@SpringBootApplication(scanBasePackages={"net.droidpla.wordcounter.restws"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
//@EnableAsync
public class WordcounterServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordcounterServicesApplication.class, args);
	}
}
