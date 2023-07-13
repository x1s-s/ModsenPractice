package by.x1ss.ModsenPractice.cache;

import by.x1ss.ModsenPractice.service.ExchangeRateGetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateCacheManager {
    private final ExchangeRateGetService exchangeRateGetService;

    @CacheEvict(value = "exchangeRates", allEntries = true)
    public void clearCache() {
        log.info("Cache cleared");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startLoadCache() {
        log.info("Cache loaded : {}", exchangeRateGetService.getExchangeRates());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void reloadCache() {
        clearCache();
        startLoadCache();
    }
}
