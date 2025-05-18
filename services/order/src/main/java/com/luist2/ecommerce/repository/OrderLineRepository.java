package com.luist2.ecommerce.repository;

import com.luist2.ecommerce.dto.order.OrderLineResponse;
import com.luist2.ecommerce.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);

}
