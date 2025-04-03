package com.aurionpro.lms.controller;

import com.aurionpro.lms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@PostMapping("/loan-status/{loanId}")
	public ResponseEntity<Void> sendLoanStatusEmail(@PathVariable int loanId, @RequestParam String status) {
		notificationService.sendLoanStatusEmail(loanId, status);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/payment-reminder/{loanPaymentId}")
	public ResponseEntity<Void> sendPaymentReminderEmail(@PathVariable int loanPaymentId) {
		notificationService.sendPaymentReminderEmail(loanPaymentId);
		return ResponseEntity.noContent().build();
	}
}