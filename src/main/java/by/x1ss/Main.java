package by.x1ss;

import by.x1ss.calculator.Calculator;

import java.io.FileNotFoundException;

public class Main
{
    public static void main( String[] args ) {
        Calculator calculator;
        try {
            calculator = new Calculator("exchange_rates.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            System.out.println(calculator.calculate("toDollars(100р + 200р)"));
        } catch (NumberFormatException e){
            System.out.println("Wrong operation : " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }
}
