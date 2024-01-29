package com.kerekegyensuly.project.dto.product;

import javax.validation.constraints.NotNull;

public class ProductDto {
    private Integer id;
    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull double price;
    private @NotNull String descpription;
    private @NotNull null Integer categoryId;

    public ProductDto(Product product) {
        this.setId(product.getId());

    }
}
