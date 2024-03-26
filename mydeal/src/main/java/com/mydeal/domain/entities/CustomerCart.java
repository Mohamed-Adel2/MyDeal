package com.mydeal.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "customercart")
public class CustomerCart {
    @EmbeddedId
    private CustomerCartId id;

    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    public String toString() {
        return "CustomerCart(id=" + this.getId().getCustomerId() + " " + this.getId().getProductId() + ", customer=" + this.getCustomer() + ", product=" + this.getProduct() + ", quantity=" + this.getQuantity() + ")";
    }
}