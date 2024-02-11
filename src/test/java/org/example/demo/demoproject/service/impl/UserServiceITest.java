package org.example.demo.demoproject.service.impl;

import org.example.demo.demoproject.apiexeption.ApiException;
import org.example.demo.demoproject.model.request.UserAmountRequest;
import org.example.demo.demoproject.repository.UserRepository;
import org.example.demo.demoproject.securety.model.UserDetail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceITest {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15.3"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        UserDetail userDetail = new UserDetail(1L, null, null, null);
        lenient().when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetail);
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void transferAmount() {
        BigDecimal balanceUser1Before = userRepository.findById(1L).get().getAccount().getBalance();
        BigDecimal bigDecimal2Before = userRepository.findById(3L).get().getAccount().getBalance();
        UserAmountRequest amountRequest = new UserAmountRequest();
        amountRequest.setUserId(3L);
        amountRequest.setAmount(BigDecimal.TEN);

        userService.transferAmount(amountRequest);

        BigDecimal balanceUser1After = userRepository.findById(1L).get().getAccount().getBalance();
        BigDecimal bigDecimal2After = userRepository.findById(3L).get().getAccount().getBalance();

        assertEquals(balanceUser1Before.subtract(BigDecimal.TEN), balanceUser1After);
        assertEquals(bigDecimal2Before.add(BigDecimal.TEN), bigDecimal2After);
    }

    @Test
    void transferAmount_whenAmountIsZero() {
        UserAmountRequest amountRequest = new UserAmountRequest();
        amountRequest.setAmount(BigDecimal.ZERO);
        amountRequest.setUserId(3L);

        assertThrows(ApiException.class, () -> userService.transferAmount(amountRequest), "Amount must be positive");
    }

    @Test
    void transferAmount_whenAmountIsNegative() {
        UserAmountRequest amountRequest = new UserAmountRequest();
        amountRequest.setAmount(BigDecimal.valueOf(-1));
        amountRequest.setUserId(3L);

        assertThrows(ApiException.class, () -> userService.transferAmount(amountRequest), "Amount must be positive");
    }

    @Test
    void transferAmount_whenAmountIsNotEnough() {
        UserAmountRequest amountRequest = new UserAmountRequest();
        amountRequest.setAmount(BigDecimal.valueOf(1000));
        amountRequest.setUserId(3L);

        assertThrows(ApiException.class, () -> userService.transferAmount(amountRequest), "Not enough money");
    }

}
