// Read 5 valid number from the console, entered by the user, and print the sum of the numbers. 
// You'll want to check that the numbers entered, are valid integers.
// if not, print out message "Invalid number" to the console, but continue looping, until you have 5 valid numbers.
// Before the user enters each number, prompt them with the message, "Enter number #x:", where x represents the count: 1,2,3,ect.
// And as an example there, the first message would look something like, "Enter number #1:", the next , "Enter number #2:", and so on.

import java.util.Scanner;

public class ParsingValuesReadInput {
    public static void main(String[] args) {
              System.out.println(getInputFromScanner());
    }
     public static int getInputFromScanner(){
          Scanner scanner = new Scanner(System.in);
          int sum = 0;
          // int count = 1;
         double count = 1;
          do {
              System.out.println("enter number # " + count + " ? ");
              
              try {
                  // int number = Integer.parseInt(scanner.nextLine());
                  double number = Double.parseDouble(scanner.nextLine());
                  count++;
                  sum += number;
              } catch(NumberFormatException badUserData){
                  System.out.println("Characters not allowed, Try again.");
              }
           } while (count <= 5);

         return sum;
     }

 }
