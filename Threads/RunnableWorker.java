public class RunnableWorker implements  Runnable{
    String name;

    RunnableWorker(String threadName){
        name = threadName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name+": "+ (i+1));
        }
    }
}
