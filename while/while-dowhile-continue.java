public class whileLopping {
    public static void main(String[] args) {
        for(int i =1; i <= 5; i++) {
           System.out.println(i);
        }
        int j = 1;
        //// using while loop
        while(j<=5){
            System.out.println(j);
            j++;
        }
        ////// set condition with break
        int k = 1;
        while(true){
            if(k > 5){
                break;
            }
            System.out.println(k);
            k++;
        }
        ///
        boolean isReady = false;
        int m = 1;
        while(isReady) {
            if(m > 5) {
               break;
            }
            System.out.println(k);
            m++;
        }

        ////do while loop
        boolean isDone = false;
        int n = 1;
        do {
            if(n > 5) {
              break;
            }
            System.out.println(k);
            n++;
            isDone = (j > 0); this will make isDone true
        } while(isDone);
      
      
        /// continue stops current iteration of block of code and start a new iteration

        int number = 0;
        while (number <  50) {
            number += 5;
            if( number % 25 == 0) {
                continue;  // it starts new iteration if cnd is true;
            }
            System.out.print((number + "_"));
           }
         }
        }
    }
  
}
