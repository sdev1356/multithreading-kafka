//package com.example.demo.api;
//
//import com.example.demo.Dispatcher;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.IntegerSerializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.Properties;
//import java.util.Scanner;
//
//@Slf4j
//public class Multithreading {
//    public static void main(String[] args) {
//
//
//        String topicName = "demo_java";
//        S[] eventFiles = {1,2,3,4,5,6,7,8,9,10};
//
//        Properties properties = new Properties();
//        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
//
//        log.trace("Starting dispatcher threads...");
//        KafkaProducer<Integer, String> producer = new KafkaProducer<>(properties);
//
//        Thread[] dispatchers = new Thread[eventFiles.length];
//        for (int i = 0; i < eventFiles.length; i++) {
//            dispatchers[i] = new Thread(new Dispatcher(producer, topicName, eventFiles[i]));
//            dispatchers[i].start();
//        }
//
//        try {
//            for (Thread t : dispatchers)
//                t.join();
//        } catch (InterruptedException e) {
//            log.error("Thread Interrupted ");
//        } finally {
//            producer.close();
//            log.info("Finished dispatcher demo - Closing Kafka Producer.");
//        }
//    }
//}
