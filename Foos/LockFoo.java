package Foos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockFoo implements Foo {
    private final Lock locker;
    private final Condition firstFinished;
    private final Condition secondFinished;
    private boolean firstDone;
    private boolean secondDone;


    public LockFoo() {
        locker = new ReentrantLock();
        firstFinished = locker.newCondition();
        secondFinished = locker.newCondition();
        firstDone = false;
        secondDone = false;
    }

    @Override
    public void first(Runnable r) {
        locker.lock();
        try {
            r.run();
            firstDone = true;
            firstFinished.signal();
        } finally {
            locker.unlock();
        }
    }

    @Override
    public void second(Runnable r) {
        locker.lock();
        try {
            while(!firstDone) {
                firstFinished.await();
            }
            r.run();
            secondDone = true;
            secondFinished.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            locker.unlock();
        }
    }

    @Override
    public void third(Runnable r) {
        locker.lock();
        try {
            while(!secondDone) {
                secondFinished.await();
            }
            r.run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            locker.unlock();
        }

    }
}
