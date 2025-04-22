//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class ReportServiceImpl implements ReportService {
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	public Map<String, Object> generateLoanOfficerReport(int loanOfficerId) {
//		LoanOfficer officer = loanOfficerRepository.findById(loanOfficerId)
//				.orElseThrow(() -> new ResourceNotFoundException("Loan Officer not found with ID: " + loanOfficerId));
//		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//		List<Loan> activeLoans = loans.stream().filter(l -> !l.getStatus().getStatusName().equals("PAID_OFF")
//				&& !l.getStatus().getStatusName().equals("CLOSED")).collect(Collectors.toList());
//
//		Map<String, Object> report = new HashMap<>();
//		report.put("loansOffered", loans.size());
//		report.put("rejected", loans.stream().filter(l -> l.getStatus().getStatusName().equals("REJECTED")).count());
//		report.put("inProcess", loans.stream().filter(l -> l.getStatus().getStatusName().equals("PENDING")
//				|| l.getStatus().getStatusName().equals("UNDER_REVIEW")).count());
//		report.put("amountDisbursed",
//				loans.stream()
//						.filter(l -> l.getStatus().getStatusName().equals("APPROVED")
//								|| (l.getNpaStatus() != null && l.getNpaStatus().getStatusName().equals("NPA")))
//						.map(Loan::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
//		report.put("customersEntertained",
//				customerRepository.findByLoanOfficerIdAndIsDeletedFalse(loanOfficerId).stream().map(Customer::getId).distinct().count());
//		report.put("npas", activeLoans.stream()
//				.filter(l -> l.getNpaStatus() != null && l.getNpaStatus().getStatusName().equals("NPA")).count());
//		report.put("redFlaggedCustomers",
//				customerRepository.findByLoanOfficerIdAndIsDeletedFalse(loanOfficerId).stream().filter(Customer::isRedFlagged).count());
//
//		return report;
//	}
//}












//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.exception.ReportGenerationException;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//import com.itextpdf.layout.properties.TextAlignment;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class ReportServiceImpl implements ReportService {
//
//    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
//
//    @Autowired
//    private LoanRepository loanRepository;
//
//    @Autowired
//    private LoanOfficerRepository loanOfficerRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Override
//    public byte[] generateLoanOfficerReport(int loanOfficerId) {
//        logger.info("Generating PDF report for loan officer ID: {}", loanOfficerId);
//        LoanOfficer officer = loanOfficerRepository.findById(loanOfficerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Loan Officer not found with ID: " + loanOfficerId));
//        List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//        List<Loan> activeLoans = loans.stream()
//                .filter(l -> !l.getStatus().getStatusName().equals("PAID_OFF") && !l.getStatus().getStatusName().equals("CLOSED"))
//                .collect(Collectors.toList());
//
//        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//            PdfWriter writer = new PdfWriter(baos);
//            PdfDocument pdf = new PdfDocument(writer);
//            Document document = new Document(pdf);
//
//            // Title
//            document.add(new Paragraph("Loan Officer Report")
//                    .setFontSize(18)
//                    .setBold()
//                    .setTextAlignment(TextAlignment.CENTER));
//
//            // Officer Details
//            document.add(new Paragraph("Loan Officer ID: " + officer.getId()));
//            document.add(new Paragraph("Username: " + officer.getUser().getUsername()));
//            document.add(new Paragraph("Email: " + officer.getUser().getEmail()));
//            document.add(new Paragraph("\n"));
//
//            // Report Metrics
//            Table table = new Table(2);
//            table.addCell("Metric");
//            table.addCell("Value");
//            table.addCell("Total Loans Offered");
//            table.addCell(String.valueOf(loans.size()));
//            table.addCell("Rejected Loans");
//            table.addCell(String.valueOf(loans.stream().filter(l -> l.getStatus().getStatusName().equals("REJECTED")).count()));
//            table.addCell("Loans In Process");
//            table.addCell(String.valueOf(loans.stream().filter(l -> l.getStatus().getStatusName().equals("PENDING") || l.getStatus().getStatusName().equals("UNDER_REVIEW")).count()));
//            table.addCell("Amount Disbursed");
//            table.addCell(loans.stream()
//                    .filter(l -> l.getStatus().getStatusName().equals("APPROVED") || (l.getNpaStatus() != null && l.getNpaStatus().getStatusName().equals("NPA")))
//                    .map(Loan::getAmount)
//                    .reduce(BigDecimal.ZERO, BigDecimal::add)
//                    .toString());
//            table.addCell("Customers Entertained");
//            table.addCell(String.valueOf(customerRepository.findByLoanOfficerIdAndIsDeletedFalse(loanOfficerId).stream().map(Customer::getId).distinct().count()));
//            table.addCell("NPAs");
//            table.addCell(String.valueOf(activeLoans.stream()
//                    .filter(l -> l.getNpaStatus() != null && l.getNpaStatus().getStatusName().equals("NPA"))
//                    .count()));
//            table.addCell("Red Flagged Customers");
//            table.addCell(String.valueOf(customerRepository.findByLoanOfficerIdAndIsDeletedFalse(loanOfficerId).stream().filter(Customer::isRedFlagged).count()));
//            document.add(table);
//
//            document.close();
//            logger.info("PDF generated successfully for loan officer ID: {}", loanOfficerId);
//            return baos.toByteArray();
//        } catch (Exception e) {
//            logger.error("Failed to generate PDF for loan officer ID: {}", loanOfficerId, e);
//            throw new ReportGenerationException("Failed to generate PDF report: " + e.getMessage());
//        }
//    }
//}



package com.aurionpro.lms.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.Loan;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.exception.ReportGenerationException;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.LoanOfficerRepository;
import com.aurionpro.lms.repository.LoanRepository;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private static final DeviceRgb PRIMARY_COLOR = new DeviceRgb(0, 102, 204); // Blue
    private static final DeviceRgb LIGHT_GRAY = new DeviceRgb(240, 240, 240);

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanOfficerRepository loanOfficerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public byte[] generateLoanOfficerReport(int loanOfficerId) {
        logger.info("Generating PDF report for loan officer ID: {}", loanOfficerId);
        LoanOfficer officer = loanOfficerRepository.findById(loanOfficerId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan Officer not found with ID: " + loanOfficerId));
        List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
        List<Loan> activeLoans = loans.stream()
                .filter(l -> !l.getStatus().getStatusName().equals("PAID_OFF") && !l.getStatus().getStatusName().equals("CLOSED"))
                .collect(Collectors.toList());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(50, 50, 50, 50);

            // Set font
            PdfFont font = PdfFontFactory.createFont("Helvetica");
            PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");

            // Add watermark
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, event -> {
                PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
                PdfDocument pdfDoc = docEvent.getDocument();
                PdfCanvas canvas = new PdfCanvas(pdfDoc.getLastPage());
                PdfExtGState gs = new PdfExtGState().setFillOpacity(0.2f);
                canvas.saveState().setExtGState(gs);
                canvas.beginText()
                        .setFontAndSize(boldFont, 60)
                        .setFillColor(ColorConstants.RED)
                        .moveText(100, 300)
                        .setTextMatrix(1, 0, -0.5f, 1, 100, 300)
                        .showText("Confidential")
                        .endText();
                canvas.restoreState();
            });

            // Header
            Table headerTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}));
            headerTable.setWidth(UnitValue.createPercentValue(100));
            headerTable.addCell(new Cell()
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph("Loan Officer Report")
                            .setFont(boldFont)
                            .setFontSize(20)
                            .setFontColor(ColorConstants.WHITE)
                            .setBackgroundColor(PRIMARY_COLOR)
                            .setPadding(5)));
            headerTable.addCell(new Cell()
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph("Date: " + LocalDate.now())
                            .setFont(font)
                            .setFontSize(12)
                            .setTextAlignment(TextAlignment.RIGHT)));
            document.add(headerTable);
            document.add(new Paragraph("\n"));

            // Officer Details
            document.add(new Paragraph("Loan Officer Details")
                    .setFont(boldFont)
                    .setFontSize(14)
                    .setFontColor(PRIMARY_COLOR));
            document.add(new Paragraph("ID: " + officer.getId()).setFont(font).setFontSize(12));
            document.add(new Paragraph("Username: " + officer.getUser().getUsername()).setFont(font).setFontSize(12));
            document.add(new Paragraph("Email: " + officer.getUser().getEmail()).setFont(font).setFontSize(12));
            document.add(new Paragraph("\n"));

            // Report Metrics
            document.add(new Paragraph("Performance Metrics")
                    .setFont(boldFont)
                    .setFontSize(14)
                    .setFontColor(PRIMARY_COLOR));
            Table table = new Table(UnitValue.createPercentArray(new float[]{60, 40}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Table Headers
            table.addHeaderCell(new Cell()
                    .setBackgroundColor(PRIMARY_COLOR)
                    .setFont(boldFont)
                    .setFontColor(ColorConstants.WHITE)
                    .setPadding(8)
                    .add(new Paragraph("Metric").setTextAlignment(TextAlignment.LEFT)));
            table.addHeaderCell(new Cell()
                    .setBackgroundColor(PRIMARY_COLOR)
                    .setFont(boldFont)
                    .setFontColor(ColorConstants.WHITE)
                    .setPadding(8)
                    .add(new Paragraph("Value").setTextAlignment(TextAlignment.RIGHT)));

            // Table Rows
            addTableRow(table, "Total Loans Offered", String.valueOf(loans.size()), font, 0);
            addTableRow(table, "Rejected Loans",
                    String.valueOf(loans.stream().filter(l -> l.getStatus().getStatusName().equals("REJECTED")).count()),
                    font, 1);
            addTableRow(table, "Loans In Process",
                    String.valueOf(loans.stream().filter(l -> l.getStatus().getStatusName().equals("PENDING") || l.getStatus().getStatusName().equals("UNDER_REVIEW")).count()),
                    font, 0);
            addTableRow(table, "Amount Disbursed",
                    loans.stream()
                            .filter(l -> l.getStatus().getStatusName().equals("APPROVED") || (l.getNpaStatus() != null && l.getNpaStatus().getStatusName().equals("NPA")))
                            .map(Loan::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .toString(),
                    font, 1);
            addTableRow(table, "Customers Entertained",
                    String.valueOf(customerRepository.findByLoanOfficerIdAndIsDeletedFalse(loanOfficerId).stream().map(Customer::getId).distinct().count()),
                    font, 0);
            addTableRow(table, "NPAs",
                    String.valueOf(activeLoans.stream()
                            .filter(l -> l.getNpaStatus() != null && l.getNpaStatus().getStatusName().equals("NPA"))
                            .count()),
                    font, 1);
            addTableRow(table, "Red Flagged Customers",
                    String.valueOf(customerRepository.findByLoanOfficerIdAndIsDeletedFalse(loanOfficerId).stream().filter(Customer::isRedFlagged).count()),
                    font, 0);

            document.add(table);

            // Footer (Page Number)
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, event -> {
                PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
                PdfDocument pdfDoc = docEvent.getDocument();
                PdfCanvas canvas = new PdfCanvas(pdfDoc.getLastPage());
                canvas.beginText()
                        .setFontAndSize(font, 10)
                        .moveText(550, 30)
                        .showText("Page " + pdfDoc.getPageNumber(pdfDoc.getLastPage()))
                        .endText();
                canvas.release();
            });

            document.close();
            logger.info("PDF generated successfully for loan officer ID: {}", loanOfficerId);
            return baos.toByteArray();
        } catch (Exception e) {
            logger.error("Failed to generate PDF for loan officer ID: {}", loanOfficerId, e);
            throw new ReportGenerationException("Failed to generate PDF report: " + e.getMessage());
        }
    }

    private void addTableRow(Table table, String metric, String value, PdfFont font, int rowIndex) {
        Cell metricCell = new Cell()
                .setPadding(6)
                .setBackgroundColor(rowIndex % 2 == 0 ? ColorConstants.WHITE : LIGHT_GRAY)
                .add(new Paragraph(metric).setFont(font).setFontSize(12));
        Cell valueCell = new Cell()
                .setPadding(6)
                .setBackgroundColor(rowIndex % 2 == 0 ? ColorConstants.WHITE : LIGHT_GRAY)
                .add(new Paragraph(value).setFont(font).setFontSize(12).setTextAlignment(TextAlignment.RIGHT));
        table.addCell(metricCell);
        table.addCell(valueCell);
    }
}