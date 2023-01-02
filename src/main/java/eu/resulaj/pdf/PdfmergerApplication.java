package eu.resulaj.pdf;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdfmergerApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(PdfmergerApplication.class, args);

	}

}
