package com.product.flipdeal;

import com.product.flipdeal.config.APIConnection;
import com.product.flipdeal.model.CurrencyExchange;
import com.product.flipdeal.model.ProductDetail;
import com.product.flipdeal.response.ProductDiscounts;
import com.product.flipdeal.service.Converter;
import com.product.flipdeal.strategy.PromotionContext;
import com.product.flipdeal.strategy.PromotionSetA;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

@SpringBootApplication
public class FlipdealApplication {

    public static void main(String[] args) throws MalformedURLException {

        System.out.println("run main program");
        //SpringApplication.run(FlipdealApplication.class, args);
        System.out.println(args.length);
        System.out.println(args[0]);

        String promotionSet = args[0];
        PromotionContext promotionContext = new PromotionContext();
        if (promotionSet.equals("promotionSetA")) {
            APIConnection apiConnection = new APIConnection();
            URL productUrl = new URL("https://mock.coverself.net/rest/hiring/products");
            URL exchangeUrl = new URL("https://mock.coverself.net/rest/hiring/exchange-rates");
            boolean isProductConnected = apiConnection.isConnectionCreated(productUrl);
            boolean isExchangeConnected = apiConnection.isConnectionCreated(exchangeUrl);
            promotionContext.setPromotionStrategy(new PromotionSetA());
            if (isProductConnected && isExchangeConnected) {
                Converter converter = new Converter();
                List<ProductDetail> productDetail = converter.getAndConvertJSON(productUrl);
                CurrencyExchange currencyExchange = converter.convertCurrencyExchange(exchangeUrl);
                List<ProductDiscounts> productDiscounts = promotionContext.execute(productDetail,currencyExchange);
            }
        }
    }
}
