package com.seongjun.paymentmodule.common.config;

import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Table(name="payment_transactions")
public class PaymentTransaction {
    @Id
    private Long id;
    private String userId;
    private Integer amount;
    private LocalDateTime processedAt = LocalDateTime.now();
}