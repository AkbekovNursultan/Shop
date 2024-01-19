package com.example.test.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer balance;

    @OneToOne(mappedBy = "customer")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products ;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Purchase> purchases;
}