package org.example.controllers.requestClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class RecipeRequest {
    private String title;
    private String caption;
    private Float averageRating;
    private String thumbnailLink;
    private String videoLink;
    //!!THIS STRING SHOULD HAVE THIS FORMAT: yyyy-MM-dd
    private String uploadDate;
    private Long posterId;
    private BigDecimal healthAverageRating;
    private BigDecimal nutritionAverageRating;
    private BigDecimal tasteAverageRating;
}
