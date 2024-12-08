package com.seongjun.paymentmodule.payment.service;

import com.seongjun.paymentmodule.payment.domain.PaymentTransaction;
import com.seongjun.paymentmodule.payment.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

   private final PaymentTransactionRepository paymentTransactionRepository;

    public void paymentSave(PaymentTransaction payment) {
        paymentTransactionRepository.save(payment);
    }
}
