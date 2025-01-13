package com.example.backend.service;

import com.example.backend.dto.ProductDto;
import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long productId);

    ProductDto updateProduct(Long productId, ProductDto productDto);

    void deleteProduct(Long productId);
}
