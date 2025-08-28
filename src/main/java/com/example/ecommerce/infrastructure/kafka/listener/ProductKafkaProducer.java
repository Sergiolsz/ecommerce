package com.example.ecommerce.infrastructure.kafka.listener;

import static com.example.ecommerce.common.utils.MessageCodes.TOPIC_PRODUCT_CREATED;

import com.example.ecommerce.infrastructure.avro.ProductAvro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductKafkaProducer {

    private final KafkaTemplate<String, ProductAvro> kafkaTemplate;

    /**
     * Sends a ProductCreatedEvent to the Kafka topic "products-created".
     *
     * @param productAvro the product event to send
     */
    public void sendProductCreatedEvent(ProductAvro productAvro) {
        log.info("Sending event to Kafka: {}", productAvro);
        try {
            kafkaTemplate.send(
                    TOPIC_PRODUCT_CREATED,
                    String.valueOf(productAvro.getProductId()),
                    productAvro
            ).whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Event sent successfully, offset={}",
                            result.getRecordMetadata().offset());
                } else {
                    log.error("Failed to send event in callback", ex);
                }
            });
        } catch (Exception e) {
            log.error("Failed to send event (serialization or immediate error)", e);
        }
    }
}