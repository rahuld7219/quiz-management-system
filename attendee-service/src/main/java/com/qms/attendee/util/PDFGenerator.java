package com.qms.attendee.util;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.qms.attendee.dto.QuizResult;
import com.qms.attendee.dto.ResultDetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PDFGenerator {

	private static final Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
	private static final Font COURIER_MEDIUM = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
	private static final Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
	private static final Font COURIER_VERY_SMALL = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
	private static final Font COURIER_FOOTER = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
	private static final Font COURIER_FOOTER_RED = new Font(Font.FontFamily.COURIER, 12, Font.BOLD, BaseColor.RED);
	private static final Font COURIER_FOOTER_GREEN = new Font(Font.FontFamily.COURIER, 12, Font.BOLD, BaseColor.GREEN);

//	private static final String pdfDir = "/home/ranosys/Desktop/";

	private static final String REPORT_FILENAME = "Quiz-Report";

	private final QuizResult quizResult;
	private final String quizTitle;
	private final String attendeeName;
	private final String attendeeEmail;
	private final HttpServletResponse response;

	public PDFGenerator(QuizResult quizResult, String quizTitle, String attendeeName, String attendeeEmail,
			HttpServletResponse response) {
		super();
		this.quizResult = quizResult;
		this.quizTitle = quizTitle;
		this.attendeeName = attendeeName;
		this.attendeeEmail = attendeeEmail;
		this.response = response;
	}

	public void generatePdfReport() {

		Document document = new Document();

		try {
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			addReportTitle(document);
			addReportSummary(document);
			addHorizontalLine(document);
			addReportDetails(document);
			addFooter(document);
			document.close();
			log.info("------------------ PDF Report is ready!-------------------------");

		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addReportTitle(Document document) throws DocumentException {
		Paragraph paragraph = new Paragraph(this.quizTitle.toUpperCase() + " Quiz-Report", COURIER);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);
	}

	private void addReportSummary(Document document) throws DocumentException {

		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 2);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

		final List<String> summaryContent = new ArrayList<>();
		summaryContent.add("Name: " + this.attendeeName.toUpperCase());
		summaryContent.add("Right Answers: " + this.quizResult.getCorrectAnswersCount());
		summaryContent.add("Email: " + this.attendeeEmail.toUpperCase());
		summaryContent.add("Wrong Answers: " + this.quizResult.getWrongAnswersCount());
		summaryContent
				.add("Exam Date: " + this.quizResult.getExamDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
		summaryContent.add("Your score: " + this.quizResult.getTotalScore());

		addDataToSummaryTable(table, summaryContent);

		document.add(table);
	}

	private void addDataToSummaryTable(PdfPTable table, List<String> summaryContent) {

		boolean leftColumn = true;

		for (String cellData : summaryContent) {
			PdfPCell cell = new PdfPCell(new Phrase(cellData, COURIER_MEDIUM));
			if (leftColumn) {
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				leftColumn = false;
			} else {
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				leftColumn = true;
			}
			cell.setPaddingTop(5);
			cell.setPaddingRight(5);
			cell.setPaddingBottom(5);
			cell.setPaddingLeft(5);
			table.addCell(cell);
		}
	}

	private void addHorizontalLine(Document document) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 1);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		PdfPCell cell = new PdfPCell(new Phrase(""));
		cell.setFixedHeight(1);
		table.addCell(cell);
		document.add(table);
	}

	private void addReportDetails(Document document) throws DocumentException {

		Paragraph p = new Paragraph();
		leaveEmptyLine(p, 1);

		int questionNumber = 0;
		for (ResultDetail resultDetail : this.quizResult.getDetails()) {
			questionNumber++;
			addQuestionReport(p, resultDetail, questionNumber);
			leaveEmptyLine(p, 1);
		}

		p.setKeepTogether(true);
		document.add(p);

	}

	private void addQuestionReport(Paragraph p, ResultDetail resultDetail, int questionNumber) {

		Paragraph p1 = new Paragraph("Q." + questionNumber + " " + resultDetail.getQuestionDetail(), COURIER_SMALL);
		p1.setAlignment(Element.ALIGN_LEFT);

		Paragraph p2 = new Paragraph("(A) " + resultDetail.getOptionA(), COURIER_VERY_SMALL);
		Paragraph p3 = new Paragraph("(B) " + resultDetail.getOptionB(), COURIER_VERY_SMALL);
		Paragraph p4 = new Paragraph("(C) " + resultDetail.getOptionC(), COURIER_VERY_SMALL);
		Paragraph p5 = new Paragraph("(D) " + resultDetail.getOptionD(), COURIER_VERY_SMALL);

		Paragraph p6;
		if (resultDetail.getSubmittedAnswer().equalsIgnoreCase(resultDetail.getCorrectAnswer())) {
			p6 = new Paragraph("Submitted Answer: (" + resultDetail.getSubmittedAnswer().toUpperCase() + ")",
					COURIER_FOOTER_GREEN);
		} else {
			p6 = new Paragraph("Submitted Answer: (" + resultDetail.getSubmittedAnswer().toUpperCase() + ")",
					COURIER_FOOTER_RED);
		}

		Paragraph p7 = new Paragraph("Correct Answer: (" + resultDetail.getCorrectAnswer().toUpperCase() + ")",
				COURIER_FOOTER);
		p.add(p1);
		p.add(p2);
		p.add(p3);
		p.add(p4);
		p.add(p5);
		p.add(p6);
		p.add(p7);

	}

	private void addFooter(Document document) throws DocumentException {
		Paragraph p = new Paragraph();
		leaveEmptyLine(p, 3);
		p.setAlignment(Element.ALIGN_MIDDLE);
		p.add(new Paragraph("X----------------------End Of " + REPORT_FILENAME + "------------------------X",
				COURIER_FOOTER));

		document.add(p);
	}

	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

//	private String getPdfNameWithDate() {
//		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
//		return pdfDir + REPORT_FILENAME + "-" + localDateString + ".pdf";
//	}

}