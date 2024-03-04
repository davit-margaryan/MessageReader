package com.example.messagereader;

import com.example.messagereader.entity.MessageEntity;
import com.example.messagereader.repository.MessageRepository;
import com.example.messagereader.service.impl.KafkaConsumerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class KafkaConsumerImplTest {

    @InjectMocks
    private KafkaConsumerImpl kafkaConsumer;

    @Mock
    private MessageRepository messageRepository;

    @Captor
    private ArgumentCaptor<MessageEntity> captor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsumeSavesValidJsonMessage() {
        String validJsonMessage = "{\"valid\":\"Hello World!\"}";

        kafkaConsumer.consume(validJsonMessage);

        verify(messageRepository).save(captor.capture());
        assertEquals(validJsonMessage, captor.getValue().getMessage());
    }

    @Test
    public void testConsumeDoesNotSaveInvalidJsonMessage() {
        String invalidJsonMessage = "{'invalid':'Hello World!}";

        kafkaConsumer.consume(invalidJsonMessage);

        verify(messageRepository, never()).save(any(MessageEntity.class));
    }

    @Test
    public void testConsumeDoesNotSaveNullMessage() {
        kafkaConsumer.consume(null);

        verify(messageRepository, never()).save(any(MessageEntity.class));
    }

    @Test
    public void testConsumeDoesNotSaveEmptyMessage() {
        kafkaConsumer.consume("");

        verify(messageRepository, never()).save(any(MessageEntity.class));
    }
}