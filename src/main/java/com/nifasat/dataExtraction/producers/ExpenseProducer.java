package com.nifasat.dataExtraction.producers;

import com.nifasat.dataExtraction.model.ExpenseDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ExpenseProducer {
    private final KafkaTemplate<String, ExpenseDetails>kafkaTemplate;
    private final String TOPIC_NAME;

    @Autowired
    public ExpenseProducer(KafkaTemplate<String, ExpenseDetails>kafkaTemplate, @Value("${spring.kafka.topic.name}")String topic){
        this.kafkaTemplate = kafkaTemplate;
        this.TOPIC_NAME = topic;
    }

    public void sendEventToKafka(ExpenseDetails expenseDetails){
        Message<ExpenseDetails> message = MessageBuilder
                .withPayload(expenseDetails)
                .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME)
                .build();
        kafkaTemplate.send(message);
    }

}
