### Topics covered
- [x] 1. Thread Basics   
- [x] 2. Synchronization / Locks   
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

### Synchronization / Locks
In multithreaded system, threads interfere with each other and access same variables/objects/resources at the same time causing the state/data to be
inconsistent also called as race condition.

#### Synchronize at class level using class lock

```
   private static int count = 0;
    public static synchronized void increment() {
        count++;
    }
```
As the shared variable is class level, class level lock makes more sense. So, ClassName.class lock is used. All its instances share the same lock.
The above gets converted to 
```
   private static int count = 0;
    public static void increment() {
      synchronized(Counter.class){
        count++;
      }
    }
```

#### Synchronize at method level using object lock
``` 
public synchronized void increment()
```
or
``` 
public void increment() {
    synchronized(this) {
        count++;
        #rest of the method
    }
}
```
All the code in the method is locked using "this" - the current object lock. It means    
Thread A calling counter.increment() gets the lock of that object     
Thread B calling the same method on the same object must wait

#### Synchronize at block level using some object lock
```
private final Object lock = new Object();
public void increment(){
   readFile();
   synchronized(lock){
      count++;
   }
   printResult();
}
```

Any object is used as lock and is passed to the synchronized block. A lock is nothing but an object's Monitor. A Java object contains
user defined data field and the monitor to handle thread management, that is why it has wait(), notify(), notifyAll() methods.
When the thread acquires the lock(whichever type of lock), it is telling JVM the object is locked by flipping bits in the object.
Then the thread perform its code execution, flips the bit again and release the lock.
The flipping flips the MARK word present in the object header.

```
Java Object
 ├─ Data fields
 ├─ Method 
 └─ Monitor (Mechanism for synchronization - MARK word)
      ├─ Lock owner (thread)
      ├─ Entry queue (threads waiting to acquire lock)
      └─ Wait set (threads waiting from wait())
```

#### Issues with Synchronized 
Lock contention - Blocked threads cannot do other work and consume system resources.    
Deadlocks - two threads wait for each other forever.    
No timeout - a thread can wait forever.    
No fairness - The JVM does not guarantee order in thread execution, can cause thread starvation.
