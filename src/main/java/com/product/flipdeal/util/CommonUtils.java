package com.product.flipdeal.util;

import com.product.flipdeal.model.ProductDetail;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

public class CommonUtils {

    public static double calculateDiscount(int percent, double price) {
        double mul = percent * price;
        return mul / 100;
    }

    public static double convertToINR(double price, String currency, Map<String, BigDecimal> rates) {
        if (!currency.equals("INR")) {
            BigDecimal conversion = rates.get(currency);
            BigDecimal priceBig = new BigDecimal(price);
            BigDecimal convert = priceBig.divide(conversion, MathContext.DECIMAL32);
            return convert.doubleValue();
        }
        return price;
    }

    public static double defaultDiscount(ProductDetail productDetail) {
        double discount3 = 0;
        if (productDetail.getPrice() > 1000) {
            discount3 = calculateDiscount(2, productDetail.getPrice());
        }
        return discount3;
    }
}
