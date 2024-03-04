package com.example.messagereader.service.impl;

import com.example.messagereader.entity.MessageEntity;
import com.example.messagereader.repository.MessageRepository;
import com.example.messagereader.service.KafkaConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaConsumerImpl implements KafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(KafkaConsumerImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageRepository messageRepository;

    @Override
    @KafkaListener(topics = "json_messages", groupId = "message_group")
    public void consume(String message) {
        try {
            if (!isValidMessage(message)) {
                log.warn("Received invalid message: {}", message);
                return;
            }

            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMessage(message);
            messageRepository.save(messageEntity);

            log.info("Received and saved message: {}", message);
        } catch (Exception e) {
            log.error("Error while consuming and saving message: {}", message, e);
        }
    }

    private boolean isValidMessage(String message) {
        try {
            if (message == null || message.trim().isEmpty()) {
                log.error("Received null or empty message");
                return false;
            }

            objectMapper.readTree(message);
            return true;
        } catch (Exception e) {
            log.error("Invalid message format: {}, error: {}", message, e.getMessage());
            return false;
        }
    }
}
