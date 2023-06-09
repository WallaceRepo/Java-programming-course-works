// Java For loop 
public class primeNumber {
    public static void main(String[] args) {
        System.out.println("0 is " + (isPrime(0) ? "" : "NOT ") + "a prime number");
        System.out.println("1 is " + (isPrime(1) ? "" : "NOT ") + "a prime number");
        System.out.println("2 is " + (isPrime(2) ? "" : "NOT ") + "a prime number");
        System.out.println("3 is " + (isPrime(3) ? "" : "NOT " )+ "a prime number");

        System.out.println("8 is " + (isPrime(8) ? "" : "NOT ") + "a prime number");

        System.out.println("17 is " + (isPrime(17) ? "" : "NOT ") + "a prime number");
        System.out.println("17 has " + isCount(17) + " prime numbers");
        int count = 0;
        for(int i = 10; count < 3 && i <=50; i++) {
            if(isPrime(i)){
                System.out.println("number " + i + " is a prime number");
                count++;
            }
        }
    }
    public static boolean isPrime(int wholeNumber) {
        if( wholeNumber <= 2 ) {
            return (wholeNumber == 2);
        }
        for( int divisor = 2; divisor <= wholeNumber/2; divisor++) {
           if(wholeNumber % divisor == 0) {
               return false;
           }
        }
        return true;
    }
    public static int isCount(int wholeNumber) {
        int count = 0;
        if( wholeNumber == 2 ) {
            count+=1;
        } else if( wholeNumber < 2 || wholeNumber > 1000) {
            return count;
        }
        for( int divisor = 2; divisor <= wholeNumber; divisor++) {
            if (wholeNumber % divisor != 0) {
                count += 1;
                if (count == 3) {
                    break;
                }
            }
        }
        return count;
    }
}
//// for loop challenge

public class ForLoopChallenge {
    public static void main(String[] args) {
        for (double rate = 7.5; rate <=10; rate+=0.25){
            double interestAmount = calculateInterest(100.0, rate);
            if(interestAmount > 8.5){
                break;
            }
            System.out.println("10 000 at " + rate + " % interest = " + interestAmount);
        }
   }
    public static double calculateInterest(double amount, double interestRate){
        return ( amount * (interestRate / 100));
    }

}
