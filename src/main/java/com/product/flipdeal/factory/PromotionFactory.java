package com.product.flipdeal.factory;

import com.product.flipdeal.strategy.PromotionSetAStrategy;
import com.product.flipdeal.strategy.PromotionSetBStrategy;
import com.product.flipdeal.strategy.PromotionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionFactory {

    @Autowired
    public PromotionSetAStrategy promotionSetAStrategy;
    @Autowired
    public PromotionSetBStrategy promotionSetBStrategy;

    public PromotionStrategy getPromotion(String promotion) {
        return switch (promotion) {
            case "promotionSetA" -> promotionSetAStrategy;
            case "promotionSetB" -> promotionSetBStrategy;
            default -> null;
        };
    }
}
