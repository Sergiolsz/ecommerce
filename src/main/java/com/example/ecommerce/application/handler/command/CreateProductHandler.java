package com.example.ecommerce.application.handler.command;


import com.example.ecommerce.common.i18n.MessageHandler;
import com.example.ecommerce.domain.exception.EventException;
import com.example.ecommerce.infrastructure.avro.ProductAvro;
import com.example.ecommerce.infrastructure.kafka.listener.ProductKafkaProducer;
import com.example.ecommerce.infrastructure.mapper.ProductAvroMapper;
import com.example.ecommerce.infrastructure.rest.dto.ProductRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateProductHandler {

    private final ProductKafkaProducer kafkaProducer;
    private final ProductAvroMapper productAvroMapper;
    private final MessageHandler messageHandler;

    /**
     * Handles the creation of a new product by sending a product created event to Kafka.
     *
     * @param productRequestDTO The product data.
     */
    public void createProduct(ProductRequestDTO productRequestDTO) {
        log.info("Request received to create product. productId={}",
                productRequestDTO.productId());

        try {
            ProductAvro productAvro = productAvroMapper.productDtoToAvro(productRequestDTO);
            log.info("Avro object to send: {}", productAvro);
            kafkaProducer.sendProductCreatedEvent(productAvro);
            log.info("Product created event sent to Kafka successfully. productId={}", productRequestDTO.productId());
        } catch (Exception e) {
            log.error("Failed to send product created event to Kafka. productId={}, error={}",
                    productRequestDTO.productId(), e.getMessage(), e);
            throw new EventException(messageHandler);
        }
    }

}