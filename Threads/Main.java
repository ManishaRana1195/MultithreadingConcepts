
public class Main {
    public static void main() throws Exception {
        // start() is called on your thread class
        Worker threadA = new Worker("A");
        // Your thread class is passed in the thread class and its start method is called
        Thread threadB = new Thread(new RunnableWorker("B"));
        Thread threadC = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                String name = Thread.currentThread().getName();
                System.out.println(name + ": " + (i + 1));
            }
        }, "C");

        // Need to start all threads for parallel execution
        threadA.start();
        threadB.start();
        threadC.start();
        threadA.join();
        threadB.join();
        threadC.join();
        // We use join so that the main thread waits for the above 3 threads to complete
        System.out.println("Main thread is proceeding");

    }
}
