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
    private Long folowerId; //asta e userul
    private Long foloweeId; // cei pe care ii urmareste userul

    public Follow(Long folowerId, Long foloweeId) {
        this.folowerId = folowerId;
        this.foloweeId = foloweeId;
    }
}
