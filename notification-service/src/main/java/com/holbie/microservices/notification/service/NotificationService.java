package com.holbie.microservices.notification.service;

import com.holbie.microservices.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Listening for events...");
        log.info("Received OrderPlacedEvent: {}", orderPlacedEvent);

        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom("nihad.gurbanov85@gmail.com");
            helper.setTo(orderPlacedEvent.getEmail().toString());
            log.info("_____________________Email sent to: {}___________________", orderPlacedEvent.getEmail());
            helper.setSubject(String.format("Order %s has been placed successfully", orderPlacedEvent.getOrderNumber()));
            helper.setText(String.format(
                    """
                            Hi,
                            Your order has been placed successfully. Your order number is %s.
                            Thanks for shopping with us.
                            
                            Regards,
                            Microservice Shop Team
                    """, orderPlacedEvent.getOrderNumber()));
        };

        try {
            javaMailSender.send(message);
            log.info("Email sent successfully");
        }
        catch (MailException e) {
            log.error("Failed to send email", e);
            throw new RuntimeException("Failed to send email");
        }

    };
}
