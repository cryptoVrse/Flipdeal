package com.product.flipdeal.strategy;

import com.product.flipdeal.model.CurrencyExchange;
import com.product.flipdeal.model.ProductDetail;
import com.product.flipdeal.response.Discount;
import com.product.flipdeal.response.ProductDiscounts;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.product.flipdeal.util.CommonUtils.*;

@Component
public class PromotionSetBStrategy implements PromotionStrategy {
    @Override
    public List<ProductDiscounts> calculateDiscounts(List<ProductDetail> productDetailList, CurrencyExchange currencyExchange) {
        List<ProductDiscounts> productDiscounts = new ArrayList<>();
        for (ProductDetail productDetail : productDetailList) {
            ProductDiscounts productDiscount = ProductDiscounts.builder()
                    .category(productDetail.getCategory())
                    .inventory(productDetail.getInventory())
                    .arrival(productDetail.getArrival())
                    .rating(productDetail.getRating())
                    .currency("INR")
                    .price(convertToINR(productDetail.getPrice(), productDetail.getCurrency(), currencyExchange.getRates()))
                    .origin(productDetail.getOrigin())
                    .product(productDetail.getProduct())
                    .build();

            Discount discount = calculateAndSetDiscount(productDetail, productDiscount);
            if (discount.getAmount() != 0) {
                productDiscount.setDiscount(discount);
            }
            productDiscounts.add(productDiscount);
        }
        return productDiscounts;
    }

    private Discount calculateAndSetDiscount(ProductDetail productDetail, ProductDiscounts productDiscount) {
        //inventory discount
        double discount1 = calculateInventoryDiscount(productDetail, productDiscount);
        //discount on arrival status
        double discount2 = calcualteDiscountOnArrivalStatus(productDetail, productDiscount);
        //flat discount if price exceed 1000
        double discount3 = defaultDiscount(productDetail);

        Discount discount = new Discount();
        double maxDiscount = Math.max(discount3, Math.max(discount1, discount2));
        discount.setAmount(maxDiscount);
        discount.setDiscountTag("get Rs " + maxDiscount + " Off");
        return discount;
    }

    private double calcualteDiscountOnArrivalStatus(ProductDetail productDetail, ProductDiscounts productDiscount) {
        double discount = 0;
        if (productDetail.getProduct().equals("NEW")) {
            discount = calculateDiscount(7, productDiscount.getPrice());
        }
        return discount;
    }

    private double calculateInventoryDiscount(ProductDetail productDetail, ProductDiscounts productDiscount) {
        double discount = 0;
        if (productDetail.getInventory() > 20) {
            discount = calculateDiscount(12, productDiscount.getPrice());
        }
        return discount;
    }
}
