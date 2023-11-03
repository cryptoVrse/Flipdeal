package com.product.flipdeal.response;

import com.product.flipdeal.model.ProductDetail;
import lombok.Data;

import java.util.List;

@Data
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
