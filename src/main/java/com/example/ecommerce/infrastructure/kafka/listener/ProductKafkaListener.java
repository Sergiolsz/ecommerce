package com.example.ecommerce.infrastructure.kafka.listener;

import static com.example.ecommerce.common.utils.MessageCodes.GROUP_ID_ECOMMERCE_SERVICE;
import static com.example.ecommerce.common.utils.MessageCodes.TOPIC_PRODUCT_CREATED;

import com.example.ecommerce.domain.model.Product;
import com.example.ecommerce.domain.repository.ProductCommandRepository;
import com.example.ecommerce.domain.repository.ProductQueryRepository;
import com.example.ecommerce.infrastructure.avro.ProductAvro;
import com.example.ecommerce.infrastructure.mapper.ProductAvroMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductKafkaListener {

    private final ProductCommandRepository productCommandRepository;
    private final ProductQueryRepository productQueryRepository;
    private final ProductAvroMapper productAvroMapper;

    @KafkaListener(
            topics = TOPIC_PRODUCT_CREATED,
            groupId = GROUP_ID_ECOMMERCE_SERVICE
    )
    public void createProductEvent(ProductAvro productAvro) {
        log.info("Consumed event from Kafka: {}", productAvro);

        Product product = productAvroMapper.avroToProduct(productAvro);

        if (product == null) {
            log.warn("Received null product from Avro: {}", productAvro);
            return;
        }

        boolean saved = productCommandRepository.saveProductIfNotExists(product);

        if (saved) {
            log.info("Product saved to DB: {}", product);
        } else {
            log.info("Product with id {} is a duplicate. Skipping save.", product.productId());
        }
    }
}