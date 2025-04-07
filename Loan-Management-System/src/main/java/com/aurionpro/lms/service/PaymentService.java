//package com.aurionpro.lms.service;
//
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
//import com.razorpay.Utils;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//
//@Service
//public class PaymentService {
//
//	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
//
//	@Value("${razorpay.key.id}")
//	private String keyId;
//
//	@Value("${razorpay.key.secret}")
//	private String keySecret;
//
//	@Autowired
//	private Environment env;
//
//	private RazorpayClient razorpayClient;
//
//	public PaymentService() {
//		logger.info("PaymentService initialized. RazorpayClient will be created on first use.");
//	}
//
//	private void initializeRazorpayClient() throws RazorpayException {
//		if (razorpayClient == null) {
//			// Debug: Check @Value injection
//			logger.info("From @Value - keyId: {}, keySecret: [hidden]", keyId);
//			// Fallback: Try Environment
//			String envKeyId = env.getProperty("razorpay.key.id");
//			String envKeySecret = env.getProperty("razorpay.key.secret");
//			logger.info("From Environment - keyId: {}, keySecret: [hidden]", envKeyId);
//
//			// Use Environment values if @Value fails
//			keyId = (keyId != null && !keyId.isEmpty()) ? keyId : envKeyId;
//			keySecret = (keySecret != null && !keySecret.isEmpty()) ? keySecret : envKeySecret;
//
//			if (keyId == null || keySecret == null || keyId.isEmpty() || keySecret.isEmpty()) {
//				logger.error("Razorpay keys are not properly configured: keyId={}, keySecret=[hidden]", keyId);
//				throw new IllegalStateException("Razorpay keyId or keySecret is null or empty");
//			}
//			logger.info("Initializing RazorpayClient with keyId: {}, keySecret: [hidden]", keyId);
//			this.razorpayClient = new RazorpayClient(keyId, keySecret);
//			logger.info("RazorpayClient initialized successfully");
//		}
//	}
//
//	public String createPaymentOrder(int loanPaymentId, BigDecimal amount) throws RazorpayException {
//		initializeRazorpayClient();
//
//		JSONObject orderRequest = new JSONObject();
//		orderRequest.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue()); // Amount in paise
//		orderRequest.put("currency", "INR");
//		orderRequest.put("receipt", "loan_payment_" + loanPaymentId);
//		orderRequest.put("payment_capture", 1); // Auto-capture payment
//
//		logger.info("Creating Razorpay order: {}", orderRequest.toString());
//		try {
//			Order order = razorpayClient.orders.create(orderRequest);
//			String orderId = order.get("id");
//			logger.info("Order created successfully: {}", orderId);
//			return orderId;
//		} catch (RazorpayException e) {
//			logger.error("Failed to create Razorpay order for loanPaymentId: {}. Error: {}", loanPaymentId,
//					e.getMessage());
//			throw e;
//		}
//	}
//
//	public boolean verifyPayment(String orderId, String paymentId, String signature) throws RazorpayException {
//		initializeRazorpayClient();
//		JSONObject attributes = new JSONObject();
//		attributes.put("razorpay_order_id", orderId);
//		attributes.put("razorpay_payment_id", paymentId);
//		attributes.put("razorpay_signature", signature);
//
//		logger.info("Verifying payment: orderId={}, paymentId={}", orderId, paymentId);
//		//return Utils.verifyPaymentSignature(attributes, keySecret); //commenting this to avoid error during postman testing
//		return true;
//	}
//}

//upar wala with logger

//package com.aurionpro.lms.service;
//
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
//import com.razorpay.Utils;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//
//@Service
//public class PaymentService {
//
//	@Value("${razorpay.key.id}")
//	private String keyId;
//
//	@Value("${razorpay.key.secret}")
//	private String keySecret;
//
//	@Autowired
//	private Environment env;
//
//	private RazorpayClient razorpayClient;
//
//	public PaymentService() {
//	}
//
//	private void initializeRazorpayClient() throws RazorpayException {
//		if (razorpayClient == null) {
//			String envKeyId = env.getProperty("razorpay.key.id");
//			String envKeySecret = env.getProperty("razorpay.key.secret");
//
//			keyId = (keyId != null && !keyId.isEmpty()) ? keyId : envKeyId;
//			keySecret = (keySecret != null && !keySecret.isEmpty()) ? keySecret : envKeySecret;
//
//			if (keyId == null || keySecret == null || keyId.isEmpty() || keySecret.isEmpty()) {
//				throw new IllegalStateException("Razorpay keyId or keySecret is null or empty");
//			}
//			this.razorpayClient = new RazorpayClient(keyId, keySecret);
//		}
//	}
//
//	public String createPaymentOrder(int loanPaymentId, BigDecimal amount) throws RazorpayException {
//		initializeRazorpayClient();
//
//		JSONObject orderRequest = new JSONObject();
//		orderRequest.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue()); // Amount in paise
//		orderRequest.put("currency", "INR");
//		orderRequest.put("receipt", "loan_payment_" + loanPaymentId);
//		orderRequest.put("payment_capture", 1); // Auto-capture payment
//
//		Order order = razorpayClient.orders.create(orderRequest);
//		String orderId = order.get("id");
//		return orderId;
//	}
//
//	public boolean verifyPayment(String orderId, String paymentId, String signature) throws RazorpayException {
//		initializeRazorpayClient();
//		JSONObject attributes = new JSONObject();
//		attributes.put("razorpay_order_id", orderId);
//		attributes.put("razorpay_payment_id", paymentId);
//		attributes.put("razorpay_signature", signature);
//
//		// return Utils.verifyPaymentSignature(attributes, keySecret); // Still
//		// commented for Postman testing
//		return true;
//	}
//}

// upar wala for testing

package com.aurionpro.lms.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")
	private String keySecret;

	@Autowired
	private Environment env;

	private RazorpayClient razorpayClient;

	public PaymentService() {
	}

	private void initializeRazorpayClient() throws RazorpayException {
		if (razorpayClient == null) {
			String envKeyId = env.getProperty("razorpay.key.id");
			String envKeySecret = env.getProperty("razorpay.key.secret");

			keyId = (keyId != null && !keyId.isEmpty()) ? keyId : envKeyId;
			keySecret = (keySecret != null && !keySecret.isEmpty()) ? keySecret : envKeySecret;

			if (keyId == null || keySecret == null || keyId.isEmpty() || keySecret.isEmpty()) {
				throw new IllegalStateException("Razorpay keyId or keySecret is null or empty");
			}
			this.razorpayClient = new RazorpayClient(keyId, keySecret);
		}
	}

	public String createPaymentOrder(int loanPaymentId, BigDecimal amount) throws RazorpayException {
		initializeRazorpayClient();

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue()); 
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "loan_payment_" + loanPaymentId);
		orderRequest.put("payment_capture", 1);

		Order order = razorpayClient.orders.create(orderRequest);
		String orderId = order.get("id");
		return orderId;
	}

	public boolean verifyPayment(String orderId, String paymentId, String signature) throws RazorpayException {
		initializeRazorpayClient();
		JSONObject attributes = new JSONObject();
		attributes.put("razorpay_order_id", orderId);
		attributes.put("razorpay_payment_id", paymentId);
		attributes.put("razorpay_signature", signature);

		return Utils.verifyPaymentSignature(attributes, keySecret);
	}
}