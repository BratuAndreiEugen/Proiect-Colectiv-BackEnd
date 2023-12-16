package org.example.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "follows")
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private Long folowerId; // userul logat in aplicatie
    private Long foloweeId; // userul pe care il urmareste

    public Follow(Long folowerId, Long foloweeId) {
        this.folowerId = folowerId;
        this.foloweeId = foloweeId;
    }
}
