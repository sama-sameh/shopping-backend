package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name="creditcard")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @OneToOne
    @JoinColumn(name="user_id")
    UserEntity user;
   @Column(name="cvv")
    private String cvv;
    @Column(name="encrypted_card_number")
    private String encryptedCardNumber;
}
