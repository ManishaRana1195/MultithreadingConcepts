### Topics covered
- [x] 1. Thread Basics   
- [x] 2. Synchronization / Locks   
- [x] 3. Volatile & Memory Visibility/Memory Model    
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

### Three Core Problems in Multi-Threading
| Problem                   | What it is                                              | How to fix                      |
|---------------------------|---------------------------------------------------------|---------------------------------|
| **Visibility**            | Thread doesn’t see latest value                         | `volatile`, `synchronized`      |
| **Reordering / Ordering** | Operations appear in a different order to other threads | `volatile`, `synchronized`      |
| **Atomicity**             | Compound operations are not indivisible                 | `synchronized`, `AtomicInteger` |


### Thread Basics
start() → creates a new thread    
run() → just runs in the current thread, use start() method    
join() → makes the current thread/main wait for the thread join is called on. It waits either for the lock, signal or another thread to complete.    
sleep() → pauses the thread   
wait()     → thread go to sleep & release lock
notify()   → wake up 1 sleeping thread
notifyAll()→ wake up all sleeping threads

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
![Java Object Monitor](https://github.com/ManishaRana1195/MultithreadingConcepts/blob/e2c37c2819eb193fe7ef632bcb706bb7db1c5a82/Images/JavaObjectMonitor.gif) 

#### Issues with Synchronized 
Lock contention - Blocked threads cannot do other work and consume system resources.    
Deadlocks - two threads wait for each other forever.    
No timeout - a thread can wait forever.    
No fairness - The JVM does not guarantee order in thread execution, can cause thread starvation.

### Memory Visibility and Memory Model
```
           Main Memory
           counter = 0
            /     \
     CPU Cache   CPU Cache
     (Thread A)  (Thread B)
```
Each thread may work with CPU caches. So, the count updated by Thread A may not be visible to Thread B as it is not written to memory, but is in the cache.
This is memory visibility problem for threads as they night not see updated value.
Synchronized provides visibility as each thread update is written to the memory.

JMM = rules for memory visibility among threads + ordering



### Volatile
Eg - volatile boolean ready = true;

We also have volatile keyword to define variables. It is light-weight mechanism, where the updated value is flushed to the memory. So that updating variable -> is immediately 
visible to another thread and read a variable -> is always read from main memory.

It is ideal for simple state flags. But should be used for counters as it doesnt guarantee atomicity.

```
volatile
   ↓
visibility only

synchronized
   ↓
visibility + atomicity + locking
```
