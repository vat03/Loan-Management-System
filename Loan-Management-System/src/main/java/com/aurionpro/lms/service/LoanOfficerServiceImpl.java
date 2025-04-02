package com.aurionpro.lms.service;

import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.repository.LoanOfficerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanOfficerServiceImpl implements LoanOfficerService {

	@Autowired
	private LoanOfficerRepository loanOfficerRepository;

	@Override
	public LoanOfficer saveLoanOfficer(LoanOfficer loanOfficer) {
		return loanOfficerRepository.save(loanOfficer);
	}

	@Override
	public Optional<LoanOfficer> getLoanOfficerById(int id) {
		return loanOfficerRepository.findById(id);
	}

	@Override
	public List<LoanOfficer> getAllLoanOfficers() {
		return loanOfficerRepository.findAll();
	}

	@Override
	public LoanOfficer updateLoanOfficer(LoanOfficer loanOfficer) {
		return loanOfficerRepository.save(loanOfficer);
	}

	@Override
	public void deleteLoanOfficer(int id) {
		loanOfficerRepository.deleteById(id);
	}
}