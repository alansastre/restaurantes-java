package com.restaurantes.service;

import com.restaurantes.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

// Cliente HTTP que trae los datos de una API REST externa de Productos
@Service
public class ProductService {

    private final RestClient restClient;

    public ProductService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://fakestoreapi.com").build();
    }

    public ProductDTO findById(long id) {
        return restClient
                .get()
                .uri("/products/{id}", id)
                .retrieve()
                .body(ProductDTO.class);
    }

    public List<ProductDTO> findAll() {
        return restClient
                .get()
                .uri("/products")
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
