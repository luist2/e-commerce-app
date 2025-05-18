package com.luist2.ecommerce.dto.payment;

import com.luist2.ecommerce.dto.customer.Customer;
import com.luist2.ecommerce.model.enums.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
