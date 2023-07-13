package by.x1ss.ModsenPractice.bannkclient;

import by.x1ss.ModsenPractice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "bank-client", url = "${bank.api.url}", configuration = FeignConfig.class)
public interface BankClient {
    @GetMapping
    String getExchangeRate();
}
