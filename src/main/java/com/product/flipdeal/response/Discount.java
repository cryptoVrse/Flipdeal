package com.product.flipdeal.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Discount {
    double amount;
    String discountTag;
}
