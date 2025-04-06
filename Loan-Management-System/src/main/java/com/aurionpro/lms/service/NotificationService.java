package com.aurionpro.lms.service;

public interface NotificationService {
	void sendLoanStatusEmail(int loanId, String status);

	void sendPaymentReminderEmail(int loanPaymentId);

	void sendInstallmentPlanEmail(int loanId);
}