package com.example.demo.DTO;

import lombok.Data;

@Data
public class CartDetailsDTO {
    private long productId;
    private String productName;
    private int quantity;
    private Double price;
}
