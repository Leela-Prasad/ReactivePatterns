package com.reactive.patterns.ReactivePatterns.sec01.service;

import com.reactive.patterns.ReactivePatterns.sec01.client.ProductClient;
import com.reactive.patterns.ReactivePatterns.sec01.client.PromotionClient;
import com.reactive.patterns.ReactivePatterns.sec01.client.ReviewClient;
import com.reactive.patterns.ReactivePatterns.sec01.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.util.List;

@Service
public class ProductAggregatorService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private PromotionClient promotionClient;

    @Autowired
    private ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(int id) {
        return Mono.zip(productClient.getProduct(id),
                promotionClient.getPromotion(id),
                reviewClient.getReview(id))
                .map(t -> toProductAggregateDto(t.getT1(), t.getT2(), t.getT3()));
    }

    private ProductAggregate toProductAggregateDto(ProductResponse product,
                                                   PromotionResponse promotion,
                                                   List<ReviewResponse> reviews) {
        Price price = new Price();
        float amountSaved = (product.getPrice() * promotion.getDiscount())/100;
        float discountPrice = product.getPrice() - amountSaved;


        price.setListPrice(product.getPrice());
        price.setDiscountPrice(discountPrice);
        price.setDiscount(promotion.getDiscount());
        price.setAmountSaved(amountSaved);
        price.setEndDate(promotion.getEndDate());

        return ProductAggregate.create(product.getId(),
                                product.getCategory(),
                                product.getDescription(),
                                price,
                                reviews);
    }
}
