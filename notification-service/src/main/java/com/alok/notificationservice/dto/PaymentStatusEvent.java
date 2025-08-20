package com.alok.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusEvent {
    private Long userId;
    private Long orderId;
    private String status;
    private String userEmail;
}