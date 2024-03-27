package com.example.test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.azure.spring.messaging.implementation.annotation.EnableAzureMessaging;

@SpringBootApplication
@EnableAzureMessaging
public class DemoApplication {

    public static void main(String[] args) {
       /*  ConfigurableApplicationContext applicationContext =*/ 
       SpringApplication.run(DemoApplication.class);
       // ServiceBusTemplate serviceBusTemplate = applicationContext.getBean(ServiceBusTemplate.class);
       // System.out.println("Sending a message to the queue.");
      //  serviceBusTemplate.sendAsync(QUEUE_NAME, MessageBuilder.withPayload("Hello world").build()).subscribe();
		//System.out.println("Message Sent to the queue");
    }

}
