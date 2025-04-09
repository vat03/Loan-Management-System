//package com.aurionpro.lms.service;
//
//import java.math.BigDecimal;
//
//public interface NotificationService {
//	void sendLoanStatusEmail(int loanId, String status);
//
//	void sendPaymentReminderEmail(int loanPaymentId);
//
//	void sendInstallmentPlanEmail(int loanId);
//
//	void sendPaymentConfirmationEmail(int loanPaymentId, BigDecimal amountPaid);
//
//	void sendNpaPendingNotificationToOfficer(int loanId, int loanOfficerId);
//
//	void sendNpaNotificationToCustomer(int customerId, int loanId, BigDecimal totalDues);
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.entity.Document;

import java.math.BigDecimal;

public interface NotificationService {
	void sendLoanStatusEmail(int loanId, String status);

	void sendPaymentReminderEmail(int loanPaymentId);

	void sendInstallmentPlanEmail(int loanId);

	void sendPaymentConfirmationEmail(int loanPaymentId, BigDecimal amountPaid);

	void sendNpaPendingNotificationToOfficer(int loanId, int loanOfficerId);

	void sendNpaNotificationToCustomer(int customerId, int loanId, BigDecimal totalDues);

	void sendDocumentRejectionEmail(int loanId, Document document);
}