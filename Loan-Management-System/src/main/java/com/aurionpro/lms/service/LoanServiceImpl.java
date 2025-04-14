//package com.aurionpro.lms.service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.aurionpro.lms.dto.LoanRequestDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.dto.LoanUpdateDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.entity.LoanScheme;
//import com.aurionpro.lms.entity.LoanStatus;
//import com.aurionpro.lms.exception.BusinessRuleViolationException;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import com.aurionpro.lms.repository.LoanSchemeRepository;
//import com.aurionpro.lms.repository.LoanStatusRepository;
//
//@Service
//public class LoanServiceImpl implements LoanService {
//
//    @Autowired
//    private LoanRepository loanRepository;
//
//    @Autowired
//    private LoanSchemeRepository loanSchemeRepository;
//
//    @Autowired
//    private LoanStatusRepository loanStatusRepository;
//
//    @Autowired
//    private LoanPaymentService loanPaymentService;
//
//    @Autowired
//    private NotificationService notificationService;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Override
//    public LoanResponseDTO applyForLoan(LoanRequestDTO requestDTO) {
//        Customer customer = customerRepository.findByIdAndIsDeletedFalse(requestDTO.getCustomerId())
//                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + requestDTO.getCustomerId()));
//
//        if (customer.isDeleted()) {
//            throw new IllegalStateException("Cannot apply for loan: Customer account is deactivated");
//        }
//
//        if (customer.getLoanOfficer() == null) {
//            throw new BusinessRuleViolationException("Customer must be assigned a Loan Officer before applying for a loan");
//        }
//        LoanOfficer loanOfficer = customer.getLoanOfficer();
//
//        LoanScheme loanScheme = loanSchemeRepository.findByIdAndIsDeletedFalse(requestDTO.getLoanSchemeId())
//                .orElseThrow(() -> new ResourceNotFoundException("Loan scheme not found with ID: "
//                        + requestDTO.getLoanSchemeId() + " or it has been deactivated"));
//
//        LoanStatus status = loanStatusRepository.findByStatusName("PENDING")
//                .orElseThrow(() -> new ResourceNotFoundException("Loan status PENDING not found"));
//
//        Loan loan = new Loan();
//        loan.setAmount(requestDTO.getAmount());
//        loan.setCustomer(customer);
//        loan.setLoanOfficer(loanOfficer);
//        loan.setLoanScheme(loanScheme);
//        loan.setStatus(status);
//        loan.setApplicationDate(LocalDate.now());
//        loan.setDueDate(LocalDate.now().plusMonths(loanScheme.getTenureMonths()));
//
//        loan = loanRepository.save(loan);
//
//        return toResponseDTO(loan);
//    }
//
//    @Override
//    public LoanResponseDTO updateLoanStatus(int loanId, LoanUpdateDTO updateDTO) {
//        Loan loan = loanRepository.findById(loanId)
//                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + loanId));
//
//        LoanStatus status = loanStatusRepository.findByStatusName(updateDTO.getStatusName())
//                .orElseThrow(() -> new ResourceNotFoundException("Loan status not found: " + updateDTO.getStatusName()));
//        loan.setStatus(status);
//
//        loan = loanRepository.save(loan);
//
//        if ("APPROVED".equals(updateDTO.getStatusName())) {
//            loanPaymentService.createLoanPayments(loanId);
//            notificationService.sendLoanStatusEmail(loanId, "approved");
//            notificationService.sendInstallmentPlanEmail(loanId);
//        } else if ("REJECTED".equals(updateDTO.getStatusName())) {
//            notificationService.sendLoanStatusEmail(loanId, "rejected");
//        }
//
//        return toResponseDTO(loan);
//    }
//
//    @Override
//    public LoanResponseDTO getLoanById(int id) {
//        Loan loan = loanRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + id));
//        return toResponseDTO(loan);
//    }
//
//    @Override
//    public List<LoanResponseDTO> getLoansByCustomerId(int customerId) {
//        List<Loan> loans = loanRepository.findByCustomerId(customerId);
//        if (loans.isEmpty()) {
//            throw new ResourceNotFoundException("No loans found for Customer ID: " + customerId);
//        }
//        return loans.stream().map(this::toResponseDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<LoanResponseDTO> getLoansByLoanOfficerId(int loanOfficerId) {
//        List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//        if (loans.isEmpty()) {
//            throw new ResourceNotFoundException("No loans found for Loan Officer ID: " + loanOfficerId);
//        }
//        return loans.stream().map(this::toResponseDTO).collect(Collectors.toList());
//    }
//    
//    private LoanResponseDTO toResponseDTO(Loan loan) {
//        LoanResponseDTO dto = new LoanResponseDTO();
//        dto.setLoanId(loan.getLoanId());
//        dto.setAmount(loan.getAmount());
//        dto.setLoanSchemeName(loan.getLoanScheme() != null ? loan.getLoanScheme().getSchemeName() : null);
//        dto.setStatusName(loan.getStatus() != null ? loan.getStatus().getStatusName() : null);
//        dto.setApplicationDate(loan.getApplicationDate());
//        dto.setDueDate(loan.getDueDate());
//        dto.setLoanOfficerId(loan.getLoanOfficer() != null ? loan.getLoanOfficer().getId() : 0);
//        dto.setCustomerId(loan.getCustomer() != null ? loan.getCustomer().getId() : 0);
//        return dto;
//    }
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
        Customer customer = customerRepository.findByIdAndIsDeletedFalse(requestDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + requestDTO.getCustomerId()));

        if (customer.isDeleted()) {
            throw new IllegalStateException("Cannot apply for loan: Customer account is deactivated");
        }

        if (customer.getLoanOfficer() == null) {
            throw new BusinessRuleViolationException("Customer must be assigned a Loan Officer before applying for a loan");
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

        LoanStatus status = loanStatusRepository.findByStatusName(updateDTO.getStatusName())
                .orElseThrow(() -> new ResourceNotFoundException("Loan status not found: " + updateDTO.getStatusName()));
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
        return loans.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<LoanResponseDTO> getLoansByLoanOfficerId(int loanOfficerId) {
        List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
        return loans.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public LoanResponseDTO markLoanAsNpa(int loanId, boolean isNpa) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + loanId));
        if (!"APPROVED".equals(loan.getStatus().getStatusName())) {
            throw new IllegalStateException("Only APPROVED loans can be marked as NPA");
        }
        loan.setNpa(isNpa);
        loan = loanRepository.save(loan);
        return toResponseDTO(loan);
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
        dto.setNpa(loan.isNpa());
        return dto;
    }
}