import java.util.LinkedList;
import java.util.Queue;

public class SharedQueue {
    private final Queue<String> messages = new LinkedList<>();
    private final int capacity;
    private boolean done;

    SharedQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produceMessage(String message) throws InterruptedException {
        while (messages.size() == capacity) {
            // even if other producers threads come here, they will wait till the message is consumed
            wait();
        }
        messages.add(message);
        System.out.println(Thread.currentThread().getName() + " -> " + message);
        notifyAll();
    }

    public synchronized String consumeMessage() throws InterruptedException {
        while (messages.isEmpty()) {
            // even if other consumer threads come here, they will wait till the queue is filled up
            wait();
        }

        String message = messages.poll();
        System.out.println(Thread.currentThread().getName() + " -> " + message);
        notifyAll();
        return message;
    }

    public synchronized void setDone(){
        done = true;
        // To wake up all consumers if any one is waiting
        notifyAll();
    }
    public synchronized boolean getDone(){
        return done;
    }
}
