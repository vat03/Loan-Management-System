//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.repository.LoanRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class ReportServiceImpl implements ReportService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	@Override
//	public Map<String, Object> generateLoanOfficerReport(int loanOfficerId) {
//		Optional<LoanOfficer> loOpt = loanOfficerRepository.findById(loanOfficerId);
//		if (loOpt.isEmpty()) {
//			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
//		}
//
//		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//		long approvedCount = loans.stream().filter(loan -> "APPROVED".equals(loan.getStatus().getStatusName())).count();
//		long rejectedCount = loans.stream().filter(loan -> "REJECTED".equals(loan.getStatus().getStatusName())).count();
//		BigDecimal totalAmount = loans.stream().filter(loan -> "APPROVED".equals(loan.getStatus().getStatusName()))
//				.map(Loan::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//		Map<String, Object> report = new HashMap<>();
//		report.put("loanOfficerId", loanOfficerId);
//		report.put("totalLoans", loans.size());
//		report.put("approvedLoans", approvedCount);
//		report.put("rejectedLoans", rejectedCount);
//		report.put("totalAmountDisbursed", totalAmount);
//		return report;
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.repository.LoanRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class ReportServiceImpl implements ReportService {
//	
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	@Override
//	public Map<String, Object> generateLoanOfficerReport(int loanOfficerId) {
//		Optional<LoanOfficer> loOpt = loanOfficerRepository.findById(loanOfficerId);
//		if (loOpt.isEmpty()) {
//			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
//		}
//
//		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//		long approvedCount = loans.stream().filter(loan -> "APPROVED".equals(loan.getStatus().getStatusName())).count();
//		long rejectedCount = loans.stream().filter(loan -> "REJECTED".equals(loan.getStatus().getStatusName())).count();
//		BigDecimal totalAmount = loans.stream().filter(loan -> "APPROVED".equals(loan.getStatus().getStatusName()))
//				.map(Loan::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//		Map<String, Object> report = new HashMap<>();
//		report.put("loanOfficerId", loanOfficerId);
//		report.put("totalLoans", loans.size());
//		report.put("approvedLoans", approvedCount);
//		report.put("rejectedLoans", rejectedCount);
//		report.put("totalAmountDisbursed", totalAmount);
//		return report;
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class ReportServiceImpl implements ReportService{
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	public Map<String, Object> generateLoanOfficerReport(int loanOfficerId) {
//		LoanOfficer officer = loanOfficerRepository.findById(loanOfficerId)
//				.orElseThrow(() -> new RuntimeException("Loan Officer not found"));
//		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//		List<Loan> activeLoans = loans.stream().filter(l -> !l.getStatus().getStatusName().equals("PAID_OFF")
//				&& !l.getStatus().getStatusName().equals("CLOSED")).collect(Collectors.toList());
//
//		Map<String, Object> report = new HashMap<>();
//		report.put("loansOffered", loans.size());
//		report.put("rejected", loans.stream().filter(l -> l.getStatus().getStatusName().equals("REJECTED")).count());
//		report.put("inProcess", loans.stream().filter(l -> l.getStatus().getStatusName().equals("PENDING")
//				|| l.getStatus().getStatusName().equals("UNDER_REVIEW")).count());
//		report.put("amountDisbursed",
//				loans.stream()
//						.filter(l -> l.getStatus().getStatusName().equals("APPROVED")
//								|| l.getStatus().getStatusName().equals("NPA"))
//						.map(Loan::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
//		report.put("customersEntertained",
//				customerRepository.findByLoanOfficerId(loanOfficerId).stream().map(Customer::getId).distinct().count());
//		report.put("npas", activeLoans.stream().filter(l -> l.getStatus().getStatusName().equals("NPA")).count());
//		report.put("redFlaggedCustomers",
//				customerRepository.findByLoanOfficerId(loanOfficerId).stream().filter(Customer::isRedFlagged).count());
//
//		return report;
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class ReportServiceImpl implements ReportService {
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	public Map<String, Object> generateLoanOfficerReport(int loanOfficerId) {
//		LoanOfficer officer = loanOfficerRepository.findById(loanOfficerId)
//				.orElseThrow(() -> new RuntimeException("Loan Officer not found"));
//		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//		List<Loan> activeLoans = loans.stream().filter(l -> !l.getStatus().getStatusName().equals("PAID_OFF")
//				&& !l.getStatus().getStatusName().equals("CLOSED")).collect(Collectors.toList());
//
//		Map<String, Object> report = new HashMap<>();
//		report.put("loansOffered", loans.size());
//		report.put("rejected", loans.stream().filter(l -> l.getStatus().getStatusName().equals("REJECTED")).count());
//		report.put("inProcess", loans.stream().filter(l -> l.getStatus().getStatusName().equals("PENDING")
//				|| l.getStatus().getStatusName().equals("UNDER_REVIEW")).count());
//		report.put("amountDisbursed",
//				loans.stream()
//						.filter(l -> l.getStatus().getStatusName().equals("APPROVED")
//								|| (l.getNpaStatus() != null && l.getNpaStatus().getStatusName().equals("NPA")))
//						.map(Loan::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
//		report.put("customersEntertained",
//				customerRepository.findByLoanOfficerId(loanOfficerId).stream().map(Customer::getId).distinct().count());
//		report.put("npas", activeLoans.stream()
//				.filter(l -> l.getNpaStatus() != null && l.getNpaStatus().getStatusName().equals("NPA")).count());
//		report.put("redFlaggedCustomers",
//				customerRepository.findByLoanOfficerId(loanOfficerId).stream().filter(Customer::isRedFlagged).count());
//
//		return report;
//	}
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.Loan;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.LoanOfficerRepository;
import com.aurionpro.lms.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private LoanOfficerRepository loanOfficerRepository;

	@Autowired
	private CustomerRepository customerRepository;

	public Map<String, Object> generateLoanOfficerReport(int loanOfficerId) {
		LoanOfficer officer = loanOfficerRepository.findById(loanOfficerId)
				.orElseThrow(() -> new ResourceNotFoundException("Loan Officer not found with ID: " + loanOfficerId));
		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
		List<Loan> activeLoans = loans.stream().filter(l -> !l.getStatus().getStatusName().equals("PAID_OFF")
				&& !l.getStatus().getStatusName().equals("CLOSED")).collect(Collectors.toList());

		Map<String, Object> report = new HashMap<>();
		report.put("loansOffered", loans.size());
		report.put("rejected", loans.stream().filter(l -> l.getStatus().getStatusName().equals("REJECTED")).count());
		report.put("inProcess", loans.stream().filter(l -> l.getStatus().getStatusName().equals("PENDING")
				|| l.getStatus().getStatusName().equals("UNDER_REVIEW")).count());
		report.put("amountDisbursed",
				loans.stream()
						.filter(l -> l.getStatus().getStatusName().equals("APPROVED")
								|| (l.getNpaStatus() != null && l.getNpaStatus().getStatusName().equals("NPA")))
						.map(Loan::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
		report.put("customersEntertained",
				customerRepository.findByLoanOfficerId(loanOfficerId).stream().map(Customer::getId).distinct().count());
		report.put("npas", activeLoans.stream()
				.filter(l -> l.getNpaStatus() != null && l.getNpaStatus().getStatusName().equals("NPA")).count());
		report.put("redFlaggedCustomers",
				customerRepository.findByLoanOfficerId(loanOfficerId).stream().filter(Customer::isRedFlagged).count());

		return report;
	}
}