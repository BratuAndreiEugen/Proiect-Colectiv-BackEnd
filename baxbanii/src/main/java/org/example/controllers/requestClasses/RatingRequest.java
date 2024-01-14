package org.example.controllers.requestClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RatingRequest {
    private final Long userId;
    private final Long healthy;
    private final Long nutritive;
    private final Long tasty;
}
