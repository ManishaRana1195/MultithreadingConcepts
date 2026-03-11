import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerExample {
    private final Queue<String> messages = new LinkedList<>();
    private final int capacity = 10;



    public synchronized void produceMessage(String message) throws InterruptedException {
        while (messages.size() == capacity) {
            wait();
        }
        messages.add(message);
        notifyAll();
    }

    public synchronized String consumeMessage() throws InterruptedException {
        while (messages.isEmpty()) {
            wait();
        }
        String message = messages.poll();
        notifyAll();
        return message;
    }
}
