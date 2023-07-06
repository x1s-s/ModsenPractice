package by.x1ss;

import by.x1ss.calculator.Calculator;
import by.x1ss.calculator.Money;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main
{
    public static void main( String[] args ) {
        Calculator calculator;
        try {
            calculator = new Calculator("exchange_rates.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }


        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Enter expression (enter exit to close application):");
            String expression = scanner.nextLine();
            if(expression.equals("exit")){
                break;
            }
            try {
                System.out.println(calculator.calculate(expression));
            } catch (NumberFormatException e){
                boolean checker = true;
                for (var money : Money.values()) {
                    if (e.getMessage().contains(String.valueOf(money.symbol))) {
                        System.out.println("Wrong operation " + money.name() + " value. Error of converting currency: different currencies in one addition or subtraction operation. ");
                        checker = false;
                    }
                }
                if(checker){
                    System.out.println("Wrong operation: " + e.getMessage().charAt(10));
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }


    }
}
