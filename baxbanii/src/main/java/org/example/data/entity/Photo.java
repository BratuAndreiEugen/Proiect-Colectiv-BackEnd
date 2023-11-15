package org.example.data.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "photos")
@AllArgsConstructor
public class Photo {
    @Id
    private String link;
    private Long recipeId;

    public Photo() {

    }
}
