package com.product.flipdeal.service;

import com.product.flipdeal.config.APIConnection;
import com.product.flipdeal.factory.PromotionFactory;
import com.product.flipdeal.model.CurrencyExchange;
import com.product.flipdeal.model.ProductDetail;
import com.product.flipdeal.response.ProductDiscounts;
import com.product.flipdeal.strategy.PromotionContext;
import com.product.flipdeal.strategy.PromotionStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
@Slf4j
public class FlipDealService {

    @Autowired
    public PromotionContext promotionContext;
    @Autowired
    public APIConnection apiConnection;
    @Autowired
    public PromotionFactory promotionFactory;

    public void fetchProductDetails(String promotionSet) {
        try {
            URL productUrl = new URL("https://mock.coverself.net/rest/hiring/products");
            URL exchangeUrl = new URL("https://mock.coverself.net/rest/hiring/exchange-rates");
            boolean isProductConnected = apiConnection.isConnectionCreated(productUrl);
            boolean isExchangeConnected = apiConnection.isConnectionCreated(exchangeUrl);
            if (isProductConnected && isExchangeConnected) {
                Converter converter = new Converter();
                List<ProductDetail> productDetail = converter.getAndConvertJSON(productUrl);
                CurrencyExchange currencyExchange = converter.convertCurrencyExchange(exchangeUrl);
                PromotionStrategy promotionStrategy = promotionFactory.getPromotion(promotionSet);
                promotionContext.setPromotionStrategy(promotionStrategy);
                List<ProductDiscounts> productDiscounts = promotionContext.execute(productDetail, currencyExchange);
                apiConnection.writeOutput(productDiscounts);
                log.info("Product Details with discount {}", productDiscounts);
            } else {
                log.error("API Urls not able to connect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
