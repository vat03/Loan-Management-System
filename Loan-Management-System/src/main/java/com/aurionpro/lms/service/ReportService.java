package com.aurionpro.lms.service;

import java.util.Map;

public interface ReportService {
	Map<String, Object> generateLoanOfficerReport(int loanOfficerId);
}