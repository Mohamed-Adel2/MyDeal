package com.mydeal.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor

@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Customercart> customercarts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Orderdetail> orderdetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Productimage> productimages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Review> reviews = new LinkedHashSet<>();
}