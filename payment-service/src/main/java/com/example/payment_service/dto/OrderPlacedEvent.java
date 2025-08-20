package com.example.payment_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private Long userId;

    private String orderId;

    private String userEmail;

    private double amount;

    private String status;
}
