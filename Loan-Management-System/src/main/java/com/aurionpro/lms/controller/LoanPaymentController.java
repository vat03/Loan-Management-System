//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.service.LoanPaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/loan-payments")
//public class LoanPaymentController {
//
//	@Autowired
//	private LoanPaymentService loanPaymentService;
//
//	@PostMapping("/makePayment/loan/{loanId}")
//	public ResponseEntity<Void> createLoanPayments(@PathVariable int loanId) {
//		loanPaymentService.createLoanPayments(loanId);
//		return ResponseEntity.status(201).build();
//	}
//
//	@GetMapping("/customer/{customerId}/pending")
//	public ResponseEntity<List<LoanResponseDTO>> getPendingPaymentsByCustomerId(@PathVariable int customerId) {
//		List<LoanResponseDTO> responseDTOs = loanPaymentService.getPendingPaymentsByCustomerId(customerId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.service.LoanPaymentService;
//
//@RestController
//@RequestMapping("/api/loan-payments")
//public class LoanPaymentController {
//
//	@Autowired
//	private LoanPaymentService loanPaymentService;
//
//	@PostMapping("/{loanPaymentId}/repay")
//	public ResponseEntity<String> repayLoanPayment(@PathVariable int loanPaymentId) {
//		loanPaymentService.processRepayment(loanPaymentId);
//		return ResponseEntity.ok("Payment processed successfully");
//	}
//
//	@GetMapping("/loan/{loanId}")
//	public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@PathVariable int loanId) {
//		List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId);
//		return ResponseEntity.ok(payments);
//	}
//	
//	@GetMapping("/customer/{customerId}/pending")
//	public ResponseEntity<List<LoanResponseDTO>> getPendingPaymentsByCustomerId(@PathVariable int customerId) {
//		List<LoanResponseDTO> responseDTOs = loanPaymentService.getPendingPaymentsByCustomerId(customerId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
//import com.aurionpro.lms.service.LoanPaymentService;
//import com.aurionpro.lms.service.PaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/loan-payments")
//public class LoanPaymentController {
//
//	@Autowired
//	private LoanPaymentService loanPaymentService;
//
//	@Autowired
//	private PaymentService paymentService;
//
//	@PostMapping("/{loanPaymentId}/repay")
//	public ResponseEntity<String> repayLoanPayment(@PathVariable int loanPaymentId) throws Exception {
//		BigDecimal amount = loanPaymentService.getPaymentAmount(loanPaymentId); 
//		String orderId = paymentService.createPaymentOrder(loanPaymentId, amount);
//		return ResponseEntity.ok(orderId);
//	}
//
//	@GetMapping("/loan/{loanId}")
//	public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@PathVariable int loanId) {
//		List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId);
//		return ResponseEntity.ok(payments);
//	}
//}

package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
import com.aurionpro.lms.service.LoanPaymentService;
import com.aurionpro.lms.service.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/loan-payments")
public class LoanPaymentController {

	@Autowired
	private LoanPaymentService loanPaymentService;

	@Autowired
	private PaymentService paymentService;

	// Initiate payment (creates Razorpay order)
	@PostMapping("/{loanPaymentId}/repay")
	public ResponseEntity<String> repayLoanPayment(@PathVariable int loanPaymentId) throws Exception {
		BigDecimal amount = loanPaymentService.getPaymentAmount(loanPaymentId);
		String orderId = paymentService.createPaymentOrder(loanPaymentId, amount);
		// For Postman testing, return orderId directly
		return ResponseEntity.ok(orderId);
	}

	// Simulate payment completion and verification
	@PostMapping("/complete")
	public ResponseEntity<String> completePayment(@RequestBody JSONObject paymentData) throws Exception {
		String orderId = paymentData.getString("orderId");
		String paymentId = paymentData.getString("paymentId");
		String signature = paymentData.getString("signature");
		int loanPaymentId = paymentData.getInt("loanPaymentId");

		boolean isValid = paymentService.verifyPayment(orderId, paymentId, signature);
		if (isValid) {
			loanPaymentService.processRepayment(loanPaymentId);
			return ResponseEntity.ok("Payment processed successfully");
		} else {
			return ResponseEntity.status(400).body("Payment verification failed");
		}
	}

	@GetMapping("/loan/{loanId}")
	public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@PathVariable int loanId) {
		List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId);
		return ResponseEntity.ok(payments);
	}
}