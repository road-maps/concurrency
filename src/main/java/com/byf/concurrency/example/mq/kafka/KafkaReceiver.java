package com.byf.concurrency.example.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaReceiver {

    @KafkaListener(topics = {TopicConstants.TEST})
    public void receive(ConsumerRecord<?,?> record){
        log.info("kafka receive record:{}", record);
    }
}
