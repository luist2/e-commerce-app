package com.luist2.ecommerce.service;

import com.luist2.ecommerce.notification.PaymentNotificationRequest;
import com.luist2.ecommerce.dto.payment.PaymentRequest;
import com.luist2.ecommerce.mapper.PaymentMapper;
import com.luist2.ecommerce.notification.NotificationProducer;
import com.luist2.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest paymentRequest) {
        var payment = paymentRepository.save(paymentMapper.toPayment(paymentRequest));

        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        paymentRequest.orderReference(),
                        paymentRequest.amount(),
                        paymentRequest.paymentMethod(),
                        paymentRequest.customer().firstName(),
                        paymentRequest.customer().lastName(),
                        paymentRequest.customer().email()
                )
        );

        return payment.getId();
    }
}
