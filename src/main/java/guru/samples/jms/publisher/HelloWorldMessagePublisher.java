package guru.samples.jms.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.samples.jms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import static guru.samples.jms.config.JmsConfiguration.HELLO_WORLD_QUEUE;
import static guru.samples.jms.config.JmsConfiguration.REPLY_BACK_QUEUE;
import static java.util.UUID.randomUUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloWorldMessagePublisher {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 3000)
    public void sendHelloWorldMessage() {
        log.info("Sending message...");

        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(randomUUID())
                .message("Hello world!")
                .build();

        jmsTemplate.convertAndSend(HELLO_WORLD_QUEUE, message);

        log.info("Message sent!");
    }

    @Scheduled(fixedRate = 5000)
    public void sendReplyBackMessage() {
        log.info("Sending message...");

        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(randomUUID())
                .message("Please reply back!")
                .build();

        Message receivedMessage = jmsTemplate.sendAndReceive(REPLY_BACK_QUEUE, session -> createTextMessage(session, message));
        log.info("Message received back!");
        log.info(extractMessage(receivedMessage));
    }

    private Message createTextMessage(Session session, HelloWorldMessage message) {
        try {
            Message textMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
            textMessage.setStringProperty("_type", HelloWorldMessage.class.getName());
            log.info("Message sent!");
            return textMessage;
        } catch (JsonProcessingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractMessage(Message message) {
        try {
            HelloWorldMessage content = objectMapper.readValue(message.getBody(String.class), HelloWorldMessage.class);
            return content.getMessage();
        } catch (JsonProcessingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
