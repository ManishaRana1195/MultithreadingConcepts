### Thread

start() → creates a new thread    
run() → just runs in the current thread, use start() method    
join() → makes thread wait, either for the lock, signal or another thread to complete.    
sleep() → pauses the thread 

Create threads by

1. Extending Thread class
    1. Limitation - Java classes cant extend multiple classes, so using Runnable is better approach
2. Implementing Runnable
3. Using Callable
    1. Callable task is passed to executor service
    2. Callable can return values, so using future.get() you can retrieve the result.