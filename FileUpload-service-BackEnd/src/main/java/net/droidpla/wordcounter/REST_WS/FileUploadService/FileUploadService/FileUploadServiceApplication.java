package net.droidpla.wordcounter.REST_WS.FileUploadService.FileUploadService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileUploadServiceApplication {

	private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB

	public static void main(String[] args) {
		SpringApplication.run(FileUploadServiceApplication.class, args);
	}
}
