package com.example.test.demo;

import org.springframework.stereotype.Service;

import com.azure.spring.messaging.servicebus.implementation.core.annotation.ServiceBusListener;

@Service
public class EConsumerService {

    private static final String QUEUE_NAME = "email_queue";

    @ServiceBusListener(destination = QUEUE_NAME)
    public void handleMessageFromServiceBus(String message) {
        System.out.printf("Consume message: %s%n", message);
    }
}
