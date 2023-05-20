public class Main {

    public static void main(String[] args) {
          // string manipulation
        reverseWords("Hello   world");

    }
       public static String reverseWords(String s) {

            String result = "";
            // trim the string to remove extra spaces
             String arr = s.trim();

            // split string into an array

            String [] words = arr.split(" ");

            // loop through the array backwards and concatinate the space
           // "Hello   world" split ["Hellow","","","","World"]
            for( int i = words.length-1; i >= 0; i--) {
                if(words[i] != "") {
                    if( result.length() == 0) {
                        result = words[i];
                    } else {
                        result += " " + words[i];
                    }

                }
            }

            // trim output

            // return string
           System.out.println(result);
         return result;
    }
}
