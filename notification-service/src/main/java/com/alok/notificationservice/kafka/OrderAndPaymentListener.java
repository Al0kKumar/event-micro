package com.alok.notificationservice.kafka;

import com.alok.notificationservice.dto.OrderPlacedEvent;
import com.alok.notificationservice.dto.PaymentStatusEvent;
import com.alok.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderAndPaymentListener {

    private final EmailService emailService;

    // Listener for the Order Placed event
    @KafkaListener(topics = "order-placed-topic", groupId = "notification-service-group")
    public void listenOrderPlacedEvent(OrderPlacedEvent event) {
        System.out.println("Received order event for notification: " + event.getOrderId());

        // This method will send the "Order Placed" email
        emailService.sendOrderConfirmation(event.getUserEmail(), event.getOrderId());
    }

    // Listener for the Payment Status event
    @KafkaListener(topics = "payment-status-topic", groupId = "notification-service-group")
    public void listenPaymentStatusEvent(PaymentStatusEvent event) {
        System.out.println("Received payment status event for order: " + event.getOrderId());

        // This method will send the "Payment Successful/Failed" email
        emailService.sendPaymentStatus(event.getUserEmail(), event.getOrderId(), event.getStatus());
    }
}
