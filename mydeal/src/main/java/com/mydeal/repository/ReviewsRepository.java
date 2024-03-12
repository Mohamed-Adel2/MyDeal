package com.mydeal.repository;

import com.mydeal.domain.entities.Reviews;

public class ReviewsRepository extends CrudRepository<Reviews> {
    public ReviewsRepository() {
        setEntityClass(Reviews.class);
    }
}
