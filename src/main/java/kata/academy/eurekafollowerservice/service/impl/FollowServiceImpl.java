package kata.academy.eurekafollowerservice.service.impl;

import kata.academy.eurekafollowerservice.feign.NotificationServiceFeignClient;
import kata.academy.eurekafollowerservice.model.entity.Follower;
import kata.academy.eurekafollowerservice.repository.FollowerRepository;
import kata.academy.eurekafollowerservice.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Transactional
@Service
public class FollowServiceImpl implements FollowerService {

    private final FollowerRepository followerRepository;


    private final NotificationServiceFeignClient notificationServiceFeignClient;



    @Override
    public Boolean existsByUserIdAndFollowerId (Long userId, Long followerId) {
        return followerRepository.existsByUserIdAndFollowerId (userId, followerId);
    }

    @Override
    public void addFollower(Follower follower) {
         followerRepository.save(follower);
         notificationServiceFeignClient.addNotification(String.format("Юзер с userId %d подписался на вас!", follower.getFollowerId()), follower.getUserId());
    }


    @Override
    public void deleteFollower(Follower follower) {
         followerRepository.delete(follower);
    }


    @Transactional (readOnly = true)
    @Override
    public Optional<Follower> findByUserIdAndFollowerId(Long userId, Long followerId) {
        return followerRepository.findByUserIdAndFollowerId(userId, followerId);
    }
}
