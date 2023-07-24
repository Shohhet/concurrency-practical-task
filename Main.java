import Foos.*;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Foo foo = new LockFoo();
        Runnable first = () -> foo.first(new Worker("first"));
        Runnable second = () -> foo.second(new Worker("second"));
        Runnable third = () -> foo.third(new Worker("third"));

        CompletableFuture.runAsync(second);
        CompletableFuture.runAsync(third);
        CompletableFuture.runAsync(first);

        try {
            TimeUnit.MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}