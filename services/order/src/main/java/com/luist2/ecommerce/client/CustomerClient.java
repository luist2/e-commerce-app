package com.luist2.ecommerce.client;

import com.luist2.ecommerce.dto.customer.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}"
)
public interface CustomerClient {
    @GetMapping("/api/v1/customers/{customerId}")
    Optional<CustomerResponse> findCustomerById(@PathVariable String customerId);
}
