package com.example.demo.api;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

//    @KafkaListener(topics = "demo_java", groupId = "my-group")
//    public void listen(String message) {
//        System.out.println("Received message: " + message);
//        // Process the message as needed
//    }
}