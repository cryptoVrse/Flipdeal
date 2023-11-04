package com.product.flipdeal.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.flipdeal.model.CurrencyExchange;
import com.product.flipdeal.model.ProductDetail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    public List<ProductDetail> getAndConvertJSON(URL url) {
        List<ProductDetail> productDetailList = new ArrayList<>();
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            ObjectMapper objectMapper = new ObjectMapper();
            productDetailList = objectMapper.readValue(isr, new TypeReference<List<ProductDetail>>() {
            });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return productDetailList;
    }

    public CurrencyExchange convertCurrencyExchange(URL url) {
        CurrencyExchange currencyExchange = null;
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            ObjectMapper objectMapper = new ObjectMapper();
            currencyExchange = objectMapper.readValue(isr, CurrencyExchange.class);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return currencyExchange;
    }
}
