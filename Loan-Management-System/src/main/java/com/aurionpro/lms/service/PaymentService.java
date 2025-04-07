package com.aurionpro.lms.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")
	private String keySecret;

	private RazorpayClient razorpayClient;

	public PaymentService() throws RazorpayException {
		this.razorpayClient = new RazorpayClient(keyId, keySecret);
	}

	public String createPaymentOrder(int loanPaymentId, BigDecimal amount) throws RazorpayException {
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue()); // Amount in paise
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "loan_payment_" + loanPaymentId);
		orderRequest.put("payment_capture", 1); // Auto-capture payment

		Order order = razorpayClient.orders.create(orderRequest);
		return order.get("id");
	}

	public boolean verifyPayment(String orderId, String paymentId, String signature) throws RazorpayException {
		JSONObject attributes = new JSONObject();
		attributes.put("razorpay_order_id", orderId);
		attributes.put("razorpay_payment_id", paymentId);
		attributes.put("razorpay_signature", signature);

		return Utils.verifyPaymentSignature(attributes, keySecret);
	}
}