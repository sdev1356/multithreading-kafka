package com.example.demo.api;

import com.example.demo.Dispatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

@RestController
@Slf4j
public class controller {
    @GetMapping("/")
    public String index(){
        return "Index Controller Working";
    }
    @GetMapping("/s")
    public List<SamplePojo> send(){
        List<SamplePojo>r=new ArrayList<>();
        String topicName = "demo_java";
        String[] eventFiles = {"1","2","3","4","5","6","7","8","9","10"};
        List<SamplePojo>lsp=new ArrayList<>();
        for(int i=1;i<=100;i++){
            lsp.add(new SamplePojo(String.valueOf(i)));
        }
        ExecutorService executor = Executors.newFixedThreadPool(5);

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        log.trace("Starting dispatcher threads...");
        KafkaProducer<Integer, SamplePojo> producer = new KafkaProducer<>(properties);
        List<Future<SamplePojo>> futures = new ArrayList<>();
        Thread[] dispatchers = new Thread[eventFiles.length];
        for (int i = 0; i < lsp.size(); i++) {
            Callable<SamplePojo> callableTask = new Dispatcher(producer, topicName, lsp.get(i));
//            dispatchers[i] = new Thread((new Dispatcher(producer, topicName, eventFiles[i])));
            Future<SamplePojo> future = executor.submit(callableTask);
            futures.add(future);
//            dispatchers[i].start();
        }

//        try {
//            for (Thread t : dispatchers)
//                t.join();
//        } catch (InterruptedException e) {
//            log.error("Thread Interrupted ");
//        } finally {
//            producer.close();
//            log.info("Finished dispatcher demo - Closing Kafka Producer.");
//        }

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
            // Shutdown the executor
            executor.shutdown();
        }
        return r;
    }
}
