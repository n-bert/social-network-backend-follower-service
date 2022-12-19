package kata.academy.eurekafollowerservice.feign.fallback;

import kata.academy.eurekafollowerservice.exception.FeignRequestException;
import kata.academy.eurekafollowerservice.feign.NotificationServiceFeignClient;

record NotificationServiceFallback(Throwable cause) implements NotificationServiceFeignClient {
    @Override
    public void addNotification(String text, Long recipientId) {
        throw new FeignRequestException("Сервис временно недоступен. Причина -> %s".formatted(cause.getMessage()), cause);
    }
}
