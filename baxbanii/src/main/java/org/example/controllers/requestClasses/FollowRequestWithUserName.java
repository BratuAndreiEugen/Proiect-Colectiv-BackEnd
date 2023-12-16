package org.example.controllers.requestClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FollowRequestWithUserName {
    private final String userName;
    private final Long followeeId;
}
