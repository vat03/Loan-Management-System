package com.aurionpro.lms.controller;

import com.aurionpro.lms.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ReportController {

	@Autowired
	private ReportService reportService;

//	@GetMapping("/loan-officer/{loanOfficerId}")
//	public ResponseEntity<Map<String, Object>> generateLoanOfficerReport(@PathVariable int loanOfficerId) {
//		Map<String, Object> report = reportService.generateLoanOfficerReport(loanOfficerId);
//		return ResponseEntity.ok(report);
//	}
	
	@GetMapping("/loan-officer/{loanOfficerId}")
    public ResponseEntity<byte[]> generateLoanOfficerReport(@PathVariable int loanOfficerId) {
        byte[] pdfContent = reportService.generateLoanOfficerReport(loanOfficerId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "loan_officer_report_" + loanOfficerId + ".pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}