package com.example.ecommerce.infrastructure.kafka.listener;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ecommerce.domain.model.Product;
import com.example.ecommerce.domain.repository.ProductCommandRepository;
import com.example.ecommerce.domain.repository.ProductQueryRepository;
import com.example.ecommerce.infrastructure.avro.ProductAvro;
import com.example.ecommerce.infrastructure.mapper.ProductAvroMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class ProductKafkaListenerTest {

    @Mock
    private ProductQueryRepository productQueryRepository;

    @Mock
    private ProductCommandRepository productCommandRepository;

    @Mock
    private KafkaTemplate<String, ProductAvro> kafkaTemplate;

    @Mock
    private ProductAvroMapper productAvroMapper;

    @InjectMocks
    private ProductKafkaListener listener;

    @Test
    void testConsumeProductAvro() {
        ProductAvro avro = mock(ProductAvro.class);
        Product product = mock(Product.class);

        when(productAvroMapper.avroToProduct(avro)).thenReturn(product);

        listener.createProductEvent(avro);

        verify(productAvroMapper).avroToProduct(avro);
        verify(productCommandRepository).saveProductIfNotExists(product);
    }

    @Test
    void testListenerConsumesProductAvro_ProductSaved() {
        // Creamos un producto de ejemplo
        ProductAvro productAvro = ProductAvro.newBuilder()
                .setProductId(1001)
                .setBrandId(1)
                .setPriceList(2)
                .setPrice(99.99)
                .setCurrency("EUR")
                .setStartDate("2025-08-27T00:00")
                .setEndDate("2025-12-31T23:59:59")
                .setPriority(1)
                .build();

        // Creamos el Product que devuelve el mapper
        Product product = new Product(
                1001, 1, 2, new BigDecimal("99.99"), "EUR",
                LocalDateTime.parse("2025-08-27T00:00"),
                LocalDateTime.parse("2025-12-31T23:59:59"),
                1
        );

        // Mockeamos el mapper
        when(productAvroMapper.avroToProduct(productAvro)).thenReturn(product);

        // Mockeamos que se guarda correctamente
        when(productCommandRepository.saveProductIfNotExists(product)).thenReturn(true);

        // Llamamos al listener
        listener.createProductEvent(productAvro);

        // Verificamos que se haya llamado al repository
        verify(productCommandRepository).saveProductIfNotExists(product);
    }

    @Test
    void testListenerConsumesProductAvro_ProductDuplicate() {
        ProductAvro productAvro = ProductAvro.newBuilder()
                .setProductId(1001)
                .setBrandId(1)
                .setPriceList(2)
                .setPrice(99.99)
                .setCurrency("EUR")
                .setStartDate("2025-08-27T00:00")
                .setEndDate("2025-12-31T23:59:59")
                .setPriority(1)
                .build();

        Product product = new Product(
                1001, 1, 2, new BigDecimal("99.99"), "EUR",
                LocalDateTime.parse("2025-08-27T00:00"),
                LocalDateTime.parse("2025-12-31T23:59:59"),
                1
        );

        when(productAvroMapper.avroToProduct(productAvro)).thenReturn(product);

        // Mockeamos que ya existía y no se guarda
        when(productCommandRepository.saveProductIfNotExists(product)).thenReturn(false);

        listener.createProductEvent(productAvro);

        // Verificamos que se haya llamado al repository
        verify(productCommandRepository).saveProductIfNotExists(product);
    }
}