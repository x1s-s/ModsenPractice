package by.x1ss.ModsenPractice.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExchangeRateCacheCleaner {
    @CacheEvict(value = "exchangeRates", allEntries = true)
    @Scheduled(cron = "${cache.reload.cron}")
    public void clearCache() {
        log.info("Clearing cache");
    }
}
