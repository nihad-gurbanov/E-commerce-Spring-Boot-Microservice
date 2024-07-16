package com.holbie.microservices.productservice.service;

import com.holbie.microservices.productservice.dto.ProductRequestDto;
import com.holbie.microservices.productservice.dto.ProductResponseDto;
import com.holbie.microservices.productservice.model.Product;
import com.holbie.microservices.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = modelMapper.map(productRequestDto, Product.class);

        productRepository.save(product);
        log.info("Product created: {}", product);
        return modelMapper.map(product, ProductResponseDto.class);
    }


    public List<ProductResponseDto> getAll() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .toList();
    }
}
