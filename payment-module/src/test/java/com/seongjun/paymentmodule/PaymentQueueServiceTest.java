package com.seongjun.paymentmodule;

import com.seongjun.paymentmodule.payment.service.PaymentQueueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.IntStream;

@SpringBootTest
public class PaymentQueueServiceTest {

    @Autowired
    private PaymentQueueService paymentQueueService;

    @Autowired
    @Qualifier("myReactiveRedisTemplate")
    private ReactiveRedisTemplate<String, String> redisTemplate;

    private static final String QUEUE_KEY = "payment:queue";

    @BeforeEach
    void setup() {
        // 테스트 실행 전 Redis 초기화
        redisTemplate.delete(QUEUE_KEY).block();
    }

    @Test
    void testEnqueueAndGetQueuePosition() {
        // 1000명의 요청 ID를 생성
        Flux<String> userRequests = Flux.fromStream(
                IntStream.rangeClosed(1, 1000).mapToObj(i -> "user-" + i)
        );

        // 대기열에 1000명의 요청 추가
        Mono<Long> enqueueAll = userRequests
                .flatMap(paymentQueueService::enqueue)
                .last();

        // 특정 사용자의 순위 확인
        Mono<Long> checkUserPosition = enqueueAll
                .then(paymentQueueService.getQueuePosition("user-500")); // 500번 사용자 순위 확인

        StepVerifier.create(checkUserPosition)
                .expectNext(499L) // 0부터 시작하므로 user-500은 499번째
                .verifyComplete();
    }
}