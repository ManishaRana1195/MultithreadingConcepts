class Worker extends Thread {
    String name;

    Worker(String threadName) {
        name = threadName;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + ": " + (i + 1));
        }
    }
}


