package ru.alov.market.messaging.services;

public enum KafkaTopic {

    USER_PROFILE_DTO("user");

    private final String topic;

    KafkaTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
