package ru.alov.market.auth.configs;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.alov.market.api.dto.RecoverPasswordDto;
import ru.alov.market.api.dto.UserProfileDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.producer}")
    private String kafkaProducerId;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId);
        return props;
    }

    @Bean
    public ProducerFactory<String, UserProfileDto> userProfileDtoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, UserProfileDto> userProfileDtoKafkaTemplate() {
        KafkaTemplate<String, UserProfileDto> template = new KafkaTemplate<>(userProfileDtoProducerFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<String, RecoverPasswordDto> recoverPasswordDtoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, RecoverPasswordDto> recoverPasswordDtoKafkaTemplate() {
        KafkaTemplate<String, RecoverPasswordDto> template = new KafkaTemplate<>(recoverPasswordDtoProducerFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
}