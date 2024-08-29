package deadlock;

import java.util.Random;
/** Deadlock occurs in multithreading when two or more threads are blocked forever, each waiting on the other to release a resource. In your code, you’re demonstrating a potential deadlock scenario with the MessageRepository class.

        Explanation of the Deadlock Scenario
        In the MessageRepository class:
        The read method waits for hasMessage to become true before it reads the message.
        The write method waits for hasMessage to become false before it writes a new message.
        However, in your code, both the read and write methods use a while loop to wait for the condition to change,
        but there’s nothing inside those loops. The threads are waiting indefinitely because neither the reader nor the writer is
       making progress—they're both just spinning, waiting for the condition to change.
        This situation won't cause a true deadlock because the loops aren't blocking the thread indefinitely with locks
       (they're just spinning), but it does represent a kind of "livelock" where both threads are active but not making progress.
*/
 // Consumer and Producer is going to use Message, so it's a shared resource.
class MessageRepository {
    private String message;
    private boolean hasMessage = false; // when this message flag is false, the Producer can populate the shared message. When has msg is true, the consumer can read it.
    public synchronized String read() {
        while ( !hasMessage) {

        }
        hasMessage = false;
        return message;
    }
    public synchronized void write (  String message ) {
         while( hasMessage ) {

         }
         hasMessage =  true;
         this.message = message;
    }
}
class MessageWriter implements Runnable {
 private MessageRepository outgoingMessage;
 private final String text = """
         Humpty Dumpty sat on a wall, 
         Humpty Dumpty had a great fall,
         All the king's horses and all the king's men, 
         Couldn't put Humpty together again.
         """;

    public MessageWriter(MessageRepository outgoingMessage) {
        this.outgoingMessage = outgoingMessage;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] lines = text. split("\n");

        for ( int i = 0; i < lines.length; i++ ) {
            outgoingMessage.write( lines[i]);
            try {
                Thread.sleep(random.nextInt( 500, 2000));
            } catch( InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        outgoingMessage.write("Finished");
     }
}

// Consumer class
class MessageReader implements Runnable {
    private MessageRepository incomingMessage;
    public MessageReader(MessageRepository incomingMessage) {
        this.incomingMessage = incomingMessage;
    }
   @Override
    public void run() {
          Random random =new Random();
          String latestMessage ="";
           do {
               try {
                   Thread.sleep(random.nextInt( 500, 2000));
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
               latestMessage = incomingMessage.read();
               System.out.println(latestMessage);
           } while (!latestMessage.equals("Finished"));
    }
}

public class Main {
    public static void main(String[] args) {
            MessageRepository messageRepository = new MessageRepository();

            Thread reader = new Thread(new MessageReader(messageRepository));
            Thread writer = new Thread( new MessageWriter(messageRepository));
            reader.start();
            writer.start();
    }
}
