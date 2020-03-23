package guru.samples.jms.publisher;

import guru.samples.jms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static guru.samples.jms.config.JmsConfiguration.HELLO_WORLD_QUEUE;
import static java.util.UUID.randomUUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloWorldMessagePublisher {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 3000)
    public void sendMessage() {
        log.info("Sending message...");

        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(randomUUID())
                .message("Hello world!")
                .build();

        jmsTemplate.convertAndSend(HELLO_WORLD_QUEUE, message);

        log.info("Message sent!");
    }
}
