package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name="contact_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="message")
    private String message;
}
