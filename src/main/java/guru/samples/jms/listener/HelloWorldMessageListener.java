package guru.samples.jms.listener;

import guru.samples.jms.model.HelloWorldMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static guru.samples.jms.config.JmsConfiguration.HELLO_WORLD_QUEUE;

@Slf4j
@Component
public class HelloWorldMessageListener {

    @JmsListener(destination = HELLO_WORLD_QUEUE)
    public void listen(@Payload HelloWorldMessage message, @Headers MessageHeaders headers) {
        log.info("Message received!");
        log.info(message.getMessage());
    }
}
