package com.product.flipdeal.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDiscounts {

    String category;
    int inventory;
    String arrival;
    double rating;
    String currency;
    double price;
    String origin;
    String product;
    Discount discount;
}
