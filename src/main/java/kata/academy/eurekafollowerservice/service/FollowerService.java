package kata.academy.eurekafollowerservice.service;

import kata.academy.eurekafollowerservice.model.entity.Follower;

import java.util.Optional;

public interface FollowerService {

    Boolean existsByUserIdAndFollowerId (Long userID, Long followerID);
    void addFollower (Follower follower);
    void deleteFollower (Follower follower);

    Optional<Follower> findByUserIdAndFollowerId(Long userId, Long followerId);


}
