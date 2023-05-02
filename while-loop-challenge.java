// I did
public class whileLoopChallenge {
    public static void main(String[] args) {
         int i = 5;
         while( i <= 20) {
             if(isEvenNumber(i)) {
                 System.out.println(i);
             }
             i++;
         }
    }

    public static boolean isEvenNumber(int number){
        if(number % 2 == 0) {
            return true;
        }
       return false;
    }
}

// lesson did
public class whileLoopChallenge {
    public static void main(String[] args) {
         int i = 4;
         int evenCount = 0;
         int oddCount = 0;
         while( i <= 20) {
             i++;
             if(!isEvenNumber(i)) {
                 oddCount ++;
                continue;
             }
             System.out.println(evenCount);
             evenCount++;
             if(evenCount == 5) {
                 break;
             }
            System.out.println(i);
         }
        System.out.println(oddCount);

    }

    public static boolean isEvenNumber(int number){
        if(number % 2 == 0) {
            return true;
        }
       return false;
    }
}

/// Sum of digit I did with while loop :-)

public class whileLoopChallenge {
    public static void main(String[] args) {

        System.out.println(sumDigits(126));
    }
     public static int sumDigits(int number){
        if(number < 0) {
            return -1;
        }
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number = number / 10;
        }

        return sum;

      }

}
