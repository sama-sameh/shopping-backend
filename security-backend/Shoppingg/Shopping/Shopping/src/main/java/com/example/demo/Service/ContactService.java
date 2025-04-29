package com.example.demo.Service;

import com.example.demo.Model.Contact;
import com.example.demo.Repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository = null;

    public Contact saveMessage(Contact contact) {
        // Optional: Add validation or logging if needed
        return contactRepository.save(contact);
    }
}
