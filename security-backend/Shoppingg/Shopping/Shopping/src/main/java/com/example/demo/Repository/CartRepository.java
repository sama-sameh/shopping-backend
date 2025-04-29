package com.example.demo.Repository;

import com.example.demo.Model.CartItem;
import com.example.demo.Model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(UserEntity user);
    void deleteByUser(UserEntity user);
    @Query("SELECT ci FROM CartItem ci WHERE ci.user.id = :userId AND ci.product.id = :productId")
    Optional<CartItem> findByUserIdAndProductId(
            @Param("userId") Long userId,
            @Param("productId") Long productId);

}