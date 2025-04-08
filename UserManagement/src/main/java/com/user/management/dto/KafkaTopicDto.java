package com.user.management.dto;

import lombok.Data;

@Data
public class KafkaTopicDto {

    private String action;
    private Object details;

    public KafkaTopicDto(String action, Object details) {
        this.action = action;
        this.details = details;
    }
}
