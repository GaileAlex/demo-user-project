package org.example.demo.demoproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.demoproject.entity.UserEntity;
import org.example.demo.demoproject.repository.UserRepository;
import org.example.demo.demoproject.service.CacheService;
import org.example.demo.demoproject.service.UserSchedulerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * @author Aleksei Gaile 07-Feb-24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserSchedulerServiceImpl implements UserSchedulerService {
    private final UserRepository userRepository;
    private final CacheService cacheService;

    @Value("${maximum.percentage.for.increase}")
    private Integer percentage;

    @Override
    @Retryable(maxAttempts = 15)
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void increaseUserBalance() {
        Map<Long, BigDecimal> initialBalance = cacheService.getInitialBalance();
        List<UserEntity> users = userRepository.findAll();

        users.forEach(user -> {
            BigDecimal userInitialBalance = initialBalance.get(user.getId());
            BigDecimal maxBalance = userInitialBalance.multiply(BigDecimal.valueOf(percentage))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.UP);

            if (user.getAccount().getBalance().compareTo(maxBalance) < 0) {
                BigDecimal userNewBalance = user.getAccount().getBalance().multiply(BigDecimal.valueOf(1.1))
                        .setScale(2, RoundingMode.UP);

                if (userNewBalance.compareTo(maxBalance) > 0) {
                    userNewBalance = maxBalance;
                }

                user.getAccount().setBalance(userNewBalance);
                userRepository.save(user);

                log.info("user's balance({}) with ID {} has increased, new balance- {}",
                        user.getAccount().getBalance(), user.getId(), userNewBalance);
            }

        });
    }

}
