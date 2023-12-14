package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.FollowRequest;
import org.example.controllers.requestClasses.FollowRequestWithUserName;
import org.example.controllers.requestClasses.UserDTO;
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
            return ResponseEntity.ok().body("unfollow");
        } else {
            follow = new Follow(followRequest.getFollowerId(), followRequest.getFolloweeId());
            followService.startFollowing(follow);
            return ResponseEntity.ok().body("follow");
        }
    }

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

    @GetMapping("/getFollowingWithUsername")
    private ResponseEntity<List<Follow>> getFollowingForUser(@RequestParam String username) {
        User user = userService.getUserByUserName(username);
        return ResponseEntity.ok().body(followService.getAllUsersFollowing(user.getId()));
    }

    @GetMapping("/getFollowing")
    private ResponseEntity<List<Follow>> getFollowingForUser(@RequestParam Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(followService.getAllUsersFollowing(user.getId()));
    }

    @GetMapping("/getFollowingUsers")
    private ResponseEntity<List<UserDTO>> getFollowingUsers(@RequestParam Long id){
        return ResponseEntity.ok().body(userService.getFollowingById(id));
    }

    @GetMapping("/getFollowingUsersWithUsername")
    private ResponseEntity<List<UserDTO>> getFollowingUsers(@RequestParam String username){
        return ResponseEntity.ok().body(userService.getFollowingByUsername(username));
    }

    @GetMapping("/getFollowersUsers")
    private ResponseEntity<List<UserDTO>> getFollowersUsers(@RequestParam Long id){
        return ResponseEntity.ok().body(userService.getFollowersById(id));
    }

    @GetMapping("/getFollowersUsersWithUsername")
    private ResponseEntity<List<UserDTO>> getFollowersUsers(@RequestParam String username){
        return ResponseEntity.ok().body(userService.getFollowersByUsername(username));
    }
}
