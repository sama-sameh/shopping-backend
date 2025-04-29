package com.example.demo.controller;

import com.example.demo.DTO.CartDTO;
import com.example.demo.Model.*;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UserRepository;

import com.example.demo.Service.CartService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user/orders")
@CrossOrigin(origins = "http://localhost:4200")

public class OrderController {

    private OrderService orderService;
    private UserService userService;
    @Autowired
    OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;

    }

    @GetMapping("/place")
    public Boolean placeOrder() {
        return orderService.saveOrder();
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getOrdersForUser() {
        return this.orderService.getUserOrders();
    }
}

