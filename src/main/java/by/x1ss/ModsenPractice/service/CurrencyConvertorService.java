package by.x1ss.ModsenPractice.service;

import by.x1ss.ModsenPractice.calculator.Money;
import by.x1ss.ModsenPractice.entity.ExchangeRate;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class CurrencyConvertorService {
    private final JsonFactory jsonFactory;
    private final List<ExchangeRate> exchangeRates;

    public CurrencyConvertorService(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
        this.exchangeRates = new ArrayList<>();
    }

    @PostConstruct
    public void loadExchangeRates() throws IOException {
        for (Money money : Money.values()) {
            JsonParser parser = jsonFactory.createParser(URI.create("https://api.nbrb.by/exrates/rates/" + money.id).toURL());
            BigDecimal curScale = null;
            BigDecimal curRate = null;

            while(!parser.isClosed()){
                JsonToken jsonToken = parser.nextToken();

                if(JsonToken.FIELD_NAME.equals(jsonToken)){
                    String fieldName = parser.getCurrentName();

                    jsonToken = parser.nextToken();

                    if("Cur_Scale".equals(fieldName)){
                        curScale = new BigDecimal(parser.getValueAsString());
                    } else if ("Cur_OfficialRate".equals(fieldName)){
                        curRate = new BigDecimal(parser.getValueAsString());
                    }
                }
            }



            if(curRate != null && curScale != null){
                exchangeRates.add(ExchangeRate.builder()
                        .baseCurrency("BYN")
                        .exchangeCurrency(money.toString())
                        .rate(curRate.divide(curScale, 10, RoundingMode.FLOOR))
                        .build());
                exchangeRates.add(ExchangeRate.builder()
                        .baseCurrency(money.toString())
                        .exchangeCurrency("BYN")
                        .rate(BigDecimal.valueOf(1).divide(curRate.divide(curScale, 10, RoundingMode.FLOOR), 10, RoundingMode.FLOOR))
                        .build());
            }
        }
    }

    public BigDecimal convert(String baseCurrency, String exchangeCurrency, BigDecimal amount) {
        BigDecimal exchangeRate = getExchangeRate(baseCurrency, exchangeCurrency);
        return amount.multiply(exchangeRate);
    }

    public BigDecimal getExchangeRate(String baseCurrency, String exchangeCurrency) {
        for (ExchangeRate exchangeRate : exchangeRates) {
            if (exchangeRate.getBaseCurrency().equals(baseCurrency) && exchangeRate.getExchangeCurrency().equals(exchangeCurrency)) {
                return exchangeRate.getRate();
            }
        }
        throw new IllegalArgumentException("Exchange rate not found");
    }

    public List<ExchangeRate> getAllExchangeRates(){
        return exchangeRates;
    }
}
