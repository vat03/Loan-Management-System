package com.aurionpro.lms.controller;

import com.aurionpro.lms.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("/loan-officer/{loanOfficerId}")
	public ResponseEntity<Map<String, Object>> generateLoanOfficerReport(@PathVariable int loanOfficerId) {
		Map<String, Object> report = reportService.generateLoanOfficerReport(loanOfficerId);
		return ResponseEntity.ok(report);
	}
}