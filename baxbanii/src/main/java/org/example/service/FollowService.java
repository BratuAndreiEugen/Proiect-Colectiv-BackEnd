package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.entity.Follow;
import org.example.data.entity.User;
import org.example.repository.FollowRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "followService")
@AllArgsConstructor
public class FollowService {

    FollowRepository followRepository;

    UserRepository userRepository;

    public void startFollowing(Follow follow) {
        followRepository.saveFollow(follow);
    }

    public void unfollowUser(Follow follow) {
        followRepository.deleteFollows(follow);
    }

    public List<User> getAllUsersFollowers(Long followeeId) {
        List<Follow> follows = followRepository.getAllFollowsReceivedByUser(followeeId);
        return follows.stream()
                .map(follow -> userRepository.getUserById(follow.getFolowerId()))
                .collect(Collectors.toList());
    }

    public Follow getFollow(Long followerId, Long followeeId) {
        return followRepository.getFollow(followerId, followeeId);
    }
}
