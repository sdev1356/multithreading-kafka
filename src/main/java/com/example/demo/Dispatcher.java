package com.example.demo;

import com.example.demo.api.SamplePojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Callable;

@Slf4j
public class Dispatcher implements Callable<SamplePojo> {

    private final KafkaProducer<Integer, String> producer;
    private final String topicName;
    private final SamplePojo fileLocation;


    public Dispatcher(KafkaProducer<Integer, String> producer, String topicName, SamplePojo fileLocation) {
        this.producer = producer;
        this.topicName = topicName;
        this.fileLocation = fileLocation;
    }

    @Override
    public SamplePojo call() throws InterruptedException, JsonProcessingException {
        log.info("Start processing " + fileLocation + "...");

        int msgKey = 0;
            log.info("The current thread is "+Thread.currentThread().getName() +" And the value is "+fileLocation);
        ObjectMapper objectMapper=new ObjectMapper();
            String json=objectMapper.writeValueAsString(fileLocation);
            producer.send(new ProducerRecord<>(topicName, msgKey, json));
            return fileLocation;
    }
} 
