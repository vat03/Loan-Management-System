package com.aurionpro.lms.service;

import com.aurionpro.lms.entity.LoanOfficer;
import java.util.List;
import java.util.Optional;

public interface LoanOfficerService {
	LoanOfficer saveLoanOfficer(LoanOfficer loanOfficer);

	Optional<LoanOfficer> getLoanOfficerById(int id);

	List<LoanOfficer> getAllLoanOfficers();

	LoanOfficer updateLoanOfficer(LoanOfficer loanOfficer);

	void deleteLoanOfficer(int id);
}