// Largest Prime
// Write a method named getLargestPrime with one parameter of type int named number. 

// If the number is negative or does not have any prime numbers, the method should return -1 to indicate an invalid value.



// The method should calculate the largest prime factor of a given number and return it.



// EXAMPLE INPUT/OUTPUT:

// getLargestPrime (21); should return 7 since 7 is the largest prime (3 * 7 = 21)

// getLargestPrime (217); should return 31 since 31 is the largest prime (7 * 31 = 217)

// getLargestPrime (0); should return -1 since 0 does not have any prime numbers

// getLargestPrime (45); should return 5 since 5 is the largest prime (3 * 3 * 5 = 45)

// getLargestPrime (-1); should return -1 since the parameter is negative

// It was even hard to understand!!!!!!!
// Someone did it :)


public class LargestPrime {

    public static void main(String[] args) {
        System.out.println(getLargestPrime(23));
    }
    // fastest way finding prime number;
    // Prime number is divided only by itself or 1;
    public static int getLargestPrime(int number) {
        if(number <= 0) {
            return -1;
        }
       //  int square = (int)Math.sqrt(number);
        //System.out.println(square);
        int i = 2;
        int num = number;
        int k = 0;
        while (i <= number / 2 ){  // not divisible by any number from 2 up to n / 2
            while (num % i == 0) {
                num /=i;
                k = i;
            }
            i++;
        }
        if(k!= 0) {
            return k;
        } else {
            return number;
        }
     }
}
