
public class Main {
    public static void main() throws Exception {
        // start() is called on your thread class
        Worker worker = new Worker("A");
        worker.start();

        // Your thread class is passed in the thread class and its start method is called
        Thread thread = new Thread(new RunnableWorker("B"));
        thread.start();
    }
}
