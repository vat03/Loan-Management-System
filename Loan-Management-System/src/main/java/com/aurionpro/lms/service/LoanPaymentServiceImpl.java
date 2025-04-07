//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanPayment;
//import com.aurionpro.lms.repository.LoanPaymentRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode; 
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanPaymentServiceImpl implements LoanPaymentService {
//
//    @Autowired
//    private LoanRepository loanRepository;
//
//    @Autowired
//    private LoanPaymentRepository loanPaymentRepository;
//
//    private ModelMapper mapper;
//    
//    private LoanPaymentServiceImpl() {
//    	this.mapper = new ModelMapper();
//    }
//
//    @Override
//    public void createLoanPayments(int loanId) {
//        Optional<Loan> loanOpt = loanRepository.findById(loanId);
//        if (loanOpt.isEmpty()) {
//            throw new RuntimeException("Loan not found with ID: " + loanId);
//        }
//        Loan loan = loanOpt.get();
//
//        int tenureMonths = loan.getLoanScheme().getTenureMonths();
//        BigDecimal monthlyAmount = loan.getAmount().divide(BigDecimal.valueOf(tenureMonths), 2, RoundingMode.HALF_UP);
//        LocalDate startDate = loan.getApplicationDate();
//
//        for (int i = 1; i <= tenureMonths; i++) {
//            LoanPayment payment = new LoanPayment();
//            payment.setLoan(loan);
//            payment.setAmount(monthlyAmount);
//            payment.setDueDate(startDate.plusMonths(i));
//            payment.setStatus("PENDING");
//            loanPaymentRepository.save(payment);
//        }
//    }
//
//    @Override
//    public List<LoanResponseDTO> getPendingPaymentsByCustomerId(int customerId) {
//        List<Loan> loans = loanRepository.findByCustomerId(customerId);
//        List<LoanPayment> pendingPayments = loans.stream()
//                .flatMap(loan -> loanPaymentRepository.findByLoanLoanId(loan.getLoanId()).stream())
//                .filter(payment -> "PENDING".equals(payment.getStatus()))
//                .collect(Collectors.toList());
//
//        //ModelMapper mapper = new ModelMapper();
//        mapper.typeMap(Loan.class, LoanResponseDTO.class)
//              .addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
//              .addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
//              .addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
//        return loans.stream()
//                .filter(loan -> pendingPayments.stream().anyMatch(p -> p.getLoan().getLoanId() == loan.getLoanId()))
//                .map(loan -> mapper.map(loan, LoanResponseDTO.class))
//                .collect(Collectors.toList());
//    }
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanPayment;
//import com.aurionpro.lms.repository.LoanPaymentRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanPaymentServiceImpl implements LoanPaymentService {
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private LoanPaymentRepository loanPaymentRepository;
//
//	@Override
//	public void createLoanPayments(int loanId) {
//		Optional<Loan> loanOpt = loanRepository.findById(loanId);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + loanId);
//		}
//		Loan loan = loanOpt.get();
//
//		int tenureMonths = loan.getLoanScheme().getTenureMonths();
//		BigDecimal monthlyAmount = loan.getAmount().divide(BigDecimal.valueOf(tenureMonths), 2, RoundingMode.HALF_UP);
//		LocalDate startDate = loan.getApplicationDate();
//
//		for (int i = 1; i <= tenureMonths; i++) {
//			LoanPayment payment = new LoanPayment();
//			payment.setLoan(loan);
//			payment.setAmount(monthlyAmount);
//			payment.setDueDate(startDate.plusMonths(i));
//			payment.setStatus("PENDING");
//			loanPaymentRepository.save(payment);
//		}
//	}
//
//	@Override
//	public List<LoanResponseDTO> getPendingPaymentsByCustomerId(int customerId) {
//		List<Loan> loans = loanRepository.findByCustomerId(customerId);
//		List<LoanPayment> pendingPayments = loans.stream()
//				.flatMap(loan -> loanPaymentRepository.findByLoanLoanId(loan.getLoanId()).stream())
//				.filter(payment -> "PENDING".equals(payment.getStatus())).collect(Collectors.toList());
//
//		return loans.stream()
//				.filter(loan -> pendingPayments.stream().anyMatch(p -> p.getLoan().getLoanId() == loan.getLoanId()))
//				.map(loan -> {
//					LoanResponseDTO dto = new LoanResponseDTO();
//					dto.setLoanId(loan.getLoanId());
//					dto.setAmount(loan.getAmount());
//					dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//					dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//					dto.setApplicationDate(loan.getApplicationDate());
//					dto.setDueDate(loan.getDueDate());
//					dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//					dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//					return dto;
//				}).collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanPayment;
//import com.aurionpro.lms.repository.LoanPaymentRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//
//@Service
//public class LoanPaymentServiceImpl implements LoanPaymentService {
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private LoanPaymentRepository loanPaymentRepository;
//
//	@Autowired
//	private NotificationService notificationService;
//
//	@Override
//	public void createLoanPayments(int loanId) {
//		Optional<Loan> loanOpt = loanRepository.findById(loanId);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + loanId);
//		}
//		Loan loan = loanOpt.get();
//
//		int tenureMonths = loan.getLoanScheme().getTenureMonths();
//		BigDecimal principal = loan.getAmount();
//		BigDecimal interestRate = loan.getLoanScheme().getInterestRate(); // Annual rate (e.g., 5.0%)
//		BigDecimal monthlyInterestRate = interestRate.divide(BigDecimal.valueOf(1200), 6, RoundingMode.HALF_UP);
//		BigDecimal penaltyPercentage = BigDecimal.valueOf(2); // 2% penalty for late payment
//
//		// EMI formula: [P * r * (1 + r)^n] / [(1 + r)^n - 1]
//		BigDecimal onePlusR = BigDecimal.ONE.add(monthlyInterestRate);
//		BigDecimal onePlusRPowerN = onePlusR.pow(tenureMonths);
//		BigDecimal emi = principal.multiply(monthlyInterestRate).multiply(onePlusRPowerN)
//				.divide(onePlusRPowerN.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
//
//		LocalDate startDate = loan.getApplicationDate();
//
//		for (int i = 1; i <= tenureMonths; i++) {
//			LoanPayment payment = new LoanPayment();
//			payment.setLoan(loan);
//			payment.setAmount(emi);
//			payment.setDueDate(startDate.plusMonths(i));
//			payment.setStatus("PENDING");
//			payment.setInterestRate(interestRate);
//			payment.setPenaltyPercentage(penaltyPercentage);
//			loanPaymentRepository.save(payment);
//		}
//	}
//
//	@Override
//	public List<LoanResponseDTO> getPendingPaymentsByCustomerId(int customerId) {
//		List<Loan> loans = loanRepository.findByCustomerId(customerId);
//		List<LoanPayment> pendingPayments = loans.stream()
//				.flatMap(loan -> loanPaymentRepository.findByLoanLoanId(loan.getLoanId()).stream())
//				.filter(payment -> "PENDING".equals(payment.getStatus())).collect(Collectors.toList());
//
//		return loans.stream()
//				.filter(loan -> pendingPayments.stream().anyMatch(p -> p.getLoan().getLoanId() == loan.getLoanId()))
//				.map(loan -> {
//					LoanResponseDTO dto = new LoanResponseDTO();
//					dto.setLoanId(loan.getLoanId());
//					dto.setAmount(loan.getAmount());
//					dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//					dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//					dto.setApplicationDate(loan.getApplicationDate());
//					dto.setDueDate(loan.getDueDate());
//					dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//					dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//					return dto;
//				}).collect(Collectors.toList());
//	}
//
//	@Override
//	public void processRepayment(int loanPaymentId) {
//		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
//		if (paymentOpt.isEmpty()) {
//			throw new RuntimeException("Loan payment not found with ID: " + loanPaymentId);
//		}
//		LoanPayment payment = paymentOpt.get();
//
//		if (!"PENDING".equals(payment.getStatus())) {
//			throw new RuntimeException("Payment with ID: " + loanPaymentId + " is already processed");
//		}
//
//		LocalDate today = LocalDate.now();
//		BigDecimal totalAmountPaid = payment.getAmount();
//
//		// Check if payment is late and apply penalty
//		if (today.isAfter(payment.getDueDate())) {
//			BigDecimal penalty = payment.getAmount().multiply(payment.getPenaltyPercentage())
//					.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
//			totalAmountPaid = totalAmountPaid.add(penalty);
//			payment.setPenaltyAmount(penalty);
//			payment.setStatus("PAID_LATE");
//		} else {
//			payment.setPenaltyAmount(BigDecimal.ZERO);
//			payment.setStatus("PAID");
//		}
//
//		loanPaymentRepository.save(payment);
//
//		// Send confirmation email
//		notificationService.sendPaymentConfirmationEmail(loanPaymentId, totalAmountPaid);
//	}
//
//	@Override
//	public List<LoanPaymentResponseDTO> getPaymentsByLoanId(int loanId) {
//		List<LoanPayment> payments = loanPaymentRepository.findByLoanLoanId(loanId);
//		if (payments.isEmpty()) {
//			throw new RuntimeException("No payments found for Loan ID: " + loanId);
//		}
//
//		return payments.stream().map(payment -> {
//			LoanPaymentResponseDTO dto = new LoanPaymentResponseDTO();
//			dto.setId(payment.getId());
//			dto.setLoanId(payment.getLoan().getLoanId());
//			dto.setAmount(payment.getAmount());
//			dto.setDueDate(payment.getDueDate());
//			dto.setStatus(payment.getStatus());
//			dto.setPenaltyAmount(payment.getPenaltyAmount());
//			return dto;
//		}).collect(Collectors.toList());
//	}
//
//	@Override
//	public BigDecimal getPaymentAmount(int loanPaymentId) {
//		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
//		if (paymentOpt.isEmpty()) {
//			throw new RuntimeException("Loan payment not found with ID: " + loanPaymentId);
//		}
//		LoanPayment payment = paymentOpt.get();
//
//		BigDecimal totalAmount = payment.getAmount();
//		LocalDate today = LocalDate.now();
//
//		if ("PENDING".equals(payment.getStatus()) && today.isAfter(payment.getDueDate())) {
//			BigDecimal penalty = payment.getAmount().multiply(payment.getPenaltyPercentage())
//					.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
//			totalAmount = totalAmount.add(penalty);
//		}
//		return totalAmount;
//	}
//}

// upar wala for testing

package com.aurionpro.lms.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.entity.Loan;
import com.aurionpro.lms.entity.LoanPayment;
import com.aurionpro.lms.repository.LoanPaymentRepository;
import com.aurionpro.lms.repository.LoanRepository;

@Service
public class LoanPaymentServiceImpl implements LoanPaymentService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private LoanPaymentRepository loanPaymentRepository;

	@Autowired
	private NotificationService notificationService;

	@Override
	public void createLoanPayments(int loanId) {
		Optional<Loan> loanOpt = loanRepository.findById(loanId);
		if (loanOpt.isEmpty()) {
			throw new RuntimeException("Loan not found with ID: " + loanId);
		}
		Loan loan = loanOpt.get();

		int tenureMonths = loan.getLoanScheme().getTenureMonths();
		BigDecimal principal = loan.getAmount();
		BigDecimal interestRate = loan.getLoanScheme().getInterestRate(); // Annual rate (e.g., 5.0%)
		BigDecimal monthlyInterestRate = interestRate.divide(BigDecimal.valueOf(1200), 6, RoundingMode.HALF_UP);
		BigDecimal penaltyPercentage = BigDecimal.valueOf(2); // 2% penalty for late payment

		// EMI formula: [P * r * (1 + r)^n] / [(1 + r)^n - 1]
		BigDecimal onePlusR = BigDecimal.ONE.add(monthlyInterestRate);
		BigDecimal onePlusRPowerN = onePlusR.pow(tenureMonths);
		BigDecimal emi = principal.multiply(monthlyInterestRate).multiply(onePlusRPowerN)
				.divide(onePlusRPowerN.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

		LocalDate startDate = loan.getApplicationDate();

		for (int i = 1; i <= tenureMonths; i++) {
			LoanPayment payment = new LoanPayment();
			payment.setLoan(loan);
			payment.setAmount(emi);
			payment.setDueDate(startDate.plusMonths(i));
			payment.setStatus("PENDING");
			payment.setInterestRate(interestRate);
			payment.setPenaltyPercentage(penaltyPercentage);
			loanPaymentRepository.save(payment);
		}
	}

	@Override
	public List<LoanResponseDTO> getPendingPaymentsByCustomerId(int customerId) {
		List<Loan> loans = loanRepository.findByCustomerId(customerId);
		List<LoanPayment> pendingPayments = loans.stream()
				.flatMap(loan -> loanPaymentRepository.findByLoanLoanId(loan.getLoanId()).stream())
				.filter(payment -> "PENDING".equals(payment.getStatus())).collect(Collectors.toList());

		return loans.stream()
				.filter(loan -> pendingPayments.stream().anyMatch(p -> p.getLoan().getLoanId() == loan.getLoanId()))
				.map(loan -> {
					LoanResponseDTO dto = new LoanResponseDTO();
					dto.setLoanId(loan.getLoanId());
					dto.setAmount(loan.getAmount());
					dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
					dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
					dto.setApplicationDate(loan.getApplicationDate());
					dto.setDueDate(loan.getDueDate());
					dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
					dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
					return dto;
				}).collect(Collectors.toList());
	}

	@Override
	public void processRepayment(int loanPaymentId) {
		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
		if (paymentOpt.isEmpty()) {
			throw new RuntimeException("Loan payment not found with ID: " + loanPaymentId);
		}
		LoanPayment payment = paymentOpt.get();

		if (!"PENDING".equals(payment.getStatus())) {
			throw new RuntimeException("Payment with ID: " + loanPaymentId + " is already processed");
		}

		LocalDate today = LocalDate.now();
		BigDecimal totalAmountPaid = payment.getAmount();

		// Check if payment is late and apply penalty
		if (today.isAfter(payment.getDueDate())) {
			BigDecimal penalty = payment.getAmount().multiply(payment.getPenaltyPercentage())
					.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
			totalAmountPaid = totalAmountPaid.add(penalty);
			payment.setPenaltyAmount(penalty);
			payment.setStatus("PAID_LATE");
		} else {
			payment.setPenaltyAmount(BigDecimal.ZERO);
			payment.setStatus("PAID");
		}

		loanPaymentRepository.save(payment);

		// Send confirmation email
		notificationService.sendPaymentConfirmationEmail(loanPaymentId, totalAmountPaid);
	}

	@Override
	public List<LoanPaymentResponseDTO> getPaymentsByLoanId(int loanId) {
		List<LoanPayment> payments = loanPaymentRepository.findByLoanLoanId(loanId);
		if (payments.isEmpty()) {
			throw new RuntimeException("No payments found for Loan ID: " + loanId);
		}

		return payments.stream().map(payment -> {
			LoanPaymentResponseDTO dto = new LoanPaymentResponseDTO();
			dto.setId(payment.getId());
			dto.setLoanId(payment.getLoan().getLoanId());
			dto.setAmount(payment.getAmount());
			dto.setDueDate(payment.getDueDate());
			dto.setStatus(payment.getStatus());
			dto.setPenaltyAmount(payment.getPenaltyAmount());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public BigDecimal getPaymentAmount(int loanPaymentId) {
		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
		if (paymentOpt.isEmpty()) {
			throw new RuntimeException("Loan payment not found with ID: " + loanPaymentId);
		}
		LoanPayment payment = paymentOpt.get();

		BigDecimal totalAmount = payment.getAmount();
		LocalDate today = LocalDate.now();

		if ("PENDING".equals(payment.getStatus()) && today.isAfter(payment.getDueDate())) {
			BigDecimal penalty = payment.getAmount().multiply(payment.getPenaltyPercentage())
					.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
			totalAmount = totalAmount.add(penalty);
		}
		return totalAmount;
	}
}