package com.luist2.ecommerce.dto;

import com.luist2.ecommerce.customer.Address;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
