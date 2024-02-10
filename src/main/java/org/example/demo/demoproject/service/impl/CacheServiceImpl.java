package org.example.demo.demoproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.demo.demoproject.entity.UserEntity;
import org.example.demo.demoproject.repository.UserRepository;
import org.example.demo.demoproject.service.CacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
    private final UserRepository userRepository;

    @Cacheable(value = "balance", key = "#root.methodName")
    public Map<Long, BigDecimal> getInitialBalance() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().collect(Collectors.toMap(UserEntity::getId, user->user.getAccount().getBalance()));
    }

}
