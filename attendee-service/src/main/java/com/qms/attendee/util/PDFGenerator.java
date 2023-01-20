package com.qms.attendee.util;

import java.io.IOException;
import java.time.LocalDateTime;
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

//@Component("pdfGenerator")
// TODO: handle throws by try catch and replace p variable name everywhere with paragraph
public class PDFGenerator {

	private static Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
	private static Font COURIER_MEDIUM = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
	private static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
	private static Font COURIER_VERY_SMALL = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
	private static Font COURIER_FOOTER = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
	private static Font COURIER_FOOTER_RED = new Font(Font.FontFamily.COURIER, 12, Font.BOLD, BaseColor.RED);
	private static Font COURIER_FOOTER_GREEN = new Font(Font.FontFamily.COURIER, 12, Font.BOLD, BaseColor.GREEN);

//	@Value("${pdfDir}")
	private static final String pdfDir = "/home/ranosys/Desktop/"; // TODO: extract to constant file
//
//	@Value("${reportFileName}")
	private static final String reportFileName = "Quiz-Report"; // TODO: extract to constant file
//
//	@Value("${reportFileNameDateFormat}")
//	private String reportFileNameDateFormat;
//
//	@Value("${localDateFormat}")
//	private String localDateFormat;
//
//	@Value("${logoImgPath}")
//	private String logoImgPath;
//
//	@Value("${logoImgScale}")
//	private Float[] logoImgScale;
//
//	@Value("${currencySymbol:}")
//	private String currencySymbol;
//
//	@Value("${table_noOfColumns}")
//	private int noOfColumns;
//
//	@Value("${table.columnNames}")
//	private List<String> columnNames;

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

//			addFileMetaData(document);
//			addLogo(document);

			addReportTitle(document);
			addReportSummary(document);
			addHorizontalLine(document);
			addReportDetails(document);
//			createTable(document, noOfColumns);
			addFooter(document); // TODO: use table and document bottom
			document.close();
			System.out.println("------------------ PDF Report is ready!-------------------------");

		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	private void addFileMetaData(Document document) {
//		 document.addAuthor("Author");
//		 document.addCreationDate();
//		 document.addCreator("Creator");
//		 document.addHeader("Header name", "Header Content");
//		 document.addSubject("Subject");
//		 document.addTitle("Title");
//		 document.addProducer();
//	}

	private void addReportTitle(Document document) throws DocumentException {
		Paragraph paragraph = new Paragraph(this.quizTitle.toUpperCase() + " Quiz-Report", COURIER);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);
	}

	private void addReportSummary(Document document) throws DocumentException {

		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 2);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(2); // TODO: add in constant file for number of columns
		table.setWidthPercentage(100);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

//		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
//		font.setSize(20);
//		font.setColor(CMYKColor.WHITE);

		// TODO: extract all literals to constant file

		final List<String> summaryContent = new ArrayList<>();
		summaryContent.add("Name: " + this.attendeeName.toUpperCase());
		summaryContent.add("Right Answers: " + this.quizResult.getCorrectAnswersCount());
		summaryContent.add("Email: " + this.attendeeEmail.toUpperCase());
		summaryContent.add("Wrong Answers: " + this.quizResult.getWrongAnswersCount());
		// TODO: extract to date util
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

		PdfPTable table = new PdfPTable(1); // TODO: pass number of columns
		table.setWidthPercentage(100);
		PdfPCell cell = new PdfPCell(new Phrase(""));
		cell.setFixedHeight(1);
		table.addCell(cell);
		document.add(table);
	}

	private void addReportDetails(Document document) throws DocumentException {

		// TODO: loop below code and extract as add question

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

//	private void addLogo(Document document) {
//		try {
//			Image img = Image.getInstance(logoImgPath);
//			img.scalePercent(logoImgScale[0], logoImgScale[1]);
//			img.setAlignment(Element.ALIGN_RIGHT);
//			document.add(img);
//		} catch (DocumentException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	private void createTable(Document document, int noOfColumns) throws DocumentException {
//		Paragraph paragraph = new Paragraph();
//		leaveEmptyLine(paragraph, 3);
//		document.add(paragraph);
//
//		PdfPTable table = new PdfPTable(noOfColumns);
//
//		for (int i = 0; i < noOfColumns; i++) {
//			PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBackgroundColor(BaseColor.CYAN);
//			table.addCell(cell);
//		}
//
//		table.setHeaderRows(1);
//		getDbData(table);
//		document.add(table);
//	}

	private void addFooter(Document document) throws DocumentException {
		Paragraph p = new Paragraph();
		leaveEmptyLine(p, 3);
		p.setAlignment(Element.ALIGN_MIDDLE);
		p.add(new Paragraph("X----------------------End Of " + reportFileName + "------------------------X",
				COURIER_FOOTER));

		document.add(p);
	}

	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" ")); // TODO: what if don't pass anything
		}
	}

	private String getPdfNameWithDate() {
		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
		return pdfDir + reportFileName + "-" + localDateString + ".pdf";
	}

}

//https://springhow.com/spring-boot-pdf-generation/
//https://zetcode.com/springboot/servepdf/
//	https://springjava.com/spring-boot/export-data-into-pdf-file-in-spring-boot
//		https://www.codejava.net/frameworks/spring-boot/pdf-export-example
//			https://javatechonline.com/generating-dynamic-pdf-report-using-spring-boot/