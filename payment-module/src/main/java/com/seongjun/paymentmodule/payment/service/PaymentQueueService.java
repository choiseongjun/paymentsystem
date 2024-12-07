package com.seongjun.paymentmodule.payment.service;

import com.seongjun.paymentmodule.common.config.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PaymentQueueService {

    private static final String QUEUE_KEY = "payment:queue";

    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private final ReactiveValueOperations<String, String> valueOps;
    private final KafkaProducerService kafkaProducerService;


    public PaymentQueueService(@Qualifier("myReactiveRedisTemplate") ReactiveRedisTemplate<String, String> redisTemplate, KafkaProducerService kafkaProducerService) {
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue(); // ReactiveValueOperations 생성
        this.kafkaProducerService = kafkaProducerService;
    }

    public Mono<Long> enqueue(String requestId) {
        return redisTemplate.opsForList().rightPush(QUEUE_KEY, requestId);
    }

    public Mono<Long> getQueuePosition(String requestId) {
        return redisTemplate.opsForList().range(QUEUE_KEY, 0, -1)
                .collectList()
                .flatMap(list -> {
                    int index = list.indexOf(requestId);
                    if (index == -1) {
                        return Mono.empty();
                    }
                    return Mono.just((long) index);
                });
    }

    public Mono<String> dequeue() {
        return redisTemplate.opsForList().leftPop(QUEUE_KEY);
    }
    public Mono<Void> processAllUsers() {
        return redisTemplate.opsForList().size(QUEUE_KEY)
                .flatMapMany(size -> Flux.range(0, size.intValue()))
                .flatMap(i -> dequeue()
                        .flatMap(userId -> {
                            if (userId != null) {
                                kafkaProducerService.sendMessage(userId); 
                                log.info("Sent to Kafka: " + userId);
                            }
                            return Mono.empty();
                        })
                )
                .then();
    }
}