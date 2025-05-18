package com.luist2.ecommerce.dto;

import java.math.BigDecimal;

public record ProductPurchaseResponse(

        Integer productId,
        String name,
        String description,
        double quantity,
        BigDecimal price

) {
}
