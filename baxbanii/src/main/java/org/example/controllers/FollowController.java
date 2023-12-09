package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.FollowRequest;
import org.example.controllers.requestClasses.FollowRequestWithUserName;
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

    @PostMapping("/follow")
    private ResponseEntity<String> startFollowingOrUnfollowing(@RequestBody FollowRequest followRequest) {
        Follow follow = followService.getFollow(followRequest.getFollowerId(), followRequest.getFolloweeId());
        if (follow != null) {
            followService.unfollowUser(follow);
            return ResponseEntity.ok().body("ok");
        } else {
            follow = new Follow(followRequest.getFollowerId(), followRequest.getFolloweeId());
            followService.startFollowing(follow);
            return ResponseEntity.ok().body("ok");
        }
    }

    @PostMapping("/followWithUsername")
    private ResponseEntity<String> startFollowingOrUnfollowing(@RequestBody FollowRequestWithUserName followRequest) {
        User user = userService.getUserByUserName(followRequest.getUserName());
        Follow follow = followService.getFollow(user.getId(), followRequest.getFolloweeId());
        if (follow != null) {
            followService.unfollowUser(follow);
            return ResponseEntity.ok().body("ok");
        } else {
            follow = new Follow(user.getId(), followRequest.getFolloweeId());
            followService.startFollowing(follow);
            return ResponseEntity.ok().body("ok");
        }
    }

    @GetMapping("/getFollowersWithUsername")
    private ResponseEntity<List<Follow>> getFollowersForUser(@RequestParam String username) {
        User user = userService.getUserByUserName(username);
        return ResponseEntity.ok().body(followService.gatAllUsersFollowers(user.getId()));
    }

    @GetMapping("/getFollowers")
    private ResponseEntity<List<Follow>> getFollowersForUser(@RequestParam Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(followService.gatAllUsersFollowers(user.getId()));
    }

}
