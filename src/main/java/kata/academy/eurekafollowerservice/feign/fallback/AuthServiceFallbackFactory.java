package kata.academy.eurekafollowerservice.feign.fallback;


import kata.academy.eurekafollowerservice.feign.AuthServiceFeignClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceFallbackFactory implements FallbackFactory<AuthServiceFeignClient> {


    @Override
    public AuthServiceFeignClient create(Throwable cause) {
        return new AuthServiceFallback(cause);
    }
}
