package com.example.demo.Service;

import com.example.demo.DTO.CartDTO;
import com.example.demo.Model.*;
import com.example.demo.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    
    private OrderRepository orderRepository;
    private UserService userService;
    private ProductService productService;
    private OrderItemService orderItemService;
    private CartService cartService;
    @Autowired
    OrderService(OrderRepository orderRepository, UserService userService, ProductService productService, OrderItemService orderItemService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
        this.orderItemService = orderItemService;
        this.cartService = cartService;
    }
    public ResponseEntity<List<Order>> getUserOrders() {
        Long userId = getCurrentUserId();
        Optional<UserEntity> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Order> orders = orderRepository.findByUser(user.get());
        return ResponseEntity.ok(orders);
    }
    @Transactional
    public Boolean saveOrder() {
        Long userId = getCurrentUserId();
        List<CartItem> items = this.cartService.getUserCart().getBody();
        System.out.print(items);
        Optional<UserEntity> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return false;
        }
        Order order = new Order();
        order.setUser(user.get());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");
        Double total = 0.0;
        for (CartItem item : items) {
            Product product = productService.getById(item.getProduct().getId()).get();
            total+=product.getPrice()*item.getQuantity();
        }

        order.setTotalPrice(total);

        order = orderRepository.save(order);
        for (CartItem item : items) {
            OrderItem orderItem = new OrderItem();
            Product product = productService.getById(item.getProduct().getId()).get();
            orderItem.setOrder(order);
            orderItem.setProduct(productService.getById(item.getProduct().getId()).get());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getQuantity()*product.getPrice());
            orderItemService.addOrderItem(orderItem);
        }
        cartService.emptyCart(order.getUser().getId());
        return  true;
    }
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userDetails = (UserEntity) authentication.getPrincipal();
        return userDetails.getId();
    }
}

