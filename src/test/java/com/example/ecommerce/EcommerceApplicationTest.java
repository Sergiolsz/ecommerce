package com.example.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EcommerceApplicationTest {

    @Test
    void contextLoads() {
        // Este test verifica que Spring Boot pueda levantar el contexto de la aplicación.
        // No hace aserciones porque si el contexto no se carga, el test falla automáticamente.
    }
}