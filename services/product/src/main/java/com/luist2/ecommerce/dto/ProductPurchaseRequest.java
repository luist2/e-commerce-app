package com.luist2.ecommerce.dto;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Product ID is required")
        Integer productId,

        @NotNull(message = "Product quantity is required")
        double quantity

) {
}
