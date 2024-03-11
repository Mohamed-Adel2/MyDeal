package com.mydeal.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "password")
    private String password;

    @Column(name = "credit_limit", precision = 10, scale = 2)
    private BigDecimal creditLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Customercart> customercarts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Review> reviews = new LinkedHashSet<>();
}