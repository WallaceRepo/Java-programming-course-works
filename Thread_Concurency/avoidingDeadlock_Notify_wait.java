/* wait(): Causes the current thread to pause and release the lock on the object until another thread calls notifyAll().
notifyAll(): Wakes up all threads waiting on the same object so they can check if they can proceed.
In below code, these methods coordinate the actions of the producer and consumer, ensuring they take turns 
accessing the shared MessageRepository resource without conflicts or deadlocks.
  */

class MessageRepository {
    private String message;
    private boolean hasMessage = false; // when this message flag is false, the Producer can populate the shared message. When has msg is true, the consumer can read it.
    public synchronized String read() {
        while ( !hasMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        hasMessage = false;
        notifyAll(); //
        return message;
    }
    public synchronized void write (  String message ) {
         while( hasMessage ) {
             try {
                 wait();
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
         }
         hasMessage =  true;
         notifyAll();
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
