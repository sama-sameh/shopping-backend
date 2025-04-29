package com.example.demo.controller;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userhome")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private ProductService productService;
    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> viewHomePage(Model model) {
        List<Product> products = productService.getAll();
        return products;
    }
    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable long id) {
        return  productService.getById(id).get();
    }

}
