package com.example.messagereader.service;

public interface KafkaConsumer {

    void consume(String message);

}
