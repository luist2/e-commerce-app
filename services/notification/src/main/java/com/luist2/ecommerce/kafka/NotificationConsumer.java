package com.luist2.ecommerce.kafka;

import com.luist2.ecommerce.entity.Notification;
import com.luist2.ecommerce.kafka.payment.PaymentConfirmation;
import com.luist2.ecommerce.model.enums.NotificationType;
import com.luist2.ecommerce.repository.NotificationRepository;
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
    // private final EmailService emailService;

    @KafkaListener(topics = "${payment-topic}")
    public void consumePaymentSucessNotification(PaymentConfirmation paymentConfirmation) {
        log.info("Consuming the message from payment-topic: {}", paymentConfirmation);
        // Save the notification to the database
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );
        // Send email notification
        // emailService.sendEmail(paymentConfirmation);
    }
}
