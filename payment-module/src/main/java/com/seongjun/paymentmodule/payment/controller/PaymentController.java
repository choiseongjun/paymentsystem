package com.seongjun.paymentmodule.payment.controller;

import com.seongjun.paymentmodule.payment.service.PaymentQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentQueueService paymentQueueService;

    @Autowired
    public PaymentController(PaymentQueueService paymentQueueService) {
        this.paymentQueueService = paymentQueueService;
    }

//    @PostMapping("/queue")
//    public Mono<ResponseEntity<String>> enqueuePayment() {
//        String requestId = UUID.randomUUID().toString();
//        return paymentQueueService.enqueue(requestId)
//                .map(position -> ResponseEntity.ok("Your position in the queue: " + position));
//    }
    @PostMapping("/enqueue")
    public Mono<Map<String, Object>> enqueuePayment(@RequestParam String userId) {
        return paymentQueueService.enqueue(userId)
                .map(position -> Map.of("message", "User added to queue at position: " + position));
    }
    @PostMapping("/process")
    public Mono<Map<String, Object>> processAllUsers() {
        return paymentQueueService.processAllUsers()
                .then(Mono.just(Map.of("message", "All users processed and sent to Kafka")));
    }
    @GetMapping("/queue/{requestId}")
    public Mono<ResponseEntity<String>> getQueueStatus(@PathVariable String requestId) {
        return paymentQueueService.getQueuePosition(requestId)
                .map(position -> ResponseEntity.ok("Your position: " + position))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping("/dequeue")
    public Mono<Map<String, String>> dequeueUser() {
        return paymentQueueService.dequeue()
                .map(userId -> Map.of("message", "Processed user: " + userId))
                .defaultIfEmpty(Map.of("message", "Queue is empty"));
    }
}