package com.luist2.ecommerce.dto.customer;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
