package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="order_items")
@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="quantity")
    int quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    Order order;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    Product product;
    @Column(name="price")
    Double price;
}
