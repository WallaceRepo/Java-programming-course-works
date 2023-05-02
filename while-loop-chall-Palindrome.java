public class whileLoopChallenge {
    public static void main(String[] args) {
        isPalindrome(345);

    }
     public static boolean isPalindrome(int number) {
          int reverse = 0;
          while (number > 0) {
              int lastDigit = number % 10;
                 reverse += lastDigit;
                 reverse *=10; // every repeat it gets multiplied by 10
                 number /=10; // removing the last digit of number
            }
          reverse /=10; // to remove extra 0 of end
          System.out.println(reverse);
          if(reverse == number) return true;
        return false;  
     }
}
