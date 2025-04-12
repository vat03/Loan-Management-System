//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.service.ReportService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/reports")
//public class ReportController {
//
//	@Autowired
//	private ReportService reportService;
//
//	@GetMapping("/loan-officer/{loanOfficerId}")
//	public ResponseEntity<Map<String, Object>> generateLoanOfficerReport(@PathVariable int loanOfficerId) {
//		Map<String, Object> report = reportService.generateLoanOfficerReport(loanOfficerId);
//		return ResponseEntity.ok(report);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.service.ReportService;
//
//import jakarta.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/reports")
//public class ReportController {
//
//	@Autowired
//	private ReportService reportService;
//
//	@GetMapping("/loan-officer/{loanOfficerId}")
//	public ResponseEntity<Map<String, Object>> generateLoanOfficerReport(@Valid @PathVariable int loanOfficerId) {
//		try {
//			Map<String, Object> report = reportService.generateLoanOfficerReport(loanOfficerId);
//			return ResponseEntity.ok(report);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error generating report for loan officer ID: " + loanOfficerId, e);
//		}
//	}
//}

package com.aurionpro.lms.controller;

import com.aurionpro.lms.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin("http://localhost:4200")
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