package kata.academy.eurekafollowerservice.feign.fallback;

import kata.academy.eurekafollowerservice.exception.FeignRequestException;
import kata.academy.eurekafollowerservice.feign.AuthServiceFeignClient;

record AuthServiceFallback(Throwable cause) implements AuthServiceFeignClient {
    @Override
    public Boolean existsById(Long userId) {
         throw new FeignRequestException("Сервис временно недоступен. Причина -> %s".formatted(cause.getMessage()), cause);
    }
}
