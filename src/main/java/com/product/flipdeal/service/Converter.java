package com.product.flipdeal.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.flipdeal.model.CurrencyExchange;
import com.product.flipdeal.model.ProductDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Converter {
    public List<ProductDetail> getProductDetailsJSON(URL url) {
        List<ProductDetail> productDetailList = new ArrayList<>();
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            ObjectMapper objectMapper = new ObjectMapper();
            productDetailList = objectMapper.readValue(isr, new TypeReference<>() {
            });
        } catch (IOException exception) {
            log.error("error in converting to ProductDetail Json object");
            exception.printStackTrace();
        }
        return productDetailList;
    }

    public CurrencyExchange getCurrencyExchange(URL url) {
        CurrencyExchange currencyExchange = null;
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            ObjectMapper objectMapper = new ObjectMapper();
            currencyExchange = objectMapper.readValue(isr, CurrencyExchange.class);
        } catch (IOException exception) {
            log.error("error in converting to CurrencyExchange Json object");
            exception.printStackTrace();
        }
        return currencyExchange;
    }
}
