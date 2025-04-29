package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table (name="cart_items")
@Entity
@Getter
@Setter
public class CartItem{
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    @Column(name="quantity")
    private int quantity;
    @Column(name="price")
    private Double price;
}

