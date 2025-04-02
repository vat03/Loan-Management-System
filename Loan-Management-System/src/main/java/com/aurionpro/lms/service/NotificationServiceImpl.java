package com.aurionpro.lms.service;

import com.aurionpro.lms.entity.Loan;
import com.aurionpro.lms.entity.LoanPayment;
import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.repository.LoanPaymentRepository;
import com.aurionpro.lms.repository.LoanRepository;
import com.aurionpro.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private LoanPaymentRepository loanPaymentRepository;

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

		User customerUser = userRepository.findAll().stream()
				.filter(user -> user.getUserType().getId() == loan.getCustomer().getId()) // Fixed: use == instead of
																							// equals()
				.findFirst().orElseThrow(() -> new RuntimeException("Customer not found for loan ID: " + loanId));

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

		User customerUser = userRepository.findAll().stream()
				.filter(user -> user.getUserType().getId() == loan.getCustomer().getId()) // Fixed: use == instead of
																							// equals()
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Customer not found for loan ID: " + loan.getLoanId()));

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
}