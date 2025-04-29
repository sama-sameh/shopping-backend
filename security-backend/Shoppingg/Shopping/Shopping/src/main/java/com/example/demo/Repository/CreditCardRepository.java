package com.example.demo.Repository;

import com.example.demo.Model.CreditCard;
import com.example.demo.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    @Query("SELECT ci.encryptedCardNumber FROM CreditCard ci WHERE ci.user.id = :userId")
    String findByUser(
            @Param("userId") Long userId
    );
}