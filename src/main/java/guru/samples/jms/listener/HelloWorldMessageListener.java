package guru.samples.jms.listener;

import guru.samples.jms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

import static guru.samples.jms.config.JmsConfiguration.HELLO_WORLD_QUEUE;
import static guru.samples.jms.config.JmsConfiguration.REPLY_BACK_QUEUE;
import static java.util.UUID.randomUUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloWorldMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = HELLO_WORLD_QUEUE)
    public void listenToHelloWorldMessage(@Payload HelloWorldMessage message, @Headers MessageHeaders headers) {
        log.info("Message received!");
        log.info(message.getMessage());
    }

    @JmsListener(destination = REPLY_BACK_QUEUE)
    public void listenToReplyBackMessage(@Payload HelloWorldMessage message, Message textMessage) throws JMSException {
        log.info("Message received!");
        log.info(message.getMessage());

        HelloWorldMessage replyBackMessage = HelloWorldMessage.builder()
                .id(randomUUID())
                .message("Replied!")
                .build();

        jmsTemplate.convertAndSend(textMessage.getJMSReplyTo(), replyBackMessage);
    }
}
