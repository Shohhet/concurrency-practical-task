package Foos;

public class SynchronizedFoo implements Foo {
    private boolean firstDone;
    private boolean secondDone;

    public SynchronizedFoo() {
        firstDone = false;
        secondDone = false;
    }

    @Override
    public synchronized void first(Runnable r) {
        r.run();
        firstDone = true;
        notifyAll();
    }

    @Override
    public synchronized void second(Runnable r) {
        while(!firstDone) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        r.run();
        secondDone = true;
        notifyAll();
    }

    @Override
    public synchronized void third(Runnable r) {
        while (!secondDone) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        r.run();
    }
}
