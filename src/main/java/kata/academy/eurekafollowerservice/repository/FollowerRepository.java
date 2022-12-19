package kata.academy.eurekafollowerservice.repository;

import kata.academy.eurekafollowerservice.model.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowerRepository extends  JpaRepository <Follower, Long> {
    boolean existsByUserIdAndFollowerId (Long userId, Long followerId);

    Optional<Follower> findByUserIdAndFollowerId(Long userId, Long followerId);
}
