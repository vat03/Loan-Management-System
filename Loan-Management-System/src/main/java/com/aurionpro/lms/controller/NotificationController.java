//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.service.NotificationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/notifications")
//public class NotificationController {
//
//	@Autowired
//	private NotificationService notificationService;
//
//	@PostMapping("/loan-status/{loanId}")
//	public ResponseEntity<Void> sendLoanStatusEmail(@PathVariable int loanId, @RequestParam String status) {
//		notificationService.sendLoanStatusEmail(loanId, status);
//		return ResponseEntity.noContent().build();
//	}
//
//	@PostMapping("/payment-reminder/{loanPaymentId}")
//	public ResponseEntity<Void> sendPaymentReminderEmail(@PathVariable int loanPaymentId) {
//		notificationService.sendPaymentReminderEmail(loanPaymentId);
//		return ResponseEntity.noContent().build();
//	}
//}

package com.aurionpro.lms.controller;

import com.aurionpro.lms.exception.NotificationException;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.service.NotificationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@PostMapping("/loan-status/{loanId}")
	public ResponseEntity<Void> sendLoanStatusEmail(@Valid @PathVariable int loanId, @Valid @RequestParam String status) {
		try {
			notificationService.sendLoanStatusEmail(loanId, status);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException | NotificationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error sending loan status email for loan ID: " + loanId, e);
		}
	}

	@PostMapping("/payment-reminder/{loanPaymentId}")
	public ResponseEntity<Void> sendPaymentReminderEmail(@Valid @PathVariable int loanPaymentId) {
		try {
			notificationService.sendPaymentReminderEmail(loanPaymentId);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException | NotificationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error sending payment reminder for ID: " + loanPaymentId, e);
		}
	}
}