package com.product.flipdeal;

import com.product.flipdeal.config.APIConnection;
import com.product.flipdeal.model.CurrencyExchange;
import com.product.flipdeal.model.ProductDetail;
import com.product.flipdeal.response.ProductDiscounts;
import com.product.flipdeal.service.Converter;
import com.product.flipdeal.service.FlipDealService;
import com.product.flipdeal.strategy.PromotionContext;
import com.product.flipdeal.strategy.PromotionSetAStrategy;
import com.product.flipdeal.strategy.PromotionSetBStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@SpringBootApplication
public class FlipDealApplication {

    public static void main(String[] args) {
        
        System.out.println(args[0]);
        ApplicationContext applicationContext = SpringApplication.run(FlipDealApplication.class, args);
        FlipDealService service = applicationContext.getBean(FlipDealService.class);
        service.fetchProductDetails(args[0]);
    }
}
