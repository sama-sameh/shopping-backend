package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table (name="products")
@Entity
@Getter
@Setter
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="price")
    private Double price;
    @Column(name="image_url")
    private String imageUrl;
}

