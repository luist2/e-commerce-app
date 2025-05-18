package com.luist2.ecommerce.kafka;

import com.luist2.ecommerce.dto.customer.CustomerResponse;
import com.luist2.ecommerce.dto.product.PurchaseResponse;
import com.luist2.ecommerce.model.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
