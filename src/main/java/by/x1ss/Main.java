package by.x1ss;

import by.x1ss.convetror.CurrencyConvertor;

public class Main
{
    private static final CurrencyConvertor currencyConvertor = new CurrencyConvertor();
    public static void main( String[] args ) {
        try{
            currencyConvertor.loadExchangeRates("exchange_rates.csv");
        } catch (Exception e) {
            System.out.println("Error while loading exchange rates");
        }

    }
}
