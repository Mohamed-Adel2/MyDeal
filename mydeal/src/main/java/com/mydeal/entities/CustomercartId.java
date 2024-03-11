package com.mydeal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class CustomercartId implements Serializable {
    private static final long serialVersionUID = 5052567313434029995L;
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomercartId entity = (CustomercartId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.customerId, entity.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, customerId);
    }

}