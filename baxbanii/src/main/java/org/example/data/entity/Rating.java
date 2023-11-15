package org.example.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ratings")
@AllArgsConstructor
public class Rating {
    @Id
    private Long id;
    private Long grade;
    private Long recipeId;
    private Long raterId;

    public Rating() {

    }
}
