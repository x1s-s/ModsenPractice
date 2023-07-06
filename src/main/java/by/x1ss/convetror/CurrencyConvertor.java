package by.x1ss.convetror;

import by.x1ss.entity.ExchangeRate;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
public class CurrencyConvertor {
    private List<ExchangeRate> exchangeRates;

    public CurrencyConvertor() {
        exchangeRates = new ArrayList<>();
    }

    public void loadExchangeRates(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(",");
            exchangeRates.add(new ExchangeRate(line[0], line[1], new BigDecimal(line[2])));
        }
        scanner.close();
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
}
