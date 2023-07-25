package by.x1ss.ModsenPractice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import by.x1ss.ModsenPractice.bannkclient.BankClient;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BankClientTest extends AbstractTest{
    @Autowired
    private BankClient bankClient;

    @Test
    public void testGetExchangeRate() {
        String response = bankClient.getExchangeRate();
        assertNotNull(response);
        assertNotEquals(response, "");
    }
}
