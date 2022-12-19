package kata.academy.eurekafollowerservice.feign;


import kata.academy.eurekafollowerservice.feign.fallback.AuthServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.validation.constraints.Positive;


@FeignClient(value = "eureka-auth-service", fallbackFactory = AuthServiceFallbackFactory.class)
public interface AuthServiceFeignClient {

    @GetMapping("/api/internal/v1/auth/{userId}/exists")
    Boolean existsById(@PathVariable @Positive Long userId);


}
