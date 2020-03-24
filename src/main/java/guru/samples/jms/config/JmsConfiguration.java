package guru.samples.jms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import static org.springframework.jms.support.converter.MessageType.TEXT;

@Configuration
public class JmsConfiguration {

    public static final String HELLO_WORLD_QUEUE = "hello-world";
    public static final String REPLY_BACK_QUEUE = "reply-back";

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        converter.setTargetType(TEXT);
        converter.setTypeIdPropertyName("_type");

        return converter;
    }
}
