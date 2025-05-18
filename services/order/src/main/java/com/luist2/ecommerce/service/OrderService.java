package com.luist2.ecommerce.service;

import com.luist2.ecommerce.client.CustomerClient;
import com.luist2.ecommerce.client.PaymentClient;
import com.luist2.ecommerce.client.ProductClient;
import com.luist2.ecommerce.dto.order.OrderLineRequest;
import com.luist2.ecommerce.dto.order.OrderRequest;
import com.luist2.ecommerce.dto.order.OrderResponse;
import com.luist2.ecommerce.dto.payment.PaymentRequest;
import com.luist2.ecommerce.dto.product.PurchaseRequest;
import com.luist2.ecommerce.exception.BusinessException;
import com.luist2.ecommerce.kafka.OrderConfirmation;
import com.luist2.ecommerce.kafka.OrderProducer;
import com.luist2.ecommerce.mapper.OrderMapper;
import com.luist2.ecommerce.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest orderRequest) {

        var customer = customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order. Customer not found"));

        var purchasedProducts = this.productClient.purchaseProducts(orderRequest.products());

        // persistir la orden
        var order = this.orderRepository.save(orderMapper.toOrder(orderRequest));

        for (PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                orderRequest.totalAmount(),
                orderRequest.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        // enviar la orden de confirmacion --> notification microservice (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.totalAmount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse findOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %s not found", orderId)));
    }
}
