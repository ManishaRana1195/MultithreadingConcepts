### Topics covered
- [x] 1. Thread Basics   
- [ ] 2. Synchronization / Locks   
- [ ] 3. Volatile & Memory Visibility/Memory Model    
- [ ] 4. Atomic Variables  
- [ ] 5. Executors / Thread Pools   
- [ ] 6. Producer–Consumer / Blocking Queues   
- [ ] 7. Concurrent Collections   
- [ ] 8. Coordination Primitives   
- [ ] 9. Locks (Advanced)    
- [ ] 10. Deadlocks / Livelocks / Starvation   
- [ ] 11. Fork/Join Parallelism    
- [ ] 12. Asynchronous Programming   
- [ ] 13. Parallel Streams

### Thread Basics
start() → creates a new thread    
run() → just runs in the current thread, use start() method    
join() → makes the current thread/main wait for the thread join is called on. It waits either for the lock, signal or another thread to complete.    
sleep() → pauses the thread 

Create threads by
1. Extending Thread class
    1. Limitation - Java classes cant extend multiple classes, so using Runnable is better approach
2. Implementing Runnable
3. Using Callable
    1. Callable task is passed to executor service
    2. Callable can return values, so using future.get() you can retrieve the result.


