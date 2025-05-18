package com.luist2.ecommerce.client;

import com.luist2.ecommerce.dto.product.PurchaseRequest;
import com.luist2.ecommerce.dto.product.PurchaseResponse;
import com.luist2.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class ProductClient {
    @Value("${application.config.product-url}")
    private String productUrl;

    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequests) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(purchaseRequests, headers);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<List<PurchaseResponse>>() {
                };

        ResponseEntity<List<PurchaseResponse>> responseEntity =
                restTemplate.exchange(
                        productUrl + "/api/v1/products/purchase",
                        POST,
                        requestEntity,
                        responseType
                );

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new BusinessException("Error purchasing products:" + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();

    }
}
