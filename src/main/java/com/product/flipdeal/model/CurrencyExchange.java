package com.product.flipdeal.model;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyExchange {
    String base;
    String date;
    Map<String, Double> rates;
    boolean success;
    long timestamp;
}
