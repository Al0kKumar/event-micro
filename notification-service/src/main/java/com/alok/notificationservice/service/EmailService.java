package com.alok.notificationservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // This will be called by the OrderPlacedListener
    public void sendOrderConfirmation(String recipientEmail, Long orderId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com"); // Set your sender email
        message.setTo(recipientEmail);
        message.setSubject("Order Confirmation: #" + orderId);
        message.setText("Dear Customer, your order #" + orderId + " has been successfully placed. Thank you for your purchase!");

        try {
            mailSender.send(message);
            System.out.println("Order confirmation email sent successfully for order: " + orderId);
        } catch (Exception e) {
            System.err.println("Failed to send order confirmation email: " + e.getMessage());
        }
    }

    // This will be called by the PaymentStatusListener
    public void sendPaymentStatus(String recipientEmail, Long orderId, String status) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com"); // Set your sender email
        message.setTo(recipientEmail);

        String subject;
        String text;

        if ("PAID".equals(status)) {
            subject = "Payment Successful for Order #" + orderId;
            text = "Hello! The payment for your order #" + orderId + " was successful. Your items will be shipped soon.";
        } else {
            subject = "Payment Failed for Order #" + orderId;
            text = "Hello! We encountered an issue processing the payment for your order #" + orderId + ". Please try again or update your payment method.";
        }

        message.setSubject(subject);
        message.setText(text);

        try {
            mailSender.send(message);
            System.out.println("Payment status email sent successfully for order: " + orderId);
        } catch (Exception e) {
            System.err.println("Failed to send payment status email: " + e.getMessage());
        }
    }
}