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
//			notificationService.sendInstallmentPlanEmail(loanId);
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
//import com.aurionpro.lms.exception.BusinessRuleViolationException;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
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
//			throw new ResourceNotFoundException("Customer not found with ID: " + requestDTO.getCustomerId());
//		}
//		Customer customer = customerEntityOpt.get();
//
//		if (customer.getLoanOfficer() == null) {
//			throw new BusinessRuleViolationException(
//					"Customer must be assigned a Loan Officer before applying for a loan");
//		}
//		LoanOfficer loanOfficer = customer.getLoanOfficer();
//
//		Optional<LoanScheme> schemeOpt = loanSchemeRepository.findById(requestDTO.getLoanSchemeId());
//		if (schemeOpt.isEmpty()) {
//			throw new ResourceNotFoundException("Loan scheme not found with ID: " + requestDTO.getLoanSchemeId());
//		}
//		LoanScheme loanScheme = schemeOpt.get();
//
//		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName("PENDING");
//		if (statusOpt.isEmpty()) {
//			throw new ResourceNotFoundException("Loan status PENDING not found");
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
//			throw new ResourceNotFoundException("Loan not found with ID: " + loanId);
//		}
//		Loan loan = loanOpt.get();
//
//		Optional<LoanStatus> statusOpt = loanStatusRepository.findByStatusName(updateDTO.getStatusName());
//		if (statusOpt.isEmpty()) {
//			throw new ResourceNotFoundException("Loan status not found: " + updateDTO.getStatusName());
//		}
//		loan.setStatus(statusOpt.get());
//
//		loan = loanRepository.save(loan);
//
//		if ("APPROVED".equals(updateDTO.getStatusName())) {
//			loanPaymentService.createLoanPayments(loanId);
//			notificationService.sendLoanStatusEmail(loanId, "approved");
//			notificationService.sendInstallmentPlanEmail(loanId);
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
//			throw new ResourceNotFoundException("Loan not found with ID: " + id);
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
//		if (loans.isEmpty()) {
//			throw new ResourceNotFoundException("No loans found for Customer ID: " + customerId);
//		}
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
//		if (loans.isEmpty()) {
//			throw new ResourceNotFoundException("No loans found for Loan Officer ID: " + loanOfficerId);
//		}
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurionpro.lms.dto.LoanRequestDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.dto.LoanUpdateDTO;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.Loan;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.entity.LoanScheme;
import com.aurionpro.lms.entity.LoanStatus;
import com.aurionpro.lms.exception.BusinessRuleViolationException;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.LoanRepository;
import com.aurionpro.lms.repository.LoanSchemeRepository;
import com.aurionpro.lms.repository.LoanStatusRepository;

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
		Customer customer = customerRepository.findById(requestDTO.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Customer not found with ID: " + requestDTO.getCustomerId()));

		if (customer.getLoanOfficer() == null) {
			throw new BusinessRuleViolationException(
					"Customer must be assigned a Loan Officer before applying for a loan");
		}
		LoanOfficer loanOfficer = customer.getLoanOfficer();

		LoanScheme loanScheme = loanSchemeRepository.findByIdAndIsDeletedFalse(requestDTO.getLoanSchemeId())
				.orElseThrow(() -> new ResourceNotFoundException("Loan scheme not found with ID: "
						+ requestDTO.getLoanSchemeId() + " or it has been deactivated"));

		LoanStatus status = loanStatusRepository.findByStatusName("PENDING")
				.orElseThrow(() -> new ResourceNotFoundException("Loan status PENDING not found"));

		Loan loan = new Loan();
		loan.setAmount(requestDTO.getAmount());
		loan.setCustomer(customer);
		loan.setLoanOfficer(loanOfficer);
		loan.setLoanScheme(loanScheme);
		loan.setStatus(status);
		loan.setApplicationDate(LocalDate.now());
		loan.setDueDate(LocalDate.now().plusMonths(loanScheme.getTenureMonths()));

		loan = loanRepository.save(loan);

		return toResponseDTO(loan);
	}

	@Override
	public LoanResponseDTO updateLoanStatus(int loanId, LoanUpdateDTO updateDTO) {
		Loan loan = loanRepository.findById(loanId)
				.orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + loanId));

		LoanStatus status = loanStatusRepository.findByStatusName(updateDTO.getStatusName()).orElseThrow(
				() -> new ResourceNotFoundException("Loan status not found: " + updateDTO.getStatusName()));
		loan.setStatus(status);

		loan = loanRepository.save(loan);

		if ("APPROVED".equals(updateDTO.getStatusName())) {
			loanPaymentService.createLoanPayments(loanId);
			notificationService.sendLoanStatusEmail(loanId, "approved");
			notificationService.sendInstallmentPlanEmail(loanId);
		} else if ("REJECTED".equals(updateDTO.getStatusName())) {
			notificationService.sendLoanStatusEmail(loanId, "rejected");
		}

		return toResponseDTO(loan);
	}

	@Override
	public LoanResponseDTO getLoanById(int id) {
		Loan loan = loanRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + id));
		return toResponseDTO(loan);
	}

	@Override
	public List<LoanResponseDTO> getLoansByCustomerId(int customerId) {
		List<Loan> loans = loanRepository.findByCustomerId(customerId);
		if (loans.isEmpty()) {
			throw new ResourceNotFoundException("No loans found for Customer ID: " + customerId);
		}
		return loans.stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public List<LoanResponseDTO> getLoansByLoanOfficerId(int loanOfficerId) {
		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
		if (loans.isEmpty()) {
			throw new ResourceNotFoundException("No loans found for Loan Officer ID: " + loanOfficerId);
		}
		return loans.stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	private LoanResponseDTO toResponseDTO(Loan loan) {
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
}