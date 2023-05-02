public class whileLoopChallenge {
    public static void main(String[] args) {
         System.out.println("result " + lastDigitChecker(23,32,42));
    }
     public static boolean lastDigitChecker(int num1, int num2, int num3) {
             if (num1 < 10 || num2 < 10 || num1 > 1000 || num2 > 1000 || num3 < 10 || num3 > 1000) {
                 return false;
             }
             int lastDigit1 = num1 % 10;  // 1 % 10 = 1
             int lastDigit2 = num2 % 10;
             int lastDigit3 = num3 % 10;

             if (lastDigit1 == lastDigit2 || lastDigit1 == lastDigit3 || lastDigit2 == lastDigit3) {
                 return true;
             }
          return false;
      }
}

// Someone did below

public class LastDigitChecker {
    public static boolean hasSameLastDigit(int a, int b, int c){
        if((a < 10 || a > 1000) || (b < 10 || b > 1000) || (c < 10 || c > 1000)){
            return false;
        }
        return ((a % 10 == b % 10) || (b % 10 == c % 10) || (c % 10 == a % 10));
    }
    public static boolean isValid(int num){
        return ((num >= 10) && (num <= 1000));
    }
}
