package Foos;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchFoo implements Foo {
    CountDownLatch firstDone;
    CountDownLatch secondDone;
    public CountDownLatchFoo() {
        firstDone = new CountDownLatch(1);
        secondDone = new CountDownLatch(1);
    }

    @Override
    public void first(Runnable r) {
        r.run();
        firstDone.countDown();
    }

    @Override
    public void second(Runnable r) {
        try {
            firstDone.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        r.run();
        secondDone.countDown();
    }

    @Override
    public void third(Runnable r) {
        try {
            secondDone.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        r.run();
    }
}
