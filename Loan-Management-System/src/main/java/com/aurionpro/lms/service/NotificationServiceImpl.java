//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanPayment;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.repository.LoanPaymentRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import com.aurionpro.lms.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Optional;
//import java.util.Properties;
//
//@Service
//public class NotificationServiceImpl implements NotificationService {
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private LoanPaymentRepository loanPaymentRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Value("${mail.smtp.host}")
//	private String smtpHost;
//
//	@Value("${mail.smtp.port}")
//	private String smtpPort;
//
//	@Value("${mail.smtp.username}")
//	private String smtpUsername;
//
//	@Value("${mail.smtp.password}")
//	private String smtpPassword;
//
//	@Value("${mail.smtp.auth}")
//	private String smtpAuth;
//
//	@Value("${mail.smtp.starttls.enable}")
//	private String smtpStartTls;
//
//	private Session getMailSession() {
//		Properties props = new Properties();
//		props.put("mail.smtp.host", smtpHost);
//		props.put("mail.smtp.port", smtpPort);
//		props.put("mail.smtp.auth", smtpAuth);
//		props.put("mail.smtp.starttls.enable", smtpStartTls);
//
//		return Session.getInstance(props, new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(smtpUsername, smtpPassword);
//			}
//		});
//	}
//
//	@Override
//	public void sendLoanStatusEmail(int loanId, String status) {
//		Optional<Loan> loanOpt = loanRepository.findById(loanId);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + loanId);
//		}
//		Loan loan = loanOpt.get();
//		Customer customer = loan.getCustomer();
//		Optional<User> customerUserOpt = userRepository.findById(customer.getUser().getId());
//		if (customerUserOpt.isEmpty()) {
//			throw new RuntimeException("Customer user not found for loan ID: " + loanId);
//		}
//		User customerUser = customerUserOpt.get();
//
//		Session session = getMailSession();
//		try {
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(smtpUsername));
//			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerUser.getEmail()));
//			message.setSubject("Loan Application Status Update");
//			message.setText(String.format(
//					"Dear %s,\n\nYour loan application (ID: %d) has been %s.\n\nRegards,\nLoan Management Team",
//					customerUser.getUsername(), loanId, status));
//
//			Transport.send(message);
//		} catch (MessagingException e) {
//			throw new RuntimeException("Failed to send loan status email: " + e.getMessage());
//		}
//	}
//
//	@Override
//	public void sendPaymentReminderEmail(int loanPaymentId) {
//		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
//		if (paymentOpt.isEmpty()) {
//			throw new RuntimeException("Loan payment not found with ID: " + loanPaymentId);
//		}
//		LoanPayment payment = paymentOpt.get();
//		Loan loan = payment.getLoan();
//		Customer customer = loan.getCustomer();
//		Optional<User> customerUserOpt = userRepository.findById(customer.getUser().getId());
//		if (customerUserOpt.isEmpty()) {
//			throw new RuntimeException("Customer user not found for loan ID: " + loan.getLoanId());
//		}
//		User customerUser = customerUserOpt.get();
//
//		Session session = getMailSession();
//		try {
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(smtpUsername));
//			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerUser.getEmail()));
//			message.setSubject("Payment Reminder");
//			message.setText(String.format(
//					"Dear %s,\n\nThis is a reminder for your upcoming loan payment of %s due on %s for Loan ID: %d.\n\nRegards,\nLoan Management Team",
//					customerUser.getUsername(), payment.getAmount(), payment.getDueDate(), loan.getLoanId()));
//
//			Transport.send(message);
//		} catch (MessagingException e) {
//			throw new RuntimeException("Failed to send payment reminder email: " + e.getMessage());
//		}
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.LoanPayment;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.repository.LoanPaymentRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import com.aurionpro.lms.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Optional;
//import java.util.Properties;
//
//@Service
//public class NotificationServiceImpl implements NotificationService {
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private LoanPaymentRepository loanPaymentRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Value("${mail.smtp.host}")
//	private String smtpHost;
//
//	@Value("${mail.smtp.port}")
//	private String smtpPort;
//
//	@Value("${mail.smtp.username}")
//	private String smtpUsername;
//
//	@Value("${mail.smtp.password}")
//	private String smtpPassword;
//
//	@Value("${mail.smtp.auth}")
//	private String smtpAuth;
//
//	@Value("${mail.smtp.starttls.enable}")
//	private String smtpStartTls;
//
//	private Session getMailSession() {
//		Properties props = new Properties();
//		props.put("mail.smtp.host", smtpHost);
//		props.put("mail.smtp.port", smtpPort);
//		props.put("mail.smtp.auth", smtpAuth);
//		props.put("mail.smtp.starttls.enable", smtpStartTls);
//
//		return Session.getInstance(props, new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(smtpUsername, smtpPassword);
//			}
//		});
//	}
//
//	@Override
//	public void sendLoanStatusEmail(int loanId, String status) {
//		Optional<Loan> loanOpt = loanRepository.findById(loanId);
//		if (loanOpt.isEmpty()) {
//			throw new RuntimeException("Loan not found with ID: " + loanId);
//		}
//		Loan loan = loanOpt.get();
//		Customer customer = loan.getCustomer();
//		Optional<User> customerUserOpt = userRepository.findById(customer.getUser().getId());
//		if (customerUserOpt.isEmpty()) {
//			throw new RuntimeException("Customer user not found for loan ID: " + loanId);
//		}
//		User customerUser = customerUserOpt.get();
//
//		Session session = getMailSession();
//		try {
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(smtpUsername));
//			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerUser.getEmail()));
//			message.setSubject("Loan Application Status Update");
//			message.setText(String.format(
//					"Dear %s,\n\nYour loan application (ID: %d) has been %s.\n\nRegards,\nLoan Management Team",
//					customerUser.getUsername(), loanId, status));
//
//			Transport.send(message);
//		} catch (MessagingException e) {
//			throw new RuntimeException("Failed to send loan status email: " + e.getMessage());
//		}
//	}
//
//	@Override
//	public void sendPaymentReminderEmail(int loanPaymentId) {
//		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
//		if (paymentOpt.isEmpty()) {
//			throw new RuntimeException("Loan payment not found with ID: " + loanPaymentId);
//		}
//		LoanPayment payment = paymentOpt.get();
//		Loan loan = payment.getLoan();
//		Customer customer = loan.getCustomer();
//		Optional<User> customerUserOpt = userRepository.findById(customer.getUser().getId());
//		if (customerUserOpt.isEmpty()) {
//			throw new RuntimeException("Customer user not found for loan ID: " + loan.getLoanId());
//		}
//		User customerUser = customerUserOpt.get();
//
//		Session session = getMailSession();
//		try {
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(smtpUsername));
//			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerUser.getEmail()));
//			message.setSubject("Payment Reminder");
//			message.setText(String.format(
//					"Dear %s,\n\nThis is a reminder for your upcoming loan payment of %s due on %s for Loan ID: %d.\n\nRegards,\nLoan Management Team",
//					customerUser.getUsername(), payment.getAmount(), payment.getDueDate(), loan.getLoanId()));
//
//			Transport.send(message);
//		} catch (MessagingException e) {
//			throw new RuntimeException("Failed to send payment reminder email: " + e.getMessage());
//		}
//	}
//}

package com.aurionpro.lms.service;

import java.math.BigDecimal;
import java.math.RoundingMode; // Added import
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.Loan;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.entity.LoanPayment;
import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.LoanOfficerRepository;
import com.aurionpro.lms.repository.LoanPaymentRepository;
import com.aurionpro.lms.repository.LoanRepository;
import com.aurionpro.lms.repository.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private LoanPaymentRepository loanPaymentRepository;

	@Autowired
	private LoanOfficerRepository loanOfficerRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private UserRepository userRepository;

	@Value("${mail.smtp.host}")
	private String smtpHost;

	@Value("${mail.smtp.port}")
	private String smtpPort;

	@Value("${mail.smtp.username}")
	private String smtpUsername;

	@Value("${mail.smtp.password}")
	private String smtpPassword;

	@Value("${mail.smtp.auth}")
	private String smtpAuth;

	@Value("${mail.smtp.starttls.enable}")
	private String smtpStartTls;

	private Session getMailSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", smtpAuth);
		props.put("mail.smtp.starttls.enable", smtpStartTls);

		return Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpUsername, smtpPassword);
			}
		});
	}

	@Override
	public void sendLoanStatusEmail(int loanId, String status) {
		Optional<Loan> loanOpt = loanRepository.findById(loanId);
		if (loanOpt.isEmpty()) {
			throw new RuntimeException("Loan not found with ID: " + loanId);
		}
		Loan loan = loanOpt.get();
		Customer customer = loan.getCustomer();
		Optional<User> customerUserOpt = userRepository.findById(customer.getUser().getId());
		if (customerUserOpt.isEmpty()) {
			throw new RuntimeException("Customer user not found for loan ID: " + loanId);
		}
		User customerUser = customerUserOpt.get();

		Session session = getMailSession();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(smtpUsername));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerUser.getEmail()));
			message.setSubject("Loan Application Status Update");
			message.setText(String.format(
					"Dear %s,\n\nYour loan application (ID: %d) has been %s.\n\nRegards,\nLoan Management Team",
					customerUser.getUsername(), loanId, status));
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send loan status email: " + e.getMessage());
		}
	}

	@Override
	public void sendPaymentReminderEmail(int loanPaymentId) {
		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
		if (paymentOpt.isEmpty()) {
			throw new RuntimeException("Loan payment not found with ID: " + loanPaymentId);
		}
		LoanPayment payment = paymentOpt.get();
		Loan loan = payment.getLoan();
		Customer customer = loan.getCustomer();
		Optional<User> customerUserOpt = userRepository.findById(customer.getUser().getId());
		if (customerUserOpt.isEmpty()) {
			throw new RuntimeException("Customer user not found for loan ID: " + loan.getLoanId());
		}
		User customerUser = customerUserOpt.get();

		Session session = getMailSession();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(smtpUsername));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerUser.getEmail()));
			message.setSubject("Payment Reminder");
			message.setText(String.format(
					"Dear %s,\n\nThis is a reminder for your upcoming loan payment of %s due on %s for Loan ID: %d.\n\nRegards,\nLoan Management Team",
					customerUser.getUsername(), payment.getAmount(), payment.getDueDate(), loan.getLoanId()));
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send payment reminder email: " + e.getMessage());
		}
	}

	@Override
	public void sendInstallmentPlanEmail(int loanId) {
		Optional<Loan> loanOpt = loanRepository.findById(loanId);
		if (loanOpt.isEmpty()) {
			throw new RuntimeException("Loan not found with ID: " + loanId);
		}
		Loan loan = loanOpt.get();
		Customer customer = loan.getCustomer();
		Optional<User> customerUserOpt = userRepository.findById(customer.getUser().getId());
		if (customerUserOpt.isEmpty()) {
			throw new RuntimeException("Customer user not found for loan ID: " + loanId);
		}
		User customerUser = customerUserOpt.get();
		User loanOfficerUser = loan.getLoanOfficer().getUser();

		List<LoanPayment> payments = loanPaymentRepository.findByLoanLoanId(loanId);
		String installmentList = payments.stream()
				.map(p -> String.format("Installment: %s, Due Date: %s", p.getAmount(), p.getDueDate()))
				.collect(Collectors.joining("\n"));

		Session session = getMailSession();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(smtpUsername));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerUser.getEmail()));
			message.setSubject("Your Loan Installment Plan");
			message.setText(String.format("Dear %s,\n\n"
					+ "We are pleased to inform you that your loan (ID: %d) has been approved. Below is your installment plan:\n\n"
					+ "Customer Username: %s\n" + "Customer Email ID: %s\n" + "Loan Amount Approved: %s\n"
					+ "Tenure: %d months\n" + "Interest Rate: %s%%\n" + "\nInstallments:\n%s\n"
					+ "\nPenalty (if paid after due date): %s%% (%s per late installment)\n"
					+ "\nLoan Officer Email: %s (for any grievances)\n\n" + "Regards,\nLoan Management Team",
					customerUser.getUsername(), loanId, customerUser.getUsername(), customerUser.getEmail(),
					loan.getAmount(), loan.getLoanScheme().getTenureMonths(), loan.getLoanScheme().getInterestRate(),
					installmentList, payments.get(0).getPenaltyPercentage(),
					payments.get(0).getAmount().multiply(payments.get(0).getPenaltyPercentage())
							.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP),
					loanOfficerUser.getEmail()));
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send installment plan email: " + e.getMessage());
		}
	}

	@Override
	public void sendPaymentConfirmationEmail(int loanPaymentId, BigDecimal amountPaid) {
		Optional<LoanPayment> paymentOpt = loanPaymentRepository.findById(loanPaymentId);
		if (paymentOpt.isEmpty()) {
			throw new RuntimeException("Loan payment not found with ID: " + loanPaymentId);
		}
		LoanPayment payment = paymentOpt.get();
		Loan loan = payment.getLoan();
		Customer customer = loan.getCustomer();
		Optional<User> customerUserOpt = userRepository.findById(customer.getUser().getId());
		if (customerUserOpt.isEmpty()) {
			throw new RuntimeException("Customer user not found for loan ID: " + loan.getLoanId());
		}
		User customerUser = customerUserOpt.get();

		Session session = getMailSession();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(smtpUsername));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerUser.getEmail()));
			message.setSubject("Payment Confirmation");
			message.setText(String.format(
					"Dear %s,\n\n" + "We have received your payment for Loan ID: %d, due on %s.\n" + "Base Amount: %s\n"
							+ "Penalty Amount: %s\n" + "Total Amount Paid: %s\n" + "Payment Status: %s\n\n"
							+ "Thank you for your payment.\n\nRegards,\nLoan Management Team",
					customerUser.getUsername(), loan.getLoanId(), payment.getDueDate(), payment.getAmount(),
					payment.getPenaltyAmount() != null ? payment.getPenaltyAmount() : BigDecimal.ZERO, amountPaid,
					payment.getStatus()));
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send payment confirmation email: " + e.getMessage());
		}
	}

	@Override
	public void sendNpaPendingNotificationToOfficer(int loanId, int loanOfficerId) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
		LoanOfficer officer = loanOfficerRepository.findById(loanOfficerId)
				.orElseThrow(() -> new RuntimeException("Loan Officer not found"));
		Optional<User> officerUserOpt = userRepository.findById(officer.getUser().getId());
		if (officerUserOpt.isEmpty()) {
			throw new RuntimeException("Loan officer user not found for ID: " + loanOfficerId);
		}
		User officerUser = officerUserOpt.get();

		Session session = getMailSession();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(smtpUsername));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(officerUser.getEmail()));
			message.setSubject("Review Required: Potential NPA Loan");
			message.setText("Dear " + officerUser.getUsername() + ",\n\n" + "Loan ID " + loanId
					+ " has 3+ overdue payments and is marked as NPA_PENDING. "
					+ "Please review and approve/reject its NPA status.\n\n" + "Regards,\nLMS Team");
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send NPA pending notification: " + e.getMessage());
		}
	}

	@Override
	public void sendNpaNotificationToCustomer(int customerId, int loanId, BigDecimal totalDues) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		Optional<User> customerUserOpt = userRepository.findById(customer.getUser().getId());
		if (customerUserOpt.isEmpty()) {
			throw new RuntimeException("Customer user not found for ID: " + customerId);
		}
		User customerUser = customerUserOpt.get();

		Session session = getMailSession();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(smtpUsername));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerUser.getEmail()));
			message.setSubject("Urgent: Loan Classified as NPA - Immediate Repayment Required");
			message.setText("Dear " + customerUser.getUsername() + ",\n\n" + "Your loan (ID: " + loanId
					+ ") has been classified as NPA " + "due to payments overdue by over 3 installments. Total dues: ₹"
					+ totalDues + ".\n" + "You are now red-flagged, preventing future loans until cleared. "
					+ "Please repay immediately to resolve this NPA status.\n\n" + "Regards,\nLMS Team");
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send NPA notification to customer: " + e.getMessage());
		}
	}
}