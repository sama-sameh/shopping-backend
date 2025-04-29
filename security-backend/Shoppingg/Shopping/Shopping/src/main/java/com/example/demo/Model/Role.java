package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

//@Entity
//@Table(name="roles")
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Builder
//public class Role {
//    @Id
//    @Column(name="id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @Column(name= "name",nullable = false)
//    private String name;
//}
public enum Role{
    ADMIN,
    USER
}
