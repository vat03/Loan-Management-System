//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanRequestDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.dto.LoanUpdateDTO;
//import com.aurionpro.lms.entity.*;
//import com.aurionpro.lms.repository.*;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanServiceImpl implements LoanService {
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private LoanSchemeRepository loanSchemeRepository;
//
//	@Autowired
//	private LoanStatusRepository loanStatusRepository;
//
//	@Autowired
//	private LoanPaymentService loanPaymentService;
//
//	@Autowired
//	private NotificationService notificationService;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	private ModelMapper mapper;
//
//	private LoanServiceImpl() {
//		this.mapper = new ModelMapper();
//	}
//
//	@Override
//	public LoanResponseDTO applyForLoan(LoanRequestDTO requestDTO) {
//		Optional<User> customerOpt = userRepository.findById(requestDTO.getCustomerId());
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + requestDTO.getCustomerId());
//		}
//		Optional<Customer> customerEntityOpt = customerRepository.findByUserId(requestDTO.getCustomerId());
//		if (customerEntityOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
//		}
//		Customer customer = customerEntityOpt.get();
//		LoanOfficer loanOfficer = customer.getLoanOfficer();
//
//		Optional<LoanScheme> schemeOpt = loanSchemeRepository.findById(requestDTO.getLoanSchemeId());
//		if (schemeOpt.isEmpty()) {
//			throw new RuntimeException("Loan scheme not found with ID: " + requestDTO.getLoanSchemeId());
//		}
//		LoanScheme loanScheme = schemeOpt.get();
//
//		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName("PENDING");
//		if (statusOpt.isEmpty()) {
//			throw new RuntimeException("Loan status PENDING not found");
//		}
//		LoanStatus status = statusOpt.get();
//
//		Loan loan = mapper.map(requestDTO, Loan.class);
//		loan.setCustomer(customer);
//		loan.setLoanOfficer(loanOfficer);
//		loan.setLoanScheme(loanScheme);
//		loan.setStatus(status);
//		loan.setApplicationDate(LocalDate.now());
//		loan.setDueDate(LocalDate.now().plusMonths(loanScheme.getTenureMonths()));
//
//		loan = loanRepository.save(loan);
//
//		mapper.typeMap(Loan.class, LoanResponseDTO.class)
//				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
//				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
//				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
//		return mapper.map(loan, LoanResponseDTO.class);
//	}
//
//	@Override
//	public LoanResponseDTO updateLoanStatus(int loanId, LoanUpdateDTO updateDTO) {
//		Optional<Loan> loanOpt = loanRepository.findById(loanId);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + loanId);
//		}
//		Loan loan = loanOpt.get();
//
//		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName(updateDTO.getStatusName());
//		if (statusOpt.isEmpty()) {
//			throw new RuntimeException("Loan status not found: " + updateDTO.getStatusName());
//		}
//		loan.setStatus(statusOpt.get());
//
//		loan = loanRepository.save(loan);
//
//		if ("APPROVED".equals(updateDTO.getStatusName())) {
//			loanPaymentService.createLoanPayments(loanId);
//			notificationService.sendLoanStatusEmail(loanId, "approved");
//		} else if ("REJECTED".equals(updateDTO.getStatusName())) {
//			notificationService.sendLoanStatusEmail(loanId, "rejected");
//		}
//
//		mapper.typeMap(Loan.class, LoanResponseDTO.class)
//				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
//				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
//				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
//		return mapper.map(loan, LoanResponseDTO.class);
//	}
//
//	@Override
//	public LoanResponseDTO getLoanById(int id) {
//		Optional<Loan> loanOpt = loanRepository.findById(id);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + id);
//		}
//		Loan loan = loanOpt.get();
//
//		mapper.typeMap(Loan.class, LoanResponseDTO.class)
//				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
//				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
//				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
//		return mapper.map(loan, LoanResponseDTO.class);
//	}
//
//	@Override
//	public List<LoanResponseDTO> getLoansByCustomerId(int customerId) {
//		List<Loan> loans = loanRepository.findByCustomerId(customerId);
//		mapper.typeMap(Loan.class, LoanResponseDTO.class)
//				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
//				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
//				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
//		return loans.stream().map(loan -> mapper.map(loan, LoanResponseDTO.class)).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<LoanResponseDTO> getLoansByLoanOfficerId(int loanOfficerId) {
//		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//		mapper.typeMap(Loan.class, LoanResponseDTO.class)
//				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
//				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
//				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
//		return loans.stream().map(loan -> mapper.map(loan, LoanResponseDTO.class)).collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanRequestDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.dto.LoanUpdateDTO;
//import com.aurionpro.lms.entity.*;
//import com.aurionpro.lms.repository.*;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanServiceImpl implements LoanService {
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private LoanSchemeRepository loanSchemeRepository;
//
//	@Autowired
//	private LoanStatusRepository loanStatusRepository;
//
//	@Autowired
//	private LoanPaymentService loanPaymentService;
//
//	@Autowired
//	private NotificationService notificationService;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	private ModelMapper mapper;
//
//	private LoanServiceImpl() {
//		this.mapper = new ModelMapper();
//	}
//
//	@Override
//	public LoanResponseDTO applyForLoan(LoanRequestDTO requestDTO) {
//		Optional<User> customerOpt = userRepository.findById(requestDTO.getCustomerId());
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + requestDTO.getCustomerId());
//		}
//		Optional<Customer> customerEntityOpt = customerRepository.findByUserId(requestDTO.getCustomerId());
//		if (customerEntityOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
//		}
//		Customer customer = customerEntityOpt.get();
//
//		// Enforce loan officer assignment
//		if (customer.getLoanOfficer() == null) {
//			throw new RuntimeException("Customer must be assigned a Loan Officer before applying for a loan");
//		}
//		LoanOfficer loanOfficer = customer.getLoanOfficer();
//
//		Optional<LoanScheme> schemeOpt = loanSchemeRepository.findById(requestDTO.getLoanSchemeId());
//		if (schemeOpt.isEmpty()) {
//			throw new RuntimeException("Loan scheme not found with ID: " + requestDTO.getLoanSchemeId());
//		}
//		LoanScheme loanScheme = schemeOpt.get();
//
//		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName("PENDING");
//		if (statusOpt.isEmpty()) {
//			throw new RuntimeException("Loan status PENDING not found");
//		}
//		LoanStatus status = statusOpt.get();
//
//		Loan loan = mapper.map(requestDTO, Loan.class);
//		loan.setCustomer(customer);
//		loan.setLoanOfficer(loanOfficer);
//		loan.setLoanScheme(loanScheme);
//		loan.setStatus(status);
//		loan.setApplicationDate(LocalDate.now());
//		loan.setDueDate(LocalDate.now().plusMonths(loanScheme.getTenureMonths()));
//
//		loan = loanRepository.save(loan);
//
//		mapper.typeMap(Loan.class, LoanResponseDTO.class)
//				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
//				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
//				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
//		return mapper.map(loan, LoanResponseDTO.class);
//	}
//
//	// Other methods remain unchanged
//	@Override
//	public LoanResponseDTO updateLoanStatus(int loanId, LoanUpdateDTO updateDTO) {
//		Optional<Loan> loanOpt = loanRepository.findById(loanId);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + loanId);
//		}
//		Loan loan = loanOpt.get();
//
//		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName(updateDTO.getStatusName());
//		if (statusOpt.isEmpty()) {
//			throw new RuntimeException("Loan status not found: " + updateDTO.getStatusName());
//		}
//		loan.setStatus(statusOpt.get());
//
//		loan = loanRepository.save(loan);
//
//		if ("APPROVED".equals(updateDTO.getStatusName())) {
//			loanPaymentService.createLoanPayments(loanId);
//			notificationService.sendLoanStatusEmail(loanId, "approved");
//		} else if ("REJECTED".equals(updateDTO.getStatusName())) {
//			notificationService.sendLoanStatusEmail(loanId, "rejected");
//		}
//
//		mapper.typeMap(Loan.class, LoanResponseDTO.class)
//				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
//				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
//				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
//		return mapper.map(loan, LoanResponseDTO.class);
//	}
//
//	@Override
//	public LoanResponseDTO getLoanById(int id) {
//		Optional<Loan> loanOpt = loanRepository.findById(id);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + id);
//		}
//		Loan loan = loanOpt.get();
//
//		mapper.typeMap(Loan.class, LoanResponseDTO.class)
//				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
//				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
//				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
//		return mapper.map(loan, LoanResponseDTO.class);
//	}
//
//	@Override
//	public List<LoanResponseDTO> getLoansByCustomerId(int customerId) {
//		List<Loan> loans = loanRepository.findByCustomerId(customerId);
//		mapper.typeMap(Loan.class, LoanResponseDTO.class)
//				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
//				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
//				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
//		return loans.stream().map(loan -> mapper.map(loan, LoanResponseDTO.class)).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<LoanResponseDTO> getLoansByLoanOfficerId(int loanOfficerId) {
//		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//		mapper.typeMap(Loan.class, LoanResponseDTO.class)
//				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
//				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
//				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
//		return loans.stream().map(loan -> mapper.map(loan, LoanResponseDTO.class)).collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanRequestDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.dto.LoanUpdateDTO;
//import com.aurionpro.lms.entity.*;
//import com.aurionpro.lms.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanServiceImpl implements LoanService {
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private LoanSchemeRepository loanSchemeRepository;
//
//	@Autowired
//	private LoanStatusRepository loanStatusRepository;
//
//	@Autowired
//	private LoanPaymentService loanPaymentService;
//
//	@Autowired
//	private NotificationService notificationService;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Override
//	public LoanResponseDTO applyForLoan(LoanRequestDTO requestDTO) {
//		Optional<User> customerOpt = userRepository.findById(requestDTO.getCustomerId());
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + requestDTO.getCustomerId());
//		}
//		Optional<Customer> customerEntityOpt = customerRepository.findByUserId(requestDTO.getCustomerId());
//		if (customerEntityOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
//		}
//		Customer customer = customerEntityOpt.get();
//
//		if (customer.getLoanOfficer() == null) {
//			throw new RuntimeException("Customer must be assigned a Loan Officer before applying for a loan");
//		}
//		LoanOfficer loanOfficer = customer.getLoanOfficer();
//
//		Optional<LoanScheme> schemeOpt = loanSchemeRepository.findById(requestDTO.getLoanSchemeId());
//		if (schemeOpt.isEmpty()) {
//			throw new RuntimeException("Loan scheme not found with ID: " + requestDTO.getLoanSchemeId());
//		}
//		LoanScheme loanScheme = schemeOpt.get();
//
//		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName("PENDING");
//		if (statusOpt.isEmpty()) {
//			throw new RuntimeException("Loan status PENDING not found");
//		}
//		LoanStatus status = statusOpt.get();
//
//		Loan loan = new Loan();
//		loan.setAmount(requestDTO.getAmount());
//		loan.setCustomer(customer);
//		loan.setLoanOfficer(loanOfficer);
//		loan.setLoanScheme(loanScheme);
//		loan.setStatus(status);
//		loan.setApplicationDate(LocalDate.now());
//		loan.setDueDate(LocalDate.now().plusMonths(loanScheme.getTenureMonths()));
//
//		loan = loanRepository.save(loan);
//
//		LoanResponseDTO dto = new LoanResponseDTO();
//		dto.setLoanId(loan.getLoanId());
//		dto.setAmount(loan.getAmount());
//		dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//		dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//		dto.setApplicationDate(loan.getApplicationDate());
//		dto.setDueDate(loan.getDueDate());
//		dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//		dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//		return dto;
//	}
//
//	@Override
//	public LoanResponseDTO updateLoanStatus(int loanId, LoanUpdateDTO updateDTO) {
//		Optional<Loan> loanOpt = loanRepository.findById(loanId);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + loanId);
//		}
//		Loan loan = loanOpt.get();
//
//		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName(updateDTO.getStatusName());
//		if (statusOpt.isEmpty()) {
//			throw new RuntimeException("Loan status not found: " + updateDTO.getStatusName());
//		}
//		loan.setStatus(statusOpt.get());
//
//		loan = loanRepository.save(loan);
//
//		if ("APPROVED".equals(updateDTO.getStatusName())) {
//			loanPaymentService.createLoanPayments(loanId);
//			notificationService.sendLoanStatusEmail(loanId, "approved");
//		} else if ("REJECTED".equals(updateDTO.getStatusName())) {
//			notificationService.sendLoanStatusEmail(loanId, "rejected");
//		}
//
//		LoanResponseDTO dto = new LoanResponseDTO();
//		dto.setLoanId(loan.getLoanId());
//		dto.setAmount(loan.getAmount());
//		dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//		dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//		dto.setApplicationDate(loan.getApplicationDate());
//		dto.setDueDate(loan.getDueDate());
//		dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//		dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//		return dto;
//	}
//
//	@Override
//	public LoanResponseDTO getLoanById(int id) {
//		Optional<Loan> loanOpt = loanRepository.findById(id);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + id);
//		}
//		Loan loan = loanOpt.get();
//
//		LoanResponseDTO dto = new LoanResponseDTO();
//		dto.setLoanId(loan.getLoanId());
//		dto.setAmount(loan.getAmount());
//		dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//		dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//		dto.setApplicationDate(loan.getApplicationDate());
//		dto.setDueDate(loan.getDueDate());
//		dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//		dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//		return dto;
//	}
//
//	@Override
//	public List<LoanResponseDTO> getLoansByCustomerId(int customerId) {
//		List<Loan> loans = loanRepository.findByCustomerId(customerId);
//		return loans.stream().map(loan -> {
//			LoanResponseDTO dto = new LoanResponseDTO();
//			dto.setLoanId(loan.getLoanId());
//			dto.setAmount(loan.getAmount());
//			dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//			dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//			dto.setApplicationDate(loan.getApplicationDate());
//			dto.setDueDate(loan.getDueDate());
//			dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//			dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<LoanResponseDTO> getLoansByLoanOfficerId(int loanOfficerId) {
//		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//		return loans.stream().map(loan -> {
//			LoanResponseDTO dto = new LoanResponseDTO();
//			dto.setLoanId(loan.getLoanId());
//			dto.setAmount(loan.getAmount());
//			dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//			dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//			dto.setApplicationDate(loan.getApplicationDate());
//			dto.setDueDate(loan.getDueDate());
//			dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//			dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanRequestDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.dto.LoanUpdateDTO;
//import com.aurionpro.lms.entity.*;
//import com.aurionpro.lms.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanServiceImpl implements LoanService {
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private LoanSchemeRepository loanSchemeRepository;
//
//	@Autowired
//	private LoanStatusRepository loanStatusRepository;
//
//	@Autowired
//	private LoanPaymentService loanPaymentService;
//
//	@Autowired
//	private NotificationService notificationService;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Override
//	public LoanResponseDTO applyForLoan(LoanRequestDTO requestDTO) {
//		Optional<Customer> customerEntityOpt = customerRepository.findById(requestDTO.getCustomerId());
//		if (customerEntityOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
//		}
//		Customer customer = customerEntityOpt.get();
//
//		if (customer.getLoanOfficer() == null) {
//			throw new RuntimeException("Customer must be assigned a Loan Officer before applying for a loan");
//		}
//		LoanOfficer loanOfficer = customer.getLoanOfficer();
//
//		Optional<LoanScheme> schemeOpt = loanSchemeRepository.findById(requestDTO.getLoanSchemeId());
//		if (schemeOpt.isEmpty()) {
//			throw new RuntimeException("Loan scheme not found with ID: " + requestDTO.getLoanSchemeId());
//		}
//		LoanScheme loanScheme = schemeOpt.get();
//
//		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName("PENDING");
//		if (statusOpt.isEmpty()) {
//			throw new RuntimeException("Loan status PENDING not found");
//		}
//		LoanStatus status = statusOpt.get();
//
//		Loan loan = new Loan();
//		loan.setAmount(requestDTO.getAmount());
//		loan.setCustomer(customer);
//		loan.setLoanOfficer(loanOfficer);
//		loan.setLoanScheme(loanScheme);
//		loan.setStatus(status);
//		loan.setApplicationDate(LocalDate.now());
//		loan.setDueDate(LocalDate.now().plusMonths(loanScheme.getTenureMonths()));
//
//		loan = loanRepository.save(loan);
//
//		LoanResponseDTO dto = new LoanResponseDTO();
//		dto.setLoanId(loan.getLoanId());
//		dto.setAmount(loan.getAmount());
//		dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//		dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//		dto.setApplicationDate(loan.getApplicationDate());
//		dto.setDueDate(loan.getDueDate());
//		dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//		dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//		return dto;
//	}
//
//	@Override
//	public LoanResponseDTO updateLoanStatus(int loanId, LoanUpdateDTO updateDTO) {
//		Optional<Loan> loanOpt = loanRepository.findById(loanId);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + loanId);
//		}
//		Loan loan = loanOpt.get();
//
//		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName(updateDTO.getStatusName());
//		if (statusOpt.isEmpty()) {
//			throw new RuntimeException("Loan status not found: " + updateDTO.getStatusName());
//		}
//		loan.setStatus(statusOpt.get());
//
//		loan = loanRepository.save(loan);
//
//		if ("APPROVED".equals(updateDTO.getStatusName())) {
//			loanPaymentService.createLoanPayments(loanId);
//			notificationService.sendLoanStatusEmail(loanId, "approved");
//		} else if ("REJECTED".equals(updateDTO.getStatusName())) {
//			notificationService.sendLoanStatusEmail(loanId, "rejected");
//		}
//
//		LoanResponseDTO dto = new LoanResponseDTO();
//		dto.setLoanId(loan.getLoanId());
//		dto.setAmount(loan.getAmount());
//		dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//		dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//		dto.setApplicationDate(loan.getApplicationDate());
//		dto.setDueDate(loan.getDueDate());
//		dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//		dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//		return dto;
//	}
//
//	@Override
//	public LoanResponseDTO getLoanById(int id) {
//		Optional<Loan> loanOpt = loanRepository.findById(id);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + id);
//		}
//		Loan loan = loanOpt.get();
//
//		LoanResponseDTO dto = new LoanResponseDTO();
//		dto.setLoanId(loan.getLoanId());
//		dto.setAmount(loan.getAmount());
//		dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//		dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//		dto.setApplicationDate(loan.getApplicationDate());
//		dto.setDueDate(loan.getDueDate());
//		dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//		dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//		return dto;
//	}
//
//	@Override
//	public List<LoanResponseDTO> getLoansByCustomerId(int customerId) {
//		List<Loan> loans = loanRepository.findByCustomerId(customerId);
//		return loans.stream().map(loan -> {
//			LoanResponseDTO dto = new LoanResponseDTO();
//			dto.setLoanId(loan.getLoanId());
//			dto.setAmount(loan.getAmount());
//			dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//			dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//			dto.setApplicationDate(loan.getApplicationDate());
//			dto.setDueDate(loan.getDueDate());
//			dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//			dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<LoanResponseDTO> getLoansByLoanOfficerId(int loanOfficerId) {
//		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//		return loans.stream().map(loan -> {
//			LoanResponseDTO dto = new LoanResponseDTO();
//			dto.setLoanId(loan.getLoanId());
//			dto.setAmount(loan.getAmount());
//			dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//			dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//			dto.setApplicationDate(loan.getApplicationDate());
//			dto.setDueDate(loan.getDueDate());
//			dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//			dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoanRequestDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.dto.LoanUpdateDTO;
import com.aurionpro.lms.entity.*;
import com.aurionpro.lms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private LoanSchemeRepository loanSchemeRepository;

	@Autowired
	private LoanStatusRepository loanStatusRepository;

	@Autowired
	private LoanPaymentService loanPaymentService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public LoanResponseDTO applyForLoan(LoanRequestDTO requestDTO) {
		Optional<Customer> customerEntityOpt = customerRepository.findById(requestDTO.getCustomerId());
		if (customerEntityOpt.isEmpty()) {
			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
		}
		Customer customer = customerEntityOpt.get();

		if (customer.getLoanOfficer() == null) {
			throw new RuntimeException("Customer must be assigned a Loan Officer before applying for a loan");
		}
		LoanOfficer loanOfficer = customer.getLoanOfficer();

		Optional<LoanScheme> schemeOpt = loanSchemeRepository.findById(requestDTO.getLoanSchemeId());
		if (schemeOpt.isEmpty()) {
			throw new RuntimeException("Loan scheme not found with ID: " + requestDTO.getLoanSchemeId());
		}
		LoanScheme loanScheme = schemeOpt.get();

		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName("PENDING");
		if (statusOpt.isEmpty()) {
			throw new RuntimeException("Loan status PENDING not found");
		}
		LoanStatus status = statusOpt.get();

		Loan loan = new Loan();
		loan.setAmount(requestDTO.getAmount());
		loan.setCustomer(customer);
		loan.setLoanOfficer(loanOfficer);
		loan.setLoanScheme(loanScheme);
		loan.setStatus(status);
		loan.setApplicationDate(LocalDate.now());
		loan.setDueDate(LocalDate.now().plusMonths(loanScheme.getTenureMonths()));

		loan = loanRepository.save(loan);

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
	}

	@Override
	public LoanResponseDTO updateLoanStatus(int loanId, LoanUpdateDTO updateDTO) {
		Optional<Loan> loanOpt = loanRepository.findById(loanId);
		if (loanOpt.isEmpty()) {
			throw new RuntimeException("Loan not found with ID: " + loanId);
		}
		Loan loan = loanOpt.get();

		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName(updateDTO.getStatusName());
		if (statusOpt.isEmpty()) {
			throw new RuntimeException("Loan status not found: " + updateDTO.getStatusName());
		}
		loan.setStatus(statusOpt.get());

		loan = loanRepository.save(loan);

		if ("APPROVED".equals(updateDTO.getStatusName())) {
			loanPaymentService.createLoanPayments(loanId);
			notificationService.sendLoanStatusEmail(loanId, "approved");
			notificationService.sendInstallmentPlanEmail(loanId); // New email trigger
		} else if ("REJECTED".equals(updateDTO.getStatusName())) {
			notificationService.sendLoanStatusEmail(loanId, "rejected");
		}

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
	}

	@Override
	public LoanResponseDTO getLoanById(int id) {
		Optional<Loan> loanOpt = loanRepository.findById(id);
		if (loanOpt.isEmpty()) {
			throw new RuntimeException("Loan not found with ID: " + id);
		}
		Loan loan = loanOpt.get();

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
	}

	@Override
	public List<LoanResponseDTO> getLoansByCustomerId(int customerId) {
		List<Loan> loans = loanRepository.findByCustomerId(customerId);
		return loans.stream().map(loan -> {
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
	public List<LoanResponseDTO> getLoansByLoanOfficerId(int loanOfficerId) {
		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
		return loans.stream().map(loan -> {
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
}