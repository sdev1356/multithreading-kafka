package com.example.demo;

import com.example.demo.api.SamplePojo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Callable;

@Slf4j
public class Dispatcher implements Callable<SamplePojo> {

    private final KafkaProducer<Integer, SamplePojo> producer;
    private final String topicName;
    private final SamplePojo fileLocation;


    public Dispatcher(KafkaProducer<Integer, SamplePojo> producer, String topicName, SamplePojo fileLocation) {
        this.producer = producer;
        this.topicName = topicName;
        this.fileLocation = fileLocation;
    }

    @Override
    public SamplePojo call() throws InterruptedException {
        log.info("Start processing " + fileLocation + "...");

        int msgKey = 0;
            log.info("The current thread is "+Thread.currentThread().getName() +" And the value is "+fileLocation);
                producer.send(new ProducerRecord<>(topicName, msgKey, fileLocation));
            return fileLocation;
    }
} 
