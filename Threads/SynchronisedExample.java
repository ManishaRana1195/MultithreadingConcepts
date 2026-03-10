import java.util.ArrayList;

public class SynchronisedExample {

    static void main() {
        Counter counter = new Counter();
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            threads.add(new Thread(() -> {
                counter.increment();
                // Do not print here because increment is synchronized but get counter is not,
                // so the counter will get increment but by the time it getting printed,
                // another thread would have modified it
                //  String name = java.lang.Thread.currentThread().getName();
                //  System.out.println(name + " : " + counter.getCounter());
            }));
        }
        threads.forEach(Thread::start);
    }
}

class Counter {
    private int counter;
    private final Object lock = new Object();

    public void increment() {
        // block level - can be 'this' object or 'any object' as lock
        synchronized (lock) {
            counter++;
            System.out.println(Thread.currentThread().getName() + " -- " + counter);
            try {
                Thread.sleep(2000); // Simulate other work
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // method level lock
    public synchronized void decrement() {
        counter--;
    }

    public int getCounter() {
        return counter;
    }

}



