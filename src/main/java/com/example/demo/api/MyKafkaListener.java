package com.example.demo.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import java.util.Map;
@Component
@Slf4j
public class MyKafkaListener implements ConsumerSeekAware {

    private ConsumerSeekCallback seekCallback;

    @KafkaListener(topics = "demo_java",groupId = "my-group")
    public void listen(ConsumerRecord<?, ?> record) {
        if(record.offset()==100)
        // Process the incoming message
            System.out.println("The value is "+record.key()+" "+record.value()+"THe value of offset is "+  record.offset());
    }

    @Override
    public void registerSeekCallback(ConsumerSeekCallback callback) {
        this.seekCallback = callback;
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        // Save the callback for seeking
        this.seekCallback = callback;
        // Optionally seek to specific offsets
        assignments.forEach((topicPartition, offset) -> {
            // Seek to specific offsets
            if (topicPartition.partition() == 0) {
                // Seek to a specific offset (e.g., 100)
                log.info("Seeking starts");
                seekCallback.seek(topicPartition.topic(), 0, 100);
                log.info("Seeking finished with success");

            } else {
                // Seek to the beginning of the partition
                seekCallback.seekToBeginning(topicPartition.topic(), topicPartition.partition());
                log.info("Seeking failed");
            }
        });
    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        // Handle idle container
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        // Handle partition revocation
    }
}