package com.product.flipdeal.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencyExchange {
    String base;
    String date;
    Map<String, BigDecimal> rates;
    boolean success;
    long timestamp;
}
