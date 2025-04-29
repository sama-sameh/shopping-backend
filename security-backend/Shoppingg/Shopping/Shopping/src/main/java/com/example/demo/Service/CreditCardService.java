package com.example.demo.Service;

import com.example.demo.Model.CreditCard;
import com.example.demo.Model.UserEntity;
import com.example.demo.Repository.CreditCardRepository;
import com.example.demo.Security.AESUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {
    private final CreditCardRepository repository;
    private final UserService userService;
    public CreditCardService(CreditCardRepository repository,UserService userService) {

        this.repository = repository;
        this.userService = userService;
    }

    public CreditCard saveCard(String cvv, String cardNumber) {
//        String CardNumberencrypted = AESUtil.encrypt(cardNumber);
//        String cvvencypted = AESUtil.encrypt(cvv);
        CreditCard card = CreditCard.builder()
                .cvv(cvv)
                .encryptedCardNumber(cardNumber)
                .build();
        UserEntity user = userService.getUserById(getCurrentUserId()).get();
        card.setUser(user);
        return repository.save(card);
    }

    public ResponseEntity<String> getCard() {
        String card = repository.findByUser(getCurrentUserId());
        if(card != null) {
            return  ResponseEntity.ok(card);

        }
        return  ResponseEntity.ok(null);

    }
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userDetails = (UserEntity) authentication.getPrincipal();
        return userDetails.getId();
    }

}
