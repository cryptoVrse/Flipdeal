package com.product.flipdeal.strategy;

import com.product.flipdeal.model.CurrencyExchange;
import com.product.flipdeal.model.ProductDetail;
import com.product.flipdeal.response.ProductDiscounts;

import java.util.List;

public interface PromotionStrategy {

    List<ProductDiscounts> calculateDiscounts(List<ProductDetail> productDetailList, CurrencyExchange currencyExchange);
}
