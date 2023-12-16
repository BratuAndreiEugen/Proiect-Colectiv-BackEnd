package org.example.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "recipes")
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
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
    public Recipe() {

    }

    public Recipe(String title, String caption, Float averageRating, String thumbnailLink, String videoLink, String uploadDate, Long posterId) {
        this.title = title;
        this.caption = caption;
        this.averageRating = averageRating;
        this.thumbnailLink = thumbnailLink;
        this.videoLink = videoLink;
        this.uploadDate = uploadDate;
        this.posterId = posterId;
        this.healthAverageRating = new BigDecimal(0);
        this.nutritionAverageRating = new BigDecimal(0);
        this.tasteAverageRating = new BigDecimal(0);
    }
}
