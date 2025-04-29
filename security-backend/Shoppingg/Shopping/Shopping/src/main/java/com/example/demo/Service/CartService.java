package com.example.demo.Service;

import com.example.demo.DTO.CartDTO;
import com.example.demo.DTO.CartDetailsDTO;
import com.example.demo.Model.CartItem;
import com.example.demo.Model.Product;
import com.example.demo.Model.UserEntity;
import com.example.demo.Repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    
    private CartRepository cartRepository;
    private UserService userService;
    private ProductService productService;
    @Autowired
    public CartService(CartRepository cartRepository, UserService userService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public ResponseEntity<List<CartItem>> getUserCart() {
        Long userId = getCurrentUserId();
        Optional<UserEntity> user = userService.getUserById(userId);
        if (user.isPresent()) {
            List<CartItem> items = cartRepository.findByUser(user.get());;
            return ResponseEntity.ok(items);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @Transactional
    public ResponseEntity<String> addToCart(CartDTO cartDTO) {
        Long userId = getCurrentUserId();
        Optional<UserEntity> user = userService.getUserById(userId);
        Optional<Product> product = productService.getById(cartDTO.getProduct_id());

        if (user.isPresent() && product.isPresent()) {
            CartItem item = new CartItem();
            item.setUser(user.get());
            item.setProduct(product.get());
            item.setQuantity(cartDTO.getQuantity());
            item.setPrice(cartDTO.getQuantity()*product.get().getPrice());
            cartRepository.save(item);
            return ResponseEntity.ok("Item added to cart");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User or product not found");
    }
    @Transactional
    public void removeFromCart() {
        Long id = getCurrentUserId();
        cartRepository.deleteById(id);
    }
    @Transactional
    public CartItem updateCart(long productId,int newQuantity) {
        Long id = getCurrentUserId();
        CartItem cart = getCartItemByUserAndProduct(id,productId);
        cart.setQuantity(newQuantity);
        Product product = productService.getById(cart.getProduct().getId()).get();
        cart.setPrice(product.getPrice()*newQuantity);
        return cartRepository.save(cart);
    }
    @Transactional
    public boolean deleteProductFromCart(long productId) {
        Long id = getCurrentUserId();
        CartItem cart = getCartItemByUserAndProduct(id,productId);
        if (cart!=null){
            this.cartRepository.delete(cart);
            return true;
        }
        return false;
    }
    @Transactional
    public void emptyCart(Long userId){
        UserEntity user = userService.getUserById(userId).get();
        cartRepository.deleteByUser(user);
    }
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userDetails = (UserEntity) authentication.getPrincipal();
        return userDetails.getId();
    }
    public  ResponseEntity<List<CartDetailsDTO>> getcartDetails(){
        List<CartDetailsDTO> cartDetailsDTO = new ArrayList<>();
        Long userId = getCurrentUserId();
        Optional<UserEntity> user = userService.getUserById(userId);
        if (user.isPresent()) {
            List<CartItem> items = cartRepository.findByUser(user.get());
            for (CartItem item : items) {
                CartDetailsDTO dto = new CartDetailsDTO();
                dto.setProductId(item.getProduct().getId());
                dto.setQuantity(item.getQuantity());
                dto.setPrice(item.getPrice());
                Product product = productService.getById(item.getProduct().getId()).get();
                dto.setProductName(product.getName());
                cartDetailsDTO.add(dto);
            }
            return ResponseEntity.ok(cartDetailsDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    public CartItem getCartItemByUserAndProduct(Long userId, Long productId) {
        return cartRepository.findByUserIdAndProductId(userId, productId).get();

    }
}
