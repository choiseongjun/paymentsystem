package com.seongjun.paymentmodule.payment.repository;

import com.seongjun.paymentmodule.common.config.PaymentTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PaymentTransactionRepository extends ReactiveCrudRepository<PaymentTransaction, Long> {
}
