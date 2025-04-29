package com.example.demo.controller;

import com.example.demo.Model.CreditCard;
import com.example.demo.Service.CreditCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/cards")
@CrossOrigin(origins = "http://localhost:4200")

public class CreditCardController {
    private final CreditCardService service;

    public CreditCardController(CreditCardService service) {
        this.service = service;
    }

    @PostMapping("/addcard")
    public CreditCard saveCard(@RequestBody CreditCard card) {
        return service.saveCard(card.getCvv(), card.getEncryptedCardNumber());
    }

    @GetMapping("/getCardNumber")
    public ResponseEntity<String> getCardNumber() {
        return service.getCard();
    }
}
