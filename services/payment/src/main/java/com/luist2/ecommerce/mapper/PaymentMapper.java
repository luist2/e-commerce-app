package com.luist2.ecommerce.mapper;

import com.luist2.ecommerce.dto.payment.PaymentRequest;
import com.luist2.ecommerce.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment toPayment(PaymentRequest paymentRequest) {

        return Payment.builder()
                .id(paymentRequest.id())
                .amount(paymentRequest.amount())
                .paymentMethod(paymentRequest.paymentMethod())
                .orderId(paymentRequest.orderId())
                .build();
    }
}
