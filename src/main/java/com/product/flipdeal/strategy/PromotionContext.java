package com.product.flipdeal.strategy;

import com.product.flipdeal.model.CurrencyExchange;
import com.product.flipdeal.model.ProductDetail;
import com.product.flipdeal.response.ProductDiscounts;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromotionContext {

    private PromotionStrategy promotionStrategy;

    public void setPromotionStrategy(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public List<ProductDiscounts> execute(List<ProductDetail> productDetailList, CurrencyExchange currencyExchange) {
        return promotionStrategy.calculateDiscounts(productDetailList,currencyExchange);
    }
}
