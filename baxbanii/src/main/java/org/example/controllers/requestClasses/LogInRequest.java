package org.example.controllers.requestClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class LogInRequest {
    private final String email;
    private final String password;
}
