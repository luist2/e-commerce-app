package com.luist2.ecommerce.kafka;

import com.luist2.ecommerce.entity.Notification;
import com.luist2.ecommerce.kafka.order.OrderConfirmation;
import com.luist2.ecommerce.kafka.payment.PaymentConfirmation;
import com.luist2.ecommerce.model.enums.NotificationType;
import com.luist2.ecommerce.repository.NotificationRepository;
import com.luist2.ecommerce.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "${payment-topic}")
    public void consumePaymentSucessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming the message from payment-topic: {}", paymentConfirmation);
        // Save the notification to the database
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();

        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );

    }

    @KafkaListener(topics = "${order-topic}")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming the message from order-topic: {}", orderConfirmation);
        // Save the notification to the database
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        var customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();

        emailService.sentOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );

    }
}
