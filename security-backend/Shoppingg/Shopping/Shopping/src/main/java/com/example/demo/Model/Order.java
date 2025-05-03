package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.DoubleStream;

@Table (name="orders")
@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private UserEntity user;
    @Column(name = "total")
    private Double totalPrice;
    @Column(name = "status")
    private String status;
    @Column(name = "order_date")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> items;

}
