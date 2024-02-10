package org.example.demo.demoproject.service;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
public interface UserSchedulerService {

    /**
     * Increases the balance of all users if their current balance is less than the maximum allowed balance.
     */
    void increaseUserBalance();

}
