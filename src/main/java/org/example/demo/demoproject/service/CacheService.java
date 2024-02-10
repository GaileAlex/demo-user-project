package org.example.demo.demoproject.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Aleksei Gaile 10-Feb-24
 */
public interface CacheService {

    /**
     * Retrieves the initial balance for all users.
     *
     * @return a map containing the user IDs and their initial balance
     */
    Map<Long, BigDecimal> getInitialBalance();

}
