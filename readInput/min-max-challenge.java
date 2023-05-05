/// Prompt the user to enter number or any character to quit it
// if user entered is not a number quit the prompt loop. Otherwise keep promt and get user input;
//  keep track of min number of the user entered.
// keep track of the max number of the user entered. 
// After user quit, display the min, and max numbers user entered previosly.

import java.util.Scanner;

public class MinMaxChallenge {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double max = 0;
        double min = 0;
        int loopCount = 0;
        while( true) {
            System.out.println("Enter a number, or any character to exit:");
            String nextEntry = scanner.nextLine();
            try {
                 double validNum = Double.parseDouble(nextEntry);
                 if(loopCount == 0 || validNum < min) {
                     min = validNum;
                 }
                 if(loopCount == 0 || validNum > max){
                     max = validNum;
                 }
                 loopCount++;
            } catch (NumberFormatException nfe) {
                break;
            }
        }
        if(loopCount > 0) {
            System.out.println("min = " + min + ", max = " + max);
        } else {
            System.out.println("No valid data entered");
        }
    }
}
