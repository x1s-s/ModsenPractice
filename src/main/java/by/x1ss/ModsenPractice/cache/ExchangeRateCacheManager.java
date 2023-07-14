package by.x1ss.ModsenPractice.cache;

import by.x1ss.ModsenPractice.service.ExchangeRateGetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateCacheManager {
    private final ExchangeRateGetService exchangeRateGetService;
    private final CacheManager cacheManager;

    @CacheEvict(value = "exchangeRates", allEntries = true)
    public void clearCache() {
        if(cacheManager.getCacheNames().isEmpty()){
            log.info("Cache cleared");
        } else {
            log.warn("Cache not cleared");
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startLoadCache() {
        exchangeRateGetService.getExchangeRates();
        log.info("Cache loaded : {}", Objects.requireNonNull(
                cacheManager.getCache("exchangeRates")).getNativeCache());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void reloadCache() {
        clearCache();
        startLoadCache();
    }
}
