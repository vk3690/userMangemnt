package com.user.monitor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.monitor.dto.KafkaTopicDto;
import com.user.monitor.entity.UserMonitor;
import com.user.monitor.repo.UserMonitorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {


    private static final Logger logger = LoggerFactory.getLogger("KafkaConsumer");

    @Autowired
    private UserMonitorRepo userMonitorRepo;

    @KafkaListener(topics = "user-events", groupId = "userid")
    public void consume(String message) {

        try {
            logger.info("Consumed message: {}", message);
            UserMonitor userMonitor;
            ObjectMapper mapper = new ObjectMapper();
            try {
                KafkaTopicDto kafkaTopicDto = mapper.readValue(message, KafkaTopicDto.class);
                userMonitor = new UserMonitor(kafkaTopicDto.getAction(), (String) kafkaTopicDto.getDetails());
            } catch (Exception e) {
                userMonitor = new UserMonitor(message);
                logger.error("Error to convert to object {}", e.getLocalizedMessage());
            }
            userMonitorRepo.saveAndFlush(userMonitor);
        } catch (Exception e) {
            logger.error("Error to consume message :: {}", e.getLocalizedMessage());
        }
    }
}
