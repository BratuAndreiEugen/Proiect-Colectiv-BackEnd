package org.example.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

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

    public Recipe() {

    }
}
