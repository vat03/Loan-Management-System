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

//upar wala without razorpay

//package com.aurionpro.lms.controller;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
//import com.aurionpro.lms.dto.PaymentCompletionRequestDto;
//import com.aurionpro.lms.service.LoanPaymentService;
//import com.aurionpro.lms.service.PaymentService;
//
//@RestController
//@RequestMapping("/api/loan-payments")
//public class LoanPaymentController {
//
//    private static final Logger logger = LoggerFactory.getLogger(LoanPaymentController.class);
//
//    @Autowired
//    private LoanPaymentService loanPaymentService;
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @PostMapping("/{loanPaymentId}/repay")
//    public ResponseEntity<String> repayLoanPayment(@PathVariable int loanPaymentId) throws Exception {
//        logger.info("Initiating repayment for loanPaymentId: {}", loanPaymentId);
//        BigDecimal amount = loanPaymentService.getPaymentAmount(loanPaymentId);
//        logger.info("Payment amount calculated: {} INR", amount);
//        String orderId = paymentService.createPaymentOrder(loanPaymentId, amount);
//        logger.info("Repayment order created: {}", orderId);
//        return ResponseEntity.ok(orderId);
//    }
//
//    @PostMapping("/complete")
//    public ResponseEntity<String> completePayment(@RequestBody PaymentCompletionRequestDto paymentCompletionRequestDto) throws Exception {
//        String orderId = paymentCompletionRequestDto.getOrderId();
//        String paymentId = paymentCompletionRequestDto.getPaymentId();
//        String signature = paymentCompletionRequestDto.getSignature();
//        int loanPaymentId = paymentCompletionRequestDto.getLoanPaymentId();
//
//        logger.info("Completing payment for loanPaymentId: {}, orderId: {}", loanPaymentId, orderId);
//        boolean isValid = paymentService.verifyPayment(orderId, paymentId, signature);
//        if (isValid) {
//            loanPaymentService.processRepayment(loanPaymentId);
//            logger.info("Payment processed successfully for loanPaymentId: {}", loanPaymentId);
//            return ResponseEntity.ok("Payment processed successfully");
//        } else {
//            logger.warn("Payment verification failed for orderId: {}", orderId);
//            return ResponseEntity.status(400).body("Payment verification failed");
//        }
//    }
//
//    @GetMapping("/loan/{loanId}")
//    public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@PathVariable int loanId) {
//        List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId);
//        return ResponseEntity.ok(payments);
//    }
//}

// upar wala with razorpay and logging

//package com.aurionpro.lms.controller;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
//import com.aurionpro.lms.dto.PaymentCompletionRequestDto;
//import com.aurionpro.lms.service.LoanPaymentService;
//import com.aurionpro.lms.service.PaymentService;
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
//	@PostMapping("/complete")
//	public ResponseEntity<String> completePayment(@RequestBody PaymentCompletionRequestDto paymentCompletionRequestDto)
//			throws Exception {
//		String orderId = paymentCompletionRequestDto.getOrderId();
//		String paymentId = paymentCompletionRequestDto.getPaymentId();
//		String signature = paymentCompletionRequestDto.getSignature();
//		int loanPaymentId = paymentCompletionRequestDto.getLoanPaymentId();
//
//		boolean isValid = paymentService.verifyPayment(orderId, paymentId, signature);
//		if (isValid) {
//			loanPaymentService.processRepayment(loanPaymentId);
//			return ResponseEntity.ok("Payment processed successfully");
//		} else {
//			return ResponseEntity.status(400).body("Payment verification failed");
//		}
//	}
//
//	@GetMapping("/loan/{loanId}")
//	public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@PathVariable int loanId) {
//		List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId);
//		return ResponseEntity.ok(payments);
//	}
//}

// upar wala for testing

//package com.aurionpro.lms.controller;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
//import com.aurionpro.lms.dto.PaymentCompletionRequestDto;
//import com.aurionpro.lms.service.LoanPaymentService;
//import com.aurionpro.lms.service.PaymentService;
//
//@RestController
//@RequestMapping("/api/loan-payments")
//@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
//		RequestMethod.DELETE, RequestMethod.OPTIONS })
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
//	@PostMapping("/complete")
//	public ResponseEntity<String> completePayment(@RequestBody PaymentCompletionRequestDto paymentCompletionRequestDto)
//			throws Exception {
//		String orderId = paymentCompletionRequestDto.getOrderId();
//		String paymentId = paymentCompletionRequestDto.getPaymentId();
//		String signature = paymentCompletionRequestDto.getSignature();
//		int loanPaymentId = paymentCompletionRequestDto.getLoanPaymentId();
//
//		boolean isValid = paymentService.verifyPayment(orderId, paymentId, signature);
//		if (isValid) {
//			loanPaymentService.processRepayment(loanPaymentId);
//			return ResponseEntity.ok("Payment processed successfully");
//		} else {
//			return ResponseEntity.status(400).body("Payment verification failed");
//		}
//	}
//
//	@GetMapping("/loan/{loanId}")
//	public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@PathVariable int loanId,
//			@RequestParam(required = false) String status) {
//		List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId, status);
//		return ResponseEntity.ok(payments);
//	}
//
//	@GetMapping("/getPaymentDetails/{loanPaymentId}")
//	public ResponseEntity<LoanPaymentResponseDTO> getPaymentDetails(@PathVariable int loanPaymentId) {
//		LoanPaymentResponseDTO payment = loanPaymentService.getPaymentDetails(loanPaymentId);
//		return ResponseEntity.ok(payment);
//	}
//
//	@GetMapping("/totalPaymentAmount/{loanPaymentId}/amount")
//	public ResponseEntity<BigDecimal> getTotalPaymentAmount(@PathVariable int loanPaymentId) {
//		BigDecimal amount = loanPaymentService.getPaymentAmount(loanPaymentId);
//		return ResponseEntity.ok(amount);
//	}
//
//	@PostMapping("/npa/approve/{loanId}")
//	public ResponseEntity<String> approveNpaStatus(@PathVariable int loanId, @RequestParam boolean approve) {
//		loanPaymentService.approveNpaStatus(loanId, approve);
//		return ResponseEntity.ok(approve ? "NPA status approved" : "NPA status rejected");
//	}
//
//	@PostMapping("/check-npa")
//	public ResponseEntity<String> checkAndFlagNpaLoans() {
//		loanPaymentService.checkAndFlagNpaLoans();
//		return ResponseEntity.ok("NPA check completed");
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
//import com.aurionpro.lms.dto.PaymentCompletionRequestDto;
//import com.aurionpro.lms.exception.InvalidInputException;
//import com.aurionpro.lms.exception.PaymentProcessingException;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.service.LoanPaymentService;
//import com.aurionpro.lms.service.PaymentService;
//
//import jakarta.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/loan-payments")
//@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
//		RequestMethod.DELETE, RequestMethod.OPTIONS })
//public class LoanPaymentController {
//
//	@Autowired
//	private LoanPaymentService loanPaymentService;
//
//	@Autowired
//	private PaymentService paymentService;
//
//	@PostMapping("/{loanPaymentId}/repay")
//	public ResponseEntity<String> repayLoanPayment(@Valid @PathVariable int loanPaymentId) {
//		try {
//			BigDecimal amount = loanPaymentService.getPaymentAmount(loanPaymentId);
//			String orderId = paymentService.createPaymentOrder(loanPaymentId, amount);
//			return ResponseEntity.ok(orderId);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (PaymentProcessingException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error creating payment order for ID: " + loanPaymentId, e);
//		}
//	}
//
//	@PostMapping("/complete")
//	public ResponseEntity<String> completePayment(
//			@Valid @RequestBody PaymentCompletionRequestDto paymentCompletionRequestDto) {
//		try {
//			if (paymentCompletionRequestDto.getOrderId() == null || paymentCompletionRequestDto.getPaymentId() == null
//					|| paymentCompletionRequestDto.getSignature() == null) {
//				throw new InvalidInputException("Missing required payment details (orderId, paymentId, or signature)");
//			}
//			boolean isValid = paymentService.verifyPayment(paymentCompletionRequestDto.getOrderId(),
//					paymentCompletionRequestDto.getPaymentId(), paymentCompletionRequestDto.getSignature());
//			if (isValid) {
//				loanPaymentService.processRepayment(paymentCompletionRequestDto.getLoanPaymentId());
//				return ResponseEntity.ok("Payment processed successfully");
//			} else {
//				throw new PaymentProcessingException("Payment verification failed");
//			}
//		} catch (ResourceNotFoundException | InvalidInputException | PaymentProcessingException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error completing payment", e);
//		}
//	}
//
//	@GetMapping("/loan/{loanId}")
//	public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@Valid @PathVariable int loanId,
//			@RequestParam(required = false) String status) {
//		try {
//			List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId, status);
//			return ResponseEntity.ok(payments);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching payments for loan ID: " + loanId, e);
//		}
//	}
//
//	@GetMapping("/getPaymentDetails/{loanPaymentId}")
//	public ResponseEntity<LoanPaymentResponseDTO> getPaymentDetails(@Valid @PathVariable int loanPaymentId) {
//		try {
//			LoanPaymentResponseDTO payment = loanPaymentService.getPaymentDetails(loanPaymentId);
//			return ResponseEntity.ok(payment);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching payment details for ID: " + loanPaymentId, e);
//		}
//	}
//
//	@GetMapping("/totalPaymentAmount/{loanPaymentId}/amount")
//	public ResponseEntity<BigDecimal> getTotalPaymentAmount(@Valid @PathVariable int loanPaymentId) {
//		try {
//			BigDecimal amount = loanPaymentService.getPaymentAmount(loanPaymentId);
//			return ResponseEntity.ok(amount);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching total payment amount for ID: " + loanPaymentId, e);
//		}
//	}
//
//	@PostMapping("/npa/approve/{loanId}")
//	public ResponseEntity<String> approveNpaStatus(@Valid @PathVariable int loanId, @Valid  @RequestParam boolean approve) {
//		try {
//			loanPaymentService.approveNpaStatus(loanId, approve);
//			return ResponseEntity.ok(approve ? "NPA status approved" : "NPA status rejected");
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error approving NPA status for loan ID: " + loanId, e);
//		}
//	}
//
//	@PostMapping("/check-npa")
//	public ResponseEntity<String> checkAndFlagNpaLoans() {
//		try {
//			loanPaymentService.checkAndFlagNpaLoans();
//			return ResponseEntity.ok("NPA check completed");
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error checking and flagging NPA loans", e);
//		}
//	}
//}

package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
import com.aurionpro.lms.dto.PaymentCompletionRequestDto;
import com.aurionpro.lms.exception.PaymentProcessingException;
import com.aurionpro.lms.service.LoanPaymentService;
import com.aurionpro.lms.service.PaymentService;
import com.razorpay.RazorpayException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/loan-payments")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE, RequestMethod.OPTIONS })
public class LoanPaymentController {

	@Autowired
	private LoanPaymentService loanPaymentService;

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/{loanPaymentId}/repay")
	public ResponseEntity<String> repayLoanPayment(@PathVariable int loanPaymentId) throws RazorpayException {
		BigDecimal amount = loanPaymentService.getPaymentAmount(loanPaymentId);
		String orderId = paymentService.createPaymentOrder(loanPaymentId, amount);
		return ResponseEntity.ok(orderId);
	}

	@PostMapping("/complete")
	public ResponseEntity<String> completePayment(
			@Valid @RequestBody PaymentCompletionRequestDto paymentCompletionRequestDto) throws RazorpayException {
		boolean isValid = paymentService.verifyPayment(paymentCompletionRequestDto.getOrderId(),
				paymentCompletionRequestDto.getPaymentId(), paymentCompletionRequestDto.getSignature());
		if (isValid) {
			loanPaymentService.processRepayment(paymentCompletionRequestDto.getLoanPaymentId());
			return ResponseEntity.ok("Payment processed successfully");
		} else {
			throw new PaymentProcessingException("Payment verification failed");
		}
	}

	@GetMapping("/loan/{loanId}")
	public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@PathVariable int loanId,
			@RequestParam(required = false) String status) {
		List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId, status);
		return ResponseEntity.ok(payments);
	}

	@GetMapping("/getPaymentDetails/{loanPaymentId}")
	public ResponseEntity<LoanPaymentResponseDTO> getPaymentDetails(@PathVariable int loanPaymentId) {
		LoanPaymentResponseDTO payment = loanPaymentService.getPaymentDetails(loanPaymentId);
		return ResponseEntity.ok(payment);
	}

	@GetMapping("/totalPaymentAmount/{loanPaymentId}/amount")
	public ResponseEntity<BigDecimal> getTotalPaymentAmount(@PathVariable int loanPaymentId) {
		BigDecimal amount = loanPaymentService.getPaymentAmount(loanPaymentId);
		return ResponseEntity.ok(amount);
	}

	@PostMapping("/npa/approve/{loanId}")
	public ResponseEntity<String> approveNpaStatus(@PathVariable int loanId, @RequestParam boolean approve) {
		loanPaymentService.approveNpaStatus(loanId, approve);
		return ResponseEntity.ok(approve ? "NPA status approved" : "NPA status rejected");
	}

	@PostMapping("/check-npa")
	public ResponseEntity<String> checkAndFlagNpaLoans() {
		loanPaymentService.checkAndFlagNpaLoans();
		return ResponseEntity.ok("NPA check completed");
	}
}