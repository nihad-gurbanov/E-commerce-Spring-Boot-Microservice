package com.holbie.microservices.productservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequestDto {
    private String id;
    private String skuCode;
    private String name;
    private String description;
    private BigDecimal price;
}
