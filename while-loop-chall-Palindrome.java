public class whileLoopChallenge {
    public static void main(String[] args) {
        isPalindrome(101);
        System.out.println(isPalindrome(101));
    }
     public static boolean isPalindrome(int number) {
          int original = number; // number gets changed after while loop!
          int reverse = 0;
          while (number > 0) {
              int lastDigit = number % 10;
                 reverse += lastDigit;
                 reverse *=10; // every repeat it gets multiplied by 10
                 number /=10; // removing the last digit of number
            }
           reverse /=10;
          System.out.print(reverse);
          if(reverse == original) return true;
        return false;
     }
}
