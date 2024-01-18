//package com.example.demo.api;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@Component
//public class Consumer {
//
//        @KafkaListener(topics = "demo_java")
//    public void voidString(@Payload String k){
//        log.info("The message Started consuming");
//        log.info("Received value "+k);
//    }
//}
