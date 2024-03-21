package com.example.demo.api;

import com.example.demo.Dispatcher;



import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

@RestController
@Slf4j
@EnableAsync
public class controller {
    ExecutorService executor = Executors.newFixedThreadPool(5);
    @GetMapping("/")
    public String index(){
        return "Index Controller Working";
    }
    @GetMapping("/s")
    public String send() throws InterruptedException {
        List<SamplePojo>r=new ArrayList<>();
        String topicName = "demo_java";
//        String[] eventFiles = {"1","2","3","4","5","6","7","8","9","10"};
        List<SamplePojo>lsp=new ArrayList<>();
        for(int i=1;i<=100;i++){
            lsp.add(new SamplePojo(String.valueOf(i)));
        }


        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        log.trace("Starting dispatcher threads...");
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(properties);
        List<Future<SamplePojo>> futures = new ArrayList<>();
//        Thread[] dispatchers = new Thread[eventFiles.length];
        for (int i = 0; i < lsp.size(); i++) {
            Callable<SamplePojo> callableTask = new Dispatcher(producer, topicName, lsp.get(i));
//            dispatchers[i] = new Thread((new Dispatcher(producer, topicName, eventFiles[i])));
            Future<SamplePojo> future = executor.submit(callableTask);
            futures.add(future);
//            dispatchers[i].start();

        }

        try {
            // Retrieve results from the Future objects
            for (Future<SamplePojo> future : futures) {
                SamplePojo result = future.get();
               log.info("Result from a thread: " + result);
               r.add(result);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Shut down the executor service
//            executor.shutdown();

        }

        return "r";
    }



}
