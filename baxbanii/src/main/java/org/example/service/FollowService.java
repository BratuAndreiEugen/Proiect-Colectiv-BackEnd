package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.entity.Follow;
import org.example.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "followService")
@AllArgsConstructor
public class FollowService {

    FollowRepository followRepository;

    public void startFollowing(Follow follow) {
        followRepository.saveFollow(follow);
    }

    public void unfollowUser(Follow follow) {
        followRepository.deleteFollows(follow);
    }

    public List<Follow> gatAllUsersFollowers(Long id) {
        return followRepository.getAllUsersFollowers(id);
    }

    public Follow getFollow(Long followerId, Long followeeId) {
        return followRepository.getFollow(followerId, followeeId);
    }
}
