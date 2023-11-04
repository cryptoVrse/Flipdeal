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
public class PromotionSetAStrategy implements PromotionStrategy {

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
            productDiscount.setDiscount(discount);
            productDiscounts.add(productDiscount);
        }
        return productDiscounts;
    }

    private Discount calculateAndSetDiscount(ProductDetail productDetail, ProductDiscounts productDiscount) {
        //discount if origin of product is Africa
        double discount1 = calculateOriginDiscount(productDetail, productDiscount);
        //rating discount
        double discount2 = calculateRatingDiscount(productDetail, productDiscount);
        //flat discount of 100 on electronics and furnishing
        double discount3 = calculateFlatDiscount(productDetail);
        //flat discount if price exceed 1000
        double discount4 = defaultDiscount(productDetail);

        Discount discount = new Discount();
        double maxDiscount = Math.max(discount4, Math.max(discount3, Math.max(discount1, discount2)));
        discount.setAmount(maxDiscount);
        discount.setDiscountTag("get Rs " + maxDiscount + " Off");
        return discount;
    }

    private double calculateOriginDiscount(ProductDetail productDetail, ProductDiscounts productDiscount) {
        double discount = 0;
        if (productDetail.getOrigin().equals("Africa")) {
            discount = calculateDiscount(7, productDiscount.getPrice());
        }
        return discount;
    }

    private double calculateRatingDiscount(ProductDetail productDetail, ProductDiscounts productDiscount) {
        double discount = 0;
        if (productDetail.getRating() == 2) {
            discount = calculateDiscount(4, productDiscount.getPrice());
        } else if (productDetail.getRating() < 2) {
            discount = calculateDiscount(8, productDiscount.getPrice());
        }
        return discount;
    }

    private double calculateFlatDiscount(ProductDetail productDetail) {
        double discount = 0;
        if ((productDetail.getCategory().equals("electronics")
                || productDetail.getCategory().equals("furnishing"))
                && productDetail.getPrice() >= 500) {
            discount = 100;
        }
        return discount;
    }
}
