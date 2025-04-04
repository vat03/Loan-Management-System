package com.aurionpro.lms.service;

import com.aurionpro.lms.entity.Loan;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.repository.LoanRepository;
import com.aurionpro.lms.repository.UserRepository;
import com.aurionpro.lms.repository.LoanOfficerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private LoanOfficerRepository loanOfficerRepository;

	@Override
	public Map<String, Object> generateLoanOfficerReport(int loanOfficerId) {
		Optional<LoanOfficer> loOpt = loanOfficerRepository.findById(loanOfficerId);
		if (loOpt.isEmpty()) {
			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
		}

		List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
		long approvedCount = loans.stream().filter(loan -> "APPROVED".equals(loan.getStatus().getStatusName())).count();
		long rejectedCount = loans.stream().filter(loan -> "REJECTED".equals(loan.getStatus().getStatusName())).count();
		BigDecimal totalAmount = loans.stream().filter(loan -> "APPROVED".equals(loan.getStatus().getStatusName()))
				.map(Loan::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		Map<String, Object> report = new HashMap<>();
		report.put("loanOfficerId", loanOfficerId);
		report.put("totalLoans", loans.size());
		report.put("approvedLoans", approvedCount);
		report.put("rejectedLoans", rejectedCount);
		report.put("totalAmountDisbursed", totalAmount);
		return report;
	}
}