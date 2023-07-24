package Foos;

import java.util.concurrent.Semaphore;

public class SemaphoreFoo implements Foo {
    private final Semaphore semaphore1;
    private final Semaphore semaphore2;

    public SemaphoreFoo() {
        semaphore1 = new Semaphore(1);
        semaphore2 = new Semaphore(1);
        semaphore1.acquireUninterruptibly();
        semaphore2.acquireUninterruptibly();

    }

    public void first(Runnable r) {
        r.run();
        semaphore1.release();

    }

    public void second(Runnable r) {
        semaphore1.acquireUninterruptibly();
        r.run();
        semaphore2.release();
    }

    public void third(Runnable r) {
        semaphore2.acquireUninterruptibly();
        r.run();
    }
}
