/// Prompt the user to enter number or any character to quit it
// if user entered is not a number quit the prompt loop. Otherwise keep promt and get user input;
//  keep track of min number of the user entered.
// keep track of the max number of the user entered. 
// After user quit, display the min, and max numbers user entered previosly.

import java.util.Scanner;

public class MinMaxChallenge {
    public static void main(String[] args) {
         inputThenPrintSumAndAverage();
    }
    public static void inputThenPrintSumAndAverage(){
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        long avg = 0;
        int count = 0;
        while(true) {
            System.out.println("Enter number ");
            String nextLine = scanner.nextLine();
            try {
                 sum += Integer.parseInt(nextLine);
                 count++;
                 avg = Math.round(sum / count);
            } catch(NumberFormatException er) {
                break;
            }
        }
       System.out.println("SUM = " + sum + " " + "AVG = " + avg );
    }
} 

//// Without "Enter number" prompt
import java.util.Scanner;
 
public class InputCalculator {
    // Write your code here
public static void inputThenPrintSumAndAverage() {
    Scanner scanner = new Scanner(System.in);
 
    int sum = 0;
    long avg = 0;
    int counter = 0;
 
    while (true) {
        if (scanner.hasNextInt()) {
            sum += scanner.nextInt();
            avg = Math.round((double) sum / ++counter);
        } else {
            break;
        }
        scanner.nextLine();
    }
 
    scanner.close();
 
    System.out.println("SUM = " + sum + " AVG = " + avg);
}
}

