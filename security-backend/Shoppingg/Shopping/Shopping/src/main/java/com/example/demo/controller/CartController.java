package com.example.demo.controller;

import com.example.demo.DTO.CartDTO;
import com.example.demo.DTO.CartDetailsDTO;
import com.example.demo.Model.CartItem;
import com.example.demo.Model.Product;
import com.example.demo.Model.UserEntity;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.UserRepository;


import com.example.demo.Service.CartService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/user/cart")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

    private CartService cartService;
    private ProductService productService;
    private UserService userService;
    @Autowired
    CartController(CartService cartService, ProductService productService, UserService userService) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartDTO) {
       return cartService.addToCart(cartDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<CartItem>> getCartItems() {
        return cartService.getUserCart();
    }
    @GetMapping("/cartDetails")
    public ResponseEntity<List<CartDetailsDTO>> getCartDetails() {
        return cartService.getcartDetails();
    }
    @PostMapping("/updateCart")
    public  Boolean updateCart(@RequestBody CartDTO cartDTO){
        if(this.cartService.updateCart(cartDTO.getProduct_id(),cartDTO.getQuantity())!=null){
            return true;
        }
        return false;
    }
    @GetMapping("/deleteItem/{product_id}")
    public  Boolean deleteProduct(@PathVariable Long product_id){
        if(this.cartService.deleteProductFromCart(product_id)){
            return true;
        }
        return false;
    }
}
