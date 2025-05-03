package com.example.demo.Service;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }
    @Transactional
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
    public long getProductCount() {
        return productRepository.count();
    }

}

