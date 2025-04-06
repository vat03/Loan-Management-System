package com.aurionpro.lms.service;

import java.math.BigDecimal;

public interface NotificationService {
	void sendLoanStatusEmail(int loanId, String status);

	void sendPaymentReminderEmail(int loanPaymentId);

	void sendInstallmentPlanEmail(int loanId);
	
	void sendPaymentConfirmationEmail(int loanPaymentId, BigDecimal amountPaid);
}