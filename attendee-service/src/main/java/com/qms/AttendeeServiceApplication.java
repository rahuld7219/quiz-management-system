package com.qms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AttendeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendeeServiceApplication.class, args);

//		ApplicationContext ac = SpringApplication.run(AttendeeServiceApplication.class, args);
//		PDFGenerator pDFGenerator = ac.getBean("pdfGenerator", PDFGenerator.class);
//		pDFGenerator.generatePdfReport();
	}

}
