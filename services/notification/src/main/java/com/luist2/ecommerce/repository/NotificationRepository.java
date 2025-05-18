package com.luist2.ecommerce.repository;

import com.luist2.ecommerce.entity.Notification;
import com.luist2.ecommerce.kafka.payment.PaymentConfirmation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
