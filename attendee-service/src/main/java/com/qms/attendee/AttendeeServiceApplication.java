package com.qms.attendee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.qms.attendee.util.PDFGenerator;

@SpringBootApplication
public class AttendeeServiceApplication {

	public static void main(String[] args) {
//		SpringApplication.run(AttendeeServiceApplication.class, args);
		ApplicationContext ac = SpringApplication.run(AttendeeServiceApplication.class, args);
//		PDFGenerator pDFGenerator = ac.getBean("pdfGenerator", PDFGenerator.class);
//
//		pDFGenerator.generatePdfReport();
	}

}
