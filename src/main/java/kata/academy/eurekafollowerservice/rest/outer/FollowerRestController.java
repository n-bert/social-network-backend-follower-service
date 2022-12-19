package kata.academy.eurekafollowerservice.rest.outer;


import kata.academy.eurekafollowerservice.feign.AuthServiceFeignClient;
import kata.academy.eurekafollowerservice.model.entity.Follower;
import kata.academy.eurekafollowerservice.service.FollowerService;
import kata.academy.eurekafollowerservice.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Positive;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/followers")
public class FollowerRestController {

    private final AuthServiceFeignClient authServiceFeignClient;
    private final FollowerService followerService;





    @PostMapping("/{userId}")
    public ResponseEntity<Void> follow(
            @PathVariable @Positive Long userId,
            @RequestHeader("userId") @Positive Long followerId) {
        ApiValidationUtil.requireTrue( authServiceFeignClient.existsById(userId),
                    String.format("Юзер с userId %d не существует", userId));
        ApiValidationUtil.requireFalse( followerService.existsByUserIdAndFollowerId(userId, followerId),
              String.format("Вы уже подписаны на пользователя %d", userId));
        followerService.addFollower(Follower.builder()
                 .userId(userId)
                .followerId(followerId)
                .build());
        return ResponseEntity.ok().build();

    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> unfollow(
            @PathVariable @Positive Long userId,
            @RequestHeader("userId") @Positive Long followerId) {
        Optional <Follower> follower = followerService.findByUserIdAndFollowerId(userId, followerId);
        ApiValidationUtil.requireTrue( follower.isPresent(), String.format("Вы не подписаны на пользователя с userId %d", userId));
        followerService.deleteFollower(follower.get());
        return ResponseEntity.ok().build();

    }


    @DeleteMapping ()
    public ResponseEntity<Void> deleteFollower(
            @RequestParam @Positive Long followerId,
            @RequestHeader @Positive Long userId) {

        Optional <Follower> follower = followerService.findByUserIdAndFollowerId(followerId, userId);
        ApiValidationUtil.requireTrue( follower.isPresent(), String.format("Пользователь с userId %d не подписан на вас", followerId));
        followerService.deleteFollower(follower.get());
        return ResponseEntity.ok().build();

    }




}
