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

//package com.aurionpro.lms.service;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDate;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanPayment;
//import com.aurionpro.lms.entity.LoanStatus;
//import com.aurionpro.lms.repository.CustomerRepository;
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
//	@Autowired
//	private CustomerRepository customerRepository;
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
//		BigDecimal interestRate = loan.getLoanScheme().getInterestRate();
//		BigDecimal monthlyInterestRate = interestRate.divide(BigDecimal.valueOf(1200), 6, RoundingMode.HALF_UP);
//		BigDecimal penaltyPercentage = BigDecimal.valueOf(2);
//
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
//		notificationService.sendPaymentConfirmationEmail(loanPaymentId, totalAmountPaid);
//	}
//
//	@Override
//	public List<LoanPaymentResponseDTO> getPaymentsByLoanId(int loanId, String status) {
//		List<LoanPayment> payments = loanPaymentRepository.findByLoanLoanId(loanId);
//		if (payments.isEmpty()) {
//			throw new RuntimeException("No payments found for Loan ID: " + loanId);
//		}
//		Stream<LoanPayment> paymentStream = payments.stream();
//		if (status != null && !status.isEmpty()) {
//			paymentStream = paymentStream.filter(p -> p.getStatus().equalsIgnoreCase(status));
//		}
//		return paymentStream.map(payment -> {
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
//
//	@Override
//	public LoanPaymentResponseDTO getPaymentDetails(int loanPaymentId) {
//		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
//		if (paymentOpt.isEmpty()) {
//			throw new RuntimeException("Loan payment not found with ID: " + loanPaymentId);
//		}
//		LoanPayment payment = paymentOpt.get();
//		LoanPaymentResponseDTO dto = new LoanPaymentResponseDTO();
//		dto.setId(payment.getId());
//		dto.setLoanId(payment.getLoan().getLoanId());
//		dto.setAmount(payment.getAmount());
//		dto.setDueDate(payment.getDueDate());
//		dto.setStatus(payment.getStatus());
//		dto.setPenaltyAmount(payment.getPenaltyAmount());
//		return dto;
//	}
//
//	@Override
//	public void approveNpaStatus(int loanId, boolean approve) {
//		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
//		if (!loan.getStatus().getStatusName().equals("NPA_PENDING")) {
//			throw new RuntimeException("Loan is not in NPA_PENDING status");
//		}
//
//		if (approve) {
//			LoanStatus npaStatus = new LoanStatus(); // Adjust to fetch from DB
//			npaStatus.setStatusName("NPA");
//			loan.setStatus(npaStatus);
//			loan.getCustomer().setRedFlagged(true);
//			customerRepository.save(loan.getCustomer());
//
//			List<LoanPayment> pendingPayments = loanPaymentRepository.findByLoanLoanId(loanId).stream()
//					.filter(p -> p.getStatus().equals("PENDING")).collect(Collectors.toList());
//			BigDecimal totalDues = pendingPayments.stream().map(this::getPaymentAmountForPayment)
//					.reduce(BigDecimal.ZERO, BigDecimal::add);
//
//			notificationService.sendNpaNotificationToCustomer(loan.getCustomer().getId(), loanId, totalDues);
//		} else {
//			LoanStatus activeStatus = new LoanStatus(); // Adjust to fetch from DB
//			activeStatus.setStatusName("APPROVED"); // Or original status
//			loan.setStatus(activeStatus);
//		}
//		loanRepository.save(loan);
//	}
//
//	@Override
//	public void checkAndFlagNpaLoans() {
//		List<Loan> activeLoans = loanRepository.findAllActiveLoans();
//		for (Loan loan : activeLoans) {
//			List<LoanPayment> payments = loanPaymentRepository.findByLoanLoanId(loan.getLoanId()).stream()
//					.sorted(Comparator.comparing(LoanPayment::getDueDate)).collect(Collectors.toList());
//
//			int consecutiveOverdue = 0;
//			LocalDate today = LocalDate.now();
//			for (LoanPayment payment : payments) {
//				if (payment.getStatus().equals("PENDING") && payment.getDueDate().isBefore(today)) {
//					consecutiveOverdue++;
//					if (consecutiveOverdue >= 3) {
//						LoanStatus npaPendingStatus = new LoanStatus(); // Adjust to fetch from DB
//						npaPendingStatus.setStatusName("NPA_PENDING");
//						loan.setStatus(npaPendingStatus);
//						loanRepository.save(loan);
//						notificationService.sendNpaPendingNotificationToOfficer(loan.getLoanId(),
//								loan.getLoanOfficer().getId());
//						break;
//					}
//				} else if (!payment.getStatus().equals("PENDING")) {
//					consecutiveOverdue = 0; // Reset if paid
//				}
//			}
//		}
//	}
//
//	private BigDecimal getPaymentAmountForPayment(LoanPayment payment) {
//		BigDecimal totalAmount = payment.getAmount();
//		LocalDate today = LocalDate.now();
//		if ("PENDING".equals(payment.getStatus()) && today.isAfter(payment.getDueDate())) {
//			BigDecimal penalty = payment.getAmount().multiply(payment.getPenaltyPercentage())
//					.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
//			totalAmount = totalAmount.add(penalty);
//		}
//		return totalAmount;
//	}
//}/

//package com.aurionpro.lms.service;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDate;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanPayment;
//import com.aurionpro.lms.entity.NpaStatus;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanPaymentRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import com.aurionpro.lms.repository.NpaStatusRepository;
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
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Autowired
//	private NpaStatusRepository npaStatusRepository;
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
//		BigDecimal interestRate = loan.getLoanScheme().getInterestRate();
//		BigDecimal monthlyInterestRate = interestRate.divide(BigDecimal.valueOf(1200), 6, RoundingMode.HALF_UP);
//		BigDecimal penaltyPercentage = BigDecimal.valueOf(2);
//
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
//		notificationService.sendPaymentConfirmationEmail(loanPaymentId, totalAmountPaid);
//	}
//
//	@Override
//	public List<LoanPaymentResponseDTO> getPaymentsByLoanId(int loanId, String status) {
//		List<LoanPayment> payments = loanPaymentRepository.findByLoanLoanId(loanId);
//		if (payments.isEmpty()) {
//			throw new RuntimeException("No payments found for Loan ID: " + loanId);
//		}
//		Stream<LoanPayment> paymentStream = payments.stream();
//		if (status != null && !status.isEmpty()) {
//			paymentStream = paymentStream.filter(p -> p.getStatus().equalsIgnoreCase(status));
//		}
//		return paymentStream.map(payment -> {
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
//
//	@Override
//	public LoanPaymentResponseDTO getPaymentDetails(int loanPaymentId) {
//		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
//		if (paymentOpt.isEmpty()) {
//			throw new RuntimeException("Loan payment not found with ID: " + loanPaymentId);
//		}
//		LoanPayment payment = paymentOpt.get();
//		LoanPaymentResponseDTO dto = new LoanPaymentResponseDTO();
//		dto.setId(payment.getId());
//		dto.setLoanId(payment.getLoan().getLoanId());
//		dto.setAmount(payment.getAmount());
//		dto.setDueDate(payment.getDueDate());
//		dto.setStatus(payment.getStatus());
//		dto.setPenaltyAmount(payment.getPenaltyAmount());
//		return dto;
//	}
//
//	@Override
//	public void approveNpaStatus(int loanId, boolean approve) {
//		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
//		if (loan.getNpaStatus() == null || !loan.getNpaStatus().getStatusName().equals("NPA_PENDING")) {
//			throw new RuntimeException("Loan is not in NPA_PENDING status");
//		}
//
//		if (approve) {
//			NpaStatus npaStatus = npaStatusRepository.findByStatusName("NPA")
//					.orElseThrow(() -> new RuntimeException("NPA status not found in database"));
//			loan.setNpaStatus(npaStatus);
//			loan.getCustomer().setRedFlagged(true);
//			customerRepository.save(loan.getCustomer());
//
//			List<LoanPayment> pendingPayments = loanPaymentRepository.findByLoanLoanId(loanId).stream()
//					.filter(p -> p.getStatus().equals("PENDING")).collect(Collectors.toList());
//			BigDecimal totalDues = pendingPayments.stream().map(this::getPaymentAmountForPayment)
//					.reduce(BigDecimal.ZERO, BigDecimal::add);
//
//			notificationService.sendNpaNotificationToCustomer(loan.getCustomer().getId(), loanId, totalDues);
//		} else {
//			loan.setNpaStatus(null); // Clear NPA status if rejected
//		}
//		loanRepository.save(loan);
//	}
//
//	@Override
//	public void checkAndFlagNpaLoans() {
//		List<Loan> activeLoans = loanRepository.findAllActiveLoans();
//		for (Loan loan : activeLoans) {
//			if (loan.getNpaStatus() != null)
//				continue; // Skip if already flagged
//
//			List<LoanPayment> payments = loanPaymentRepository.findByLoanLoanId(loan.getLoanId()).stream()
//					.sorted(Comparator.comparing(LoanPayment::getDueDate)).collect(Collectors.toList());
//
//			int consecutiveOverdue = 0;
//			LocalDate today = LocalDate.now();
//			for (LoanPayment payment : payments) {
//				if (payment.getStatus().equals("PENDING") && payment.getDueDate().isBefore(today)) {
//					consecutiveOverdue++;
//					if (consecutiveOverdue >= 3) {
//						NpaStatus npaPendingStatus = npaStatusRepository.findByStatusName("NPA_PENDING")
//								.orElseThrow(() -> new RuntimeException("NPA_PENDING status not found in database"));
//						loan.setNpaStatus(npaPendingStatus);
//						loanRepository.save(loan);
//						notificationService.sendNpaPendingNotificationToOfficer(loan.getLoanId(),
//								loan.getLoanOfficer().getId());
//						break;
//					}
//				} else if (!payment.getStatus().equals("PENDING")) {
//					consecutiveOverdue = 0; // Reset if paid
//				}
//			}
//		}
//	}
//
//	private BigDecimal getPaymentAmountForPayment(LoanPayment payment) {
//		BigDecimal totalAmount = payment.getAmount();
//		LocalDate today = LocalDate.now();
//		if ("PENDING".equals(payment.getStatus()) && today.isAfter(payment.getDueDate())) {
//			BigDecimal penalty = payment.getAmount().multiply(payment.getPenaltyPercentage())
//					.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
//			totalAmount = totalAmount.add(penalty);
//		}
//		return totalAmount;
//	}
//}

package com.aurionpro.lms.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.entity.Loan;
import com.aurionpro.lms.entity.LoanPayment;
import com.aurionpro.lms.entity.NpaStatus;
import com.aurionpro.lms.exception.BusinessRuleViolationException;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.LoanPaymentRepository;
import com.aurionpro.lms.repository.LoanRepository;
import com.aurionpro.lms.repository.NpaStatusRepository;

@Service
public class LoanPaymentServiceImpl implements LoanPaymentService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private LoanPaymentRepository loanPaymentRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private NpaStatusRepository npaStatusRepository;

	@Override
	public void createLoanPayments(int loanId) {
		Optional<Loan> loanOpt = loanRepository.findById(loanId);
		if (loanOpt.isEmpty()) {
			throw new ResourceNotFoundException("Loan not found with ID: " + loanId);
		}
		Loan loan = loanOpt.get();

		int tenureMonths = loan.getLoanScheme().getTenureMonths();
		BigDecimal principal = loan.getAmount();
		BigDecimal interestRate = loan.getLoanScheme().getInterestRate();
		BigDecimal monthlyInterestRate = interestRate.divide(BigDecimal.valueOf(1200), 6, RoundingMode.HALF_UP);
		BigDecimal penaltyPercentage = BigDecimal.valueOf(2);

		BigDecimal onePlusR = BigDecimal.ONE.add(monthlyInterestRate);
		BigDecimal onePlusRPowerN = onePlusR.pow(tenureMonths);
		BigDecimal emi = (principal.multiply(monthlyInterestRate).multiply(onePlusRPowerN))
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
		if (loans.isEmpty()) {
			throw new ResourceNotFoundException("No loans found for Customer ID: " + customerId);
		}
		List<LoanPayment> pendingPayments = loans.stream()
				.flatMap(loan -> loanPaymentRepository.findByLoanLoanId(loan.getLoanId()).stream())
				.filter(payment -> "PENDING".equals(payment.getStatus())).collect(Collectors.toList());

		if (pendingPayments.isEmpty()) {
			throw new ResourceNotFoundException("No pending payments found for Customer ID: " + customerId);
		}

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
			throw new ResourceNotFoundException("Loan payment not found with ID: " + loanPaymentId);
		}
		LoanPayment payment = paymentOpt.get();

		if (!"PENDING".equals(payment.getStatus())) {
			throw new BusinessRuleViolationException("Payment with ID: " + loanPaymentId + " is already processed");
		}

		LocalDate today = LocalDate.now();
		BigDecimal totalAmountPaid = payment.getAmount();

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
		notificationService.sendPaymentConfirmationEmail(loanPaymentId, totalAmountPaid);
	}

	@Override
	public List<LoanPaymentResponseDTO> getPaymentsByLoanId(int loanId, String status) {
		List<LoanPayment> payments = loanPaymentRepository.findByLoanLoanId(loanId);
		if (payments.isEmpty()) {
			throw new ResourceNotFoundException("No payments found for Loan ID: " + loanId);
		}
		Stream<LoanPayment> paymentStream = payments.stream();
		if (status != null && !status.isEmpty()) {
			paymentStream = paymentStream.filter(p -> p.getStatus().equalsIgnoreCase(status));
		}
		List<LoanPaymentResponseDTO> result = paymentStream.map(payment -> {
			LoanPaymentResponseDTO dto = new LoanPaymentResponseDTO();
			dto.setId(payment.getId());
			dto.setLoanId(payment.getLoan().getLoanId());
			dto.setAmount(payment.getAmount());
			dto.setDueDate(payment.getDueDate());
			dto.setStatus(payment.getStatus());
			dto.setPenaltyAmount(payment.getPenaltyAmount());
			return dto;
		}).collect(Collectors.toList());
		if (result.isEmpty() && status != null && !status.isEmpty()) {
			throw new ResourceNotFoundException("No payments found for Loan ID: " + loanId + " with status: " + status);
		}
		return result;
	}

	@Override
	public BigDecimal getPaymentAmount(int loanPaymentId) {
		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
		if (paymentOpt.isEmpty()) {
			throw new ResourceNotFoundException("Loan payment not found with ID: " + loanPaymentId);
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

	@Override
	public LoanPaymentResponseDTO getPaymentDetails(int loanPaymentId) {
		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
		if (paymentOpt.isEmpty()) {
			throw new ResourceNotFoundException("Loan payment not found with ID: " + loanPaymentId);
		}
		LoanPayment payment = paymentOpt.get();
		LoanPaymentResponseDTO dto = new LoanPaymentResponseDTO();
		dto.setId(payment.getId());
		dto.setLoanId(payment.getLoan().getLoanId());
		dto.setAmount(payment.getAmount());
		dto.setDueDate(payment.getDueDate());
		dto.setStatus(payment.getStatus());
		dto.setPenaltyAmount(payment.getPenaltyAmount());
		return dto;
	}

	@Override
	public void approveNpaStatus(int loanId, boolean approve) {
		Loan loan = loanRepository.findById(loanId)
				.orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + loanId));
		if (loan.getNpaStatus() == null || !loan.getNpaStatus().getStatusName().equals("NPA_PENDING")) {
			throw new BusinessRuleViolationException("Loan is not in NPA_PENDING status");
		}

		if (approve) {
			NpaStatus npaStatus = npaStatusRepository.findByStatusName("NPA")
					.orElseThrow(() -> new ResourceNotFoundException("NPA status not found in database"));
			loan.setNpaStatus(npaStatus);
			loan.getCustomer().setRedFlagged(true);
			customerRepository.save(loan.getCustomer());

			List<LoanPayment> pendingPayments = loanPaymentRepository.findByLoanLoanId(loanId).stream()
					.filter(p -> p.getStatus().equals("PENDING")).collect(Collectors.toList());
			BigDecimal totalDues = pendingPayments.stream().map(this::getPaymentAmountForPayment)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			notificationService.sendNpaNotificationToCustomer(loan.getCustomer().getId(), loanId, totalDues);
		} else {
			loan.setNpaStatus(null); // Clear NPA status if rejected
		}
		loanRepository.save(loan);
	}

	@Override
	public void checkAndFlagNpaLoans() {
		List<Loan> activeLoans = loanRepository.findAllActiveLoans();
		if (activeLoans.isEmpty()) {
			return; // No exception thrown as this is a scheduled task and empty list is valid
		}
		for (Loan loan : activeLoans) {
			if (loan.getNpaStatus() != null)
				continue; // Skip if already flagged

			List<LoanPayment> payments = loanPaymentRepository.findByLoanLoanId(loan.getLoanId()).stream()
					.sorted(Comparator.comparing(LoanPayment::getDueDate)).collect(Collectors.toList());

			int consecutiveOverdue = 0;
			LocalDate today = LocalDate.now();
			for (LoanPayment payment : payments) {
				if (payment.getStatus().equals("PENDING") && payment.getDueDate().isBefore(today)) {
					consecutiveOverdue++;
					if (consecutiveOverdue >= 3) {
						NpaStatus npaPendingStatus = npaStatusRepository.findByStatusName("NPA_PENDING").orElseThrow(
								() -> new ResourceNotFoundException("NPA_PENDING status not found in database"));
						loan.setNpaStatus(npaPendingStatus);
						loanRepository.save(loan);
						notificationService.sendNpaPendingNotificationToOfficer(loan.getLoanId(),
								loan.getLoanOfficer().getId());
						break;
					}
				} else if (!payment.getStatus().equals("PENDING")) {
					consecutiveOverdue = 0; // Reset if paid
				}
			}
		}
	}

	private BigDecimal getPaymentAmountForPayment(LoanPayment payment) {
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