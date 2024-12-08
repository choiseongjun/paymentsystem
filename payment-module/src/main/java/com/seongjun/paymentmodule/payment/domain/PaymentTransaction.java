package com.seongjun.paymentmodule.payment.domain;

import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Table(name="payment_transactions")
@Builder
public class PaymentTransaction {
    @Id
    private Long id;
    private String userId;
    private Integer amount;
    private String status; // 결제 상태 (e.g., SUCCESS, FAILED)
    private LocalDateTime processedAt = LocalDateTime.now();
}