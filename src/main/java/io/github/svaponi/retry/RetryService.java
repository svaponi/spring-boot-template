package io.github.svaponi.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;

import java.time.Duration;

@Slf4j
class RetryService {

    private static final int MAX_ATTEMPTS = 5;
    private static final double PERC = 25.0;

    volatile int failures = 0;
    volatile long lastRun = System.currentTimeMillis();

    @Retryable(value = {RuntimeException.class}, maxAttempts = MAX_ATTEMPTS, backoff = @Backoff(delay = 100, multiplier = 2))
    @Scheduled(fixedDelay = 1000)
    public void run() throws IllegalArgumentException {
        final double random = Math.floor(Math.random() * 100);
        try {
            Assert.isTrue(PERC >= random, "RetryService failure");
            failures = 0;
            log.info("@Scheduled ({} ms from last run) assert {} >= {} --> ok", Duration.ofMillis(System.currentTimeMillis() - lastRun).toMillis(), PERC, random);
        } catch (final Exception e) {
            failures++;
            log.info("@Scheduled ({} ms from last run) assert {} >= {} --> failure {}/{}", Duration.ofMillis(System.currentTimeMillis() - lastRun).toMillis(), PERC, random, failures, MAX_ATTEMPTS);
            throw e;
        } finally {
            lastRun = System.currentTimeMillis();
        }
    }

    @Recover
    public void recover(final RuntimeException e) {
        log.warn("@Recover after {} failures", failures);
        failures = 0;
    }
}
