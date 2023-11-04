package com.product.flipdeal;

import com.product.flipdeal.service.FlipDealService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FlipDealApplication {

    public static void main(String[] args) {
        
        System.out.println(args[0]);
        ApplicationContext applicationContext = SpringApplication.run(FlipDealApplication.class, args);
        FlipDealService service = applicationContext.getBean(FlipDealService.class);
        service.fetchProductDetails(args[0]);
    }
}
