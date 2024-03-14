package com.mydeal.repository;

import com.mydeal.domain.entities.Reviews;
import jakarta.persistence.EntityManager;

public class ReviewsRepository extends CrudRepository<Reviews> {
    public ReviewsRepository() {

        setEntityClass(Reviews.class);
    }
}
