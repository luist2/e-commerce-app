package com.luist2.ecommerce.dto.order;

import com.luist2.ecommerce.dto.product.PurchaseRequest;
import com.luist2.ecommerce.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,

        @Positive(message = "Total amount must be positive")
        BigDecimal totalAmount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Customer ID is required")
        @NotEmpty(message = "Customer ID is required")
        @NotBlank(message = "Customer ID is required")
        String customerId,

        @NotEmpty(message = "At least one product must be purchased")
        List<PurchaseRequest> products
) {
}
