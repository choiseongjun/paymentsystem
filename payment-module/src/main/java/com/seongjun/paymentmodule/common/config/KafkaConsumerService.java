package com.seongjun.paymentmodule.common.config;

import com.seongjun.paymentmodule.payment.repository.PaymentTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
@Slf4j
public class KafkaConsumerService {

    private final Random random = new Random();

    @KafkaListener(topics = "${spring.kafka.topic.name:payment-queue}", groupId = "payment-group")
    public void consume(String userId) {
        int amount = random.nextInt(1000000 - 10000 + 1) + 10000;


        log.info("Processing payment for user: " + userId + ", amount: " + amount);

        //결제 연결
        boolean paymentSuccess = processPayment(userId, amount);

        if (paymentSuccess) {
            log.info("Payment successful for user: " + userId);
        } else {
            log.info("Payment failed for user: " + userId);
        }
    }

    private boolean processPayment(String userId, int amount) {
        // 외부 결제 시스템과의 연동 로직 (예: HTTP 호출)
        // 여기서는 랜덤 성공/실패 시뮬레이션
        return random.nextBoolean();
    }
}
