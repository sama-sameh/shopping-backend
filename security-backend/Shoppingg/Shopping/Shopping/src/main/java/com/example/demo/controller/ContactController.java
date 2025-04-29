package com.example.demo.controller;

import com.example.demo.Model.Contact;
import com.example.demo.Repository.ContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Contact contact) {
        contactRepository.save(contact);
        return ResponseEntity.ok("Message sent successfully");
    }

    @GetMapping("/all")
    public List<Contact> getAllMessages() {
        return contactRepository.findAll();
    }
}
