package com.user.management.service;

import com.user.management.dto.KafkaTopicDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger("KafkaProducer");


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "user-events";

    public void sendMessage(KafkaTopicDto message) {

        logger.info("pRDOUCE message {}",message);
        kafkaTemplate.send(TOPIC, message);
    }
}
