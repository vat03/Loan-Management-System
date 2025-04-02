package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoanRequestDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.dto.LoanUpdateDTO;
import com.aurionpro.lms.entity.*;
import com.aurionpro.lms.repository.*;
import org.modelmapper.ModelMapper;
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
	private UserRepository userRepository;

	@Autowired
	private LoanSchemeRepository loanSchemeRepository;

	@Autowired
	private LoanStatusRepository loanStatusRepository;

	@Autowired
	private LoanPaymentService loanPaymentService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public LoanResponseDTO applyForLoan(LoanRequestDTO requestDTO) {
		Optional<User> customerOpt = userRepository.findById(requestDTO.getCustomerId());
		if (customerOpt.isEmpty() || !(customerOpt.get().getUserType() instanceof Customer)) {
			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
		}
		Customer customer = (Customer) customerOpt.get().getUserType();
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

		Loan loan = modelMapper.map(requestDTO, Loan.class);
		loan.setCustomer(customer);
		loan.setLoanOfficer(loanOfficer);
		loan.setLoanScheme(loanScheme);
		loan.setStatus(status);
		loan.setApplicationDate(LocalDate.now());
		loan.setDueDate(LocalDate.now().plusMonths(loanScheme.getTenureMonths()));

		loan = loanRepository.save(loan);

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(Loan.class, LoanResponseDTO.class)
				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
		return mapper.map(loan, LoanResponseDTO.class);
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
		} else if ("REJECTED".equals(updateDTO.getStatusName())) {
			notificationService.sendLoanStatusEmail(loanId, "rejected");
		}

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(Loan.class, LoanResponseDTO.class)
				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
		return mapper.map(loan, LoanResponseDTO.class);
	}

	@Override
	public LoanResponseDTO getLoanById(int id) {
		Optional<Loan> loanOpt = loanRepository.findById(id);
		if (loanOpt.isEmpty()) {
			throw new RuntimeException("Loan not found with ID: " + id);
		}
		Loan loan = loanOpt.get();

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(Loan.class, LoanResponseDTO.class)
				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
		return mapper.map(loan, LoanResponseDTO.class);
	}

	@Override
	public List<LoanResponseDTO> getLoansByCustomerId(int customerId) {
		List<Loan> loans = loanRepository.findByCustomerId(customerId);
		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(Loan.class, LoanResponseDTO.class)
				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
		return loans.stream().map(loan -> mapper.map(loan, LoanResponseDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<LoanResponseDTO> getLoansByLoanOfficerId(int loanOfficerId) {
		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(Loan.class, LoanResponseDTO.class)
				.addMapping(src -> src.getLoanScheme().getSchemeName(), LoanResponseDTO::setLoanSchemeName)
				.addMapping(src -> src.getStatus().getStatusName(), LoanResponseDTO::setStatusName)
				.addMapping(src -> src.getLoanOfficer().getId(), LoanResponseDTO::setLoanOfficerId);
		return loans.stream().map(loan -> mapper.map(loan, LoanResponseDTO.class)).collect(Collectors.toList());
	}
}