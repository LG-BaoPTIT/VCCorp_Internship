package com.ite.authservice.event.eventProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ite.authservice.config.SystemLogger;
import com.ite.authservice.constants.LogStepConstant;
import com.ite.authservice.event.messages.LockAccountNotification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SystemLogger logger;

    public void publishLockAccountNotificationEvent(LockAccountNotification notification){
        try {
            logger.log(Thread.currentThread().getName(), "Publish lock account notification event", LogStepConstant.BEGIN_PROCESS, notification.getEmail());
            String json = objectMapper.writeValueAsString(notification);
            rabbitTemplate.convertAndSend("email-exchange","lockAccountNotice.routing.key",json);
            logger.log(Thread.currentThread().getName(), "Publish lock account notification event", LogStepConstant.END_PROCESS, "Publishing event successful: " + notification.getEmail());
        }catch (JsonProcessingException e){
            logger.log(Thread.currentThread().getName(), "Publish lock account notification event", LogStepConstant.END_PROCESS, "publishing event failed: " + notification.getEmail());
            throw new RuntimeException(e);
        }
    }
}
