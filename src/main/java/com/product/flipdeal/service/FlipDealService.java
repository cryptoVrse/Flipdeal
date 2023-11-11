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
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
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
    @Autowired
    public Converter converter;

    public void fetchProductDetails(String promotionSet) {
        try {
            URL productUrl = new URI("https://mock.coverself.net/rest/hiring/products").toURL();
            URL exchangeUrl = new URI("https://mock.coverself.net/rest/hiring/exchange-rates").toURL();
//            RestTemplate restTemplate = new RestTemplate();
//            String fooResourceUrl
//                    = "https://mock.coverself.net/rest/hiring/products";
//            ResponseEntity<List<ProductDetail>> response
//                    = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDetail>>() {
//            });
//            System.out.println(response);
            boolean isProductDetailConnected = apiConnection.isConnectionCreated(productUrl);
            boolean isExchangeConnected = apiConnection.isConnectionCreated(exchangeUrl);
            if (isProductDetailConnected && isExchangeConnected) {
                List<ProductDetail> productDetail = converter.getProductDetailsJSON(productUrl);
                CurrencyExchange currencyExchange = converter.getCurrencyExchange(exchangeUrl);
                PromotionStrategy promotionStrategy = promotionFactory.getPromotion(promotionSet);
                if (promotionStrategy != null) {
                    promotionContext.setPromotionStrategy(promotionStrategy);
                    List<ProductDiscounts> productDiscounts = promotionContext.execute(productDetail, currencyExchange);
                    apiConnection.writeOutput(productDiscounts);
                    log.info("Product Details with discount {}", productDiscounts);
                }
            } else {
                log.error("API Urls not able to connect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
