package com.example.demo.Service;

import com.example.demo.DTO.AdminDashBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDashBoardService {
    ProductService productService;
    UserService userService;
    OrderService orderService;
    @Autowired
    AdminDashBoardService(ProductService productService, UserService userService,OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }
    public AdminDashBoard getDashBoard(){
        AdminDashBoard adminDashBoard = new AdminDashBoard();
        adminDashBoard.setNo_of_products(this.productService.getProductCount());
        adminDashBoard.setNo_of_users(this.userService.getUsersCount());
        adminDashBoard.setNo_of_orders(this.orderService.getOrderCount());
        return adminDashBoard;

    }
}
