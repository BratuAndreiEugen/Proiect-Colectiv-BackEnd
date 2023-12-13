package org.example.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ratings")
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private Long recipeId;
    private Long raterId;
    private Long healthGrade;
    private Long nutritionGrade;
    private Long tasteGrade;

    public Rating() {

    }

    public Rating(Long recipeId, Long raterId, Long healthRating, Long nutritionRating, Long tasteRating) {
        this.recipeId = recipeId;
        this.raterId = raterId;
        this.healthGrade = healthRating;
        this.nutritionGrade = nutritionRating;
        this.tasteGrade = tasteRating;
    }
}
