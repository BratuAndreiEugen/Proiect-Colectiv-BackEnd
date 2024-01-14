package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.FollowRequest;
import org.example.controllers.requestClasses.FollowRequestWithUserName;
import org.example.controllers.responseClasses.UserResponse;
import org.example.data.entity.Follow;
import org.example.data.entity.User;
import org.example.service.FollowService;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/follow")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    /**
     * Start or stop following a user based on follower and followee IDs.
     *
     * @param followRequest FollowRequest object containing follower and followee IDs.
     * @return ResponseEntity indicating whether the user is now following or unfollowing the target user.
     * Example Request: {"followerId": 1, "followeeId": 2}
     * Example Response: "follow" or "unfollow"
     */
    @PostMapping("/follow")
    private ResponseEntity<String> startFollowingOrUnfollowing(@RequestBody FollowRequest followRequest) {
        Follow follow = followService.getFollow(followRequest.getFollowerId(), followRequest.getFolloweeId());
        if (follow != null) {
            followService.unfollowUser(follow);
            return ResponseEntity.ok().body("unfollow");
        } else {
            follow = new Follow(followRequest.getFollowerId(), followRequest.getFolloweeId());
            followService.startFollowing(follow);
            return ResponseEntity.ok().body("follow");
        }
    }

    /**
     * Start or stop following a user based on follower's username and followee ID.
     *
     * @param followRequest FollowRequestWithUserName object containing follower's username and followee ID.
     * @return ResponseEntity indicating whether the user is now following or unfollowing the target user.
     * Example Request: {"userName": "john_doe", "followeeId": 2}
     * Example Response: "follow" or "unfollow"
     */
    @PostMapping("/followWithUsername")
    private ResponseEntity<String> startFollowingOrUnfollowing(@RequestBody FollowRequestWithUserName followRequest) {
        User user = userService.getUserByUserName(followRequest.getUserName());
        Follow follow = followService.getFollow(user.getId(), followRequest.getFolloweeId());
        if (follow != null) {
            followService.unfollowUser(follow);
            return ResponseEntity.ok().body("unfollow");
        } else {
            follow = new Follow(user.getId(), followRequest.getFolloweeId());
            followService.startFollowing(follow);
            return ResponseEntity.ok().body("follow");
        }
    }

    /**
     * Get a list of followers for a user based on username.
     *
     * @param username Username of the target user.
     * @return ResponseEntity containing a list of followers (Follow objects).
     * Example Request: "/getFollowersWithUsername?username=john_doe"
     * Example Response: [{"followerId": 1, "followeeId": 2}, {"followerId": 3, "followeeId": 2}, ...]
     */
    @GetMapping("/getFollowersWithUsername")
    private ResponseEntity<List<Follow>> getFollowersForUser(@RequestParam String username) {
        User user = userService.getUserByUserName(username);
        return ResponseEntity.ok().body(followService.getAllUsersFollowers(user.getId()));
    }

    /**
     * Get a list of followers for a user based on user ID.
     *
     * @param id User ID of the target user.
     * @return ResponseEntity containing a list of followers (Follow objects).
     * Example Request: "/getFollowers?id=1"
     * Example Response: [{"followerId": 1, "followeeId": 2}, {"followerId": 3, "followeeId": 2}, ...]
     */
    @GetMapping("/getFollowers")
    private ResponseEntity<List<Follow>> getFollowersForUser(@RequestParam Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(followService.getAllUsersFollowers(user.getId()));
    }

    /**
     * Get a list of users being followed by a user based on username.
     *
     * @param username Username of the target user.
     * @return ResponseEntity containing a list of followed users (Follow objects).
     * Example Request: "/getFollowingWithUsername?username=john_doe"
     * Example Response: [{"followerId": 1, "followeeId": 2}, {"followerId": 1, "followeeId": 3}, ...]
     */
    @GetMapping("/getFollowingWithUsername")
    private ResponseEntity<List<Follow>> getFollowingForUser(@RequestParam String username) {
        User user = userService.getUserByUserName(username);
        return ResponseEntity.ok().body(followService.getAllUsersFollowing(user.getId()));
    }

    /**
     * Get a list of users being followed by a user based on user ID.
     *
     * @param id User ID of the target user.
     * @return ResponseEntity containing a list of followed users (Follow objects).
     * Example Request: "/getFollowing?id=1"
     * Example Response: [{"followerId": 1, "followeeId": 2}, {"followerId": 1, "followeeId": 3}, ...]
     */
    @GetMapping("/getFollowing")
    private ResponseEntity<List<Follow>> getFollowingForUser(@RequestParam Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(followService.getAllUsersFollowing(user.getId()));
    }

    /**
     * Get a list of UserDTO objects representing users being followed by a user based on user ID.
     *
     * @param id User ID of the target user.
     * @return ResponseEntity containing a list of UserDTO objects representing followed users.
     * Example Request: "/getFollowingUsers?id=1"
     * Example Response: [{"id": 2, "username": "alice"}, {"id": 3, "username": "bob"}, ...]
     */
    @GetMapping("/getFollowingUsers")
    private ResponseEntity<List<UserResponse>> getFollowingUsers(@RequestParam Long id){
        return ResponseEntity.ok().body(userService.getFollowingById(id));
    }

    /**
     * Get a list of UserDTO objects representing users being followed by a user based on username.
     *
     * @param username Username of the target user.
     * @return ResponseEntity containing a list of UserDTO objects representing followed users.
     * Example Request: "/getFollowingUsersWithUsername?username=john_doe"
     * Example Response: [{"id": 2, "username": "alice"}, {"id": 3, "username": "bob"}, ...]
     */
    @GetMapping("/getFollowingUsersWithUsername")
    private ResponseEntity<List<UserResponse>> getFollowingUsers(@RequestParam String username){
        return ResponseEntity.ok().body(userService.getFollowingByUsername(username));
    }

    /**
     * Get a list of UserDTO objects representing users who are followers of a user based on user ID.
     *
     * @param id User ID of the target user.
     * @return ResponseEntity containing a list of UserDTO objects representing followers.
     * Example Request: "/getFollowersUsers?id=1"
     * Example Response: [{"id": 4, "username": "carol"}, {"id": 5, "username": "david"}, ...]
     */
    @GetMapping("/getFollowersUsers")
    private ResponseEntity<List<UserResponse>> getFollowersUsers(@RequestParam Long id){
        return ResponseEntity.ok().body(userService.getFollowersById(id));
    }

    /**
     * Get a list of UserDTO objects representing users who are followers of a user based on username.
     *
     * @param username Username of the target user.
     * @return ResponseEntity containing a list of UserDTO objects representing followers.
     * Example Request: "/getFollowersUsersWithUsername?username=john_doe"
     * Example Response: [{"id": 4, "username": "carol"}, {"id": 5, "username": "david"}, ...]
     */
    @GetMapping("/getFollowersUsersWithUsername")
    private ResponseEntity<List<UserResponse>> getFollowersUsers(@RequestParam String username){
        return ResponseEntity.ok().body(userService.getFollowersByUsername(username));
    }

}
