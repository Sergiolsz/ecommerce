package com.example.ecommerce.infrastructure.config;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private String bootstrapServers;

    private Producer producer = new Producer();
    private Consumer consumer = new Consumer();

    @Setter
    @Getter
    public static class Producer {

        private String keySerializer;
        private String valueSerializer;
        private Map<String, String> properties;

    }

    @Setter
    @Getter
    public static class Consumer {

        private String groupId;
        private String keyDeserializer;
        private String valueDeserializer;
        private Map<String, String> properties;

    }
}