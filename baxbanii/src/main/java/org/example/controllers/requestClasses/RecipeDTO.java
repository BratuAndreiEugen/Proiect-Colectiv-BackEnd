package org.example.controllers.requestClasses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecipeDTO {
    private Long id;
    private String title;
    private String caption;
    private Float averageRating;
    private String thumbnailLink;
    private String videoLink;
    //!!THIS STRING SHOULD HAVE THIS FORMAT: yyyy-MM-dd
    private String uploadDate;
    private Long posterId;
    private String posterUsername;
    private BigDecimal healthAverageRating;
    private BigDecimal nutritionAverageRating;
    private BigDecimal tasteAverageRating;
}
