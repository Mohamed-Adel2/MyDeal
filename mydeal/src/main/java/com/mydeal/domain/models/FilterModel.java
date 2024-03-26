package com.mydeal.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilterModel {
    private String category;
    private Double minPrice;
    private Double maxPrice;
    private String searchKey;
    private Integer startIdx;
    private Integer limit;
    private Integer categoryId;
}
