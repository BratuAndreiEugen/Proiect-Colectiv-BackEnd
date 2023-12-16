package org.example.controllers.requestClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FollowRequest {

    private final Long followerId;
    private final Long followeeId;

}
