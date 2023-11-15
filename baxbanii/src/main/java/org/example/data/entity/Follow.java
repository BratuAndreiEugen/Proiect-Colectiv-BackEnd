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
public class Follow {
    @Id
    private Long id;
    private Long folowerId;
    private Long foloweeId;

    public Follow() {

    }
}
