package guru.samples.jms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import static org.springframework.jms.support.converter.MessageType.TEXT;

@Configuration
public class JmsConfiguration {

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        converter.setTargetType(TEXT);
        converter.setTypeIdPropertyName("_type");

        return converter;
    }
}
