package kata.academy.eurekafollowerservice.outer;


import kata.academy.eurekafollowerservice.SpringSimpleContextTest;
import kata.academy.eurekafollowerservice.feign.AuthServiceFeignClient;
import kata.academy.eurekafollowerservice.feign.NotificationServiceFeignClient;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({
        @MockBean(AuthServiceFeignClient.class),
        @MockBean(NotificationServiceFeignClient.class)

})


public class FollowerRestControllerIT extends SpringSimpleContextTest {

    @Autowired
    private AuthServiceFeignClient authServiceFeignClient;



    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/FollowerRestController/follow_SuccessfulTest/After.sql")
    public void follow_SuccessfulTest() throws Exception {
        Long userId = 10L;
        Long followerId = 2L;
        doReturn(Boolean.TRUE).when(authServiceFeignClient).existsById(userId);
        mockMvc.perform(post("/api/v1/followers/{userId}", userId)
                        .header("userId", followerId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(fl.id) > 0
                                FROM Follower fl
                                WHERE fl.userId = :userId
                                AND fl.followerId = :followerId
                             
                                """, Boolean.class)
                .setParameter("userId", userId)
                .setParameter("followerId", followerId)
                .getSingleResult());
    }



    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/FollowerRestController/follow_ExistsTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/FollowerRestController/follow_ExistsTest/After.sql")
    public void follow_SubscriptionExistsTest() throws Exception {
        Long userId = 1L;
        Long followerId = 2L;
        doReturn(Boolean.TRUE).when(authServiceFeignClient).existsById(userId);
        mockMvc.perform(post("/api/v1/followers/{userId}", userId)
                        .header("userId", followerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text", Is.is(
                        String.format("Вы уже подписаны на пользователя %d", userId)))).andDo(print());
    }



    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/FollowerRestController/unFollow_SuccessfulTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/FollowerRestController/unFollow_SuccessfulTest/After.sql")
    public void unFollow_SuccessfulTest() throws Exception {
        Long userId = 1L;
        Long followerId = 2L;
        mockMvc.perform(delete("/api/v1/followers/{userId}", userId)
                        .header("userId", Long.toString(followerId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertFalse(entityManager.createQuery("""
                        SELECT COUNT(fl.id) > 0
                        FROM Follower fl
                        WHERE fl.userId = :userId
                        AND fl.followerId = :followerId
                        """, Boolean.class)
                .setParameter("userId", userId)
                .setParameter("followerId", followerId)
                .getSingleResult());
    }




}
