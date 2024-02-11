package org.example.demo.demoproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final UserSchedulerService userSchedulerService;

    @Value("${balance.run}")
    private boolean isBalanceSync;

    @Scheduled(cron = "${balance.scheduled}")
    public void syncBalance() {
        if (!isBalanceSync) {
            log.info("Synchronization disabled");
            return;
        }

        sync();
    }

    private void sync() {
        userSchedulerService.increaseUserBalance();
    }

}
