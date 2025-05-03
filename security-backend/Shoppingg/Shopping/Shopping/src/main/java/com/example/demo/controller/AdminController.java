package com.example.demo.controller;

import com.example.demo.DTO.AdminDashBoard;
import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import com.example.demo.Model.UserEntity;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AdminDashBoardService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private UserService userService;
    private ProductService productService;
    private AdminDashBoardService adminDashBoardService;
    private OrderService orderService;
    @Autowired
    AdminController(UserService userService, ProductService productService, AdminDashBoardService adminDashBoardService, OrderService orderService){
        this.userService=userService;
        this.productService=productService;
        this.adminDashBoardService=adminDashBoardService;
        this.orderService=orderService;

    }

    @GetMapping("/users")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ok("User deleted successfully.");
    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        return userService.getUserById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            return ok(userService.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ok("Product deleted successfully.");
    }

    @PutMapping("/products/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.getById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setImageUrl(updatedProduct.getImageUrl());
            return ok(productService.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashBoard> getDashBoard(){
        return ResponseEntity.ok(this.adminDashBoardService.getDashBoard());
    }
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(){
        return ResponseEntity.ok(this.orderService.getAllOrders());
    }
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("productPrice") String price
            ) {
        try {
            // Save the image to a local folder

            String uploadDir = "D:/GitHub/shopping-frontend/secure-shop/secure-shop/src/assets/images/";
            String fileName = image.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Construct the image URL
            String imageUrl = "assets/images/" + fileName;

            // Create and save the product entity (assuming you have a Product entity and repository)
            Product product = new Product();
            product.setName(productName);
            product.setDescription(description);
            product.setPrice(Double.parseDouble(price));
            product.setImageUrl(imageUrl);

            this.productService.save(product);

            return ResponseEntity.ok("Product saved successfully");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload product");
        }
    }

}
