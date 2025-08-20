package com.example.payment_service.kafka;

import com.example.payment_service.dto.OrderPlacedEvent;
import com.example.payment_service.dto.PaymentStatusEvent;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPlacedListener {

    private final PaymentRepository paymentRepository;
    private static final String PAYMENT_STATUS_TOPIC = "payment-status-topic";
    private final KafkaTemplate<String,PaymentStatusEvent> kafkaTemplate;

    @KafkaListener(topics = "order-placed-topic", groupId = "payment-service-group")
    @Transactional
    public void listen(OrderPlacedEvent event){
        System.out.println("Received message for order: " + event.getOrderId());

        // Simulate payment processing
        boolean paymentSuccessful = Math.random() > 0.1;

        String paymentStatus = paymentSuccessful ? "PAID" : "FAILED";

        // Save the payment status to the database
        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId());
        payment.setUserEmail(event.getUserEmail());
        payment.setUserId(event.getUserId());
        payment.setAmount(event.getAmount());
        payment.setStatus(paymentStatus);

        paymentRepository.save(payment);

        System.out.println("Payment for order " + event.getOrderId() + " processed with status: " + paymentStatus);

        PaymentStatusEvent paymentStatusEvent = new PaymentStatusEvent();
        paymentStatusEvent.setOrderId(event.getOrderId());
        paymentStatusEvent.setUserId(event.getUserId());
        paymentStatusEvent.setStatus(paymentStatus);
        paymentStatusEvent.setUserEmail(event.getUserEmail());

        kafkaTemplate.send(PAYMENT_STATUS_TOPIC, paymentStatusEvent);

    }

}
