package kata.academy.eurekafollowerservice.feign;

import kata.academy.eurekafollowerservice.feign.fallback.NotificationServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@FeignClient(value = "eureka-notification-service", fallbackFactory = NotificationServiceFallbackFactory.class)
public interface NotificationServiceFeignClient {

    @PostMapping("/api/internal/v1/notifications")
    void addNotification(@RequestBody @NotBlank String text,
                            @RequestParam @Positive Long recipientId);
}
