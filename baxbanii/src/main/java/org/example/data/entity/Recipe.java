package org.example.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "follows")
@AllArgsConstructor
public class Recipe {
    @Id
    private Long id;
    private String title;
    private String caption;
    private Float averageRating;
    private String thumbnailLink;
    private String videoLink;
    private String uploadDate;
    private Long posterId;

    public Recipe() {

    }
}
