public class ProducerConsumerExample {

    static void main() {
        SharedQueue sharedQueue = new SharedQueue(50);
        Thread p1 = new Thread(new Producer(sharedQueue), "P1");
        Thread p2 = new Thread(new Producer(sharedQueue), "P2");
        Thread c1 = new Thread(new Consumer(sharedQueue), "C1");
        Thread c2 = new Thread(new Consumer(sharedQueue), "C2");
        Thread c3 = new Thread(new Consumer(sharedQueue), "C3");

        p1.start();
        p2.start();
        c1.start();
        c2.start();
        c3.start();
    }
}

class Producer implements Runnable {
    private final SharedQueue queue;

    Producer(SharedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // Currently producers will produce message with same idx, because there is no shared global context.
        // To do that we can use AtomicInteger.
        for (int i = 0; i < 50; i++) {
            try {
                queue.produceMessage("message-" + i);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.setDone();
    }
}

class Consumer implements Runnable {
    private final SharedQueue queue;

    Consumer(SharedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (queue.getDone()) {
                    break;
                }
                String message = queue.consumeMessage();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

