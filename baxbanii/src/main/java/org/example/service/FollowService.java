package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.entity.Follow;
import org.example.repository.FollowRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "followService")
@AllArgsConstructor
public class FollowService {

    private FollowRepository followRepository;
    private UserRepository userRepository;

    /**
     * Start following a user.
     *
     * @param follow Follow entity representing the follower and followee relationship.
     */
    public void startFollowing(Follow follow) {
        followRepository.saveFollow(follow);
    }

    /**
     * Unfollow a user.
     *
     * @param follow Follow entity representing the follower and followee relationship.
     */
    public void unfollowUser(Follow follow) {
        followRepository.deleteFollows(follow);
    }

    /**
     * Get a Follow entity based on the follower and followee IDs.
     *
     * @param followerId User ID representing the follower.
     * @param followeeId User ID representing the followee.
     * @return Follow entity or null if not found.
     */
    public Follow getFollow(Long followerId, Long followeeId) {
        return followRepository.getFollow(followerId, followeeId);
    }

    /**
     * Get a list of Follow entities representing users who follow the given user.
     *
     * @param id User ID for whom followers are retrieved.
     * @return List of Follow entities or an empty list if none found.
     */
    public List<Follow> getAllUsersFollowers(Long id) {
        return followRepository.getAllFollowsReceivedByUser(id);
    }

    /**
     * Get a list of Follow entities representing users whom the given user is following.
     *
     * @param id User ID for whom following users are retrieved.
     * @return List of Follow entities or an empty list if none found.
     */
    public List<Follow> getAllUsersFollowing(Long id){
        return followRepository.getAllFollowsByUser(id);
    }
}

