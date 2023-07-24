package Foos;

import java.util.concurrent.Phaser;

public class PhaserFoo implements Foo{
    private final Phaser phaser;
    public PhaserFoo() {
        phaser = new Phaser(3);
    }

    @Override
    public void first(Runnable r) {
        r.run();
        phaser.arriveAndDeregister();
    }

    @Override
    public void second(Runnable r) {
        while(phaser.getPhase() != 1) {
            phaser.arriveAndAwaitAdvance();
        }
        r.run();
        phaser.arriveAndDeregister();
    }

    @Override
    public void third(Runnable r) {
        while (phaser.getPhase() != 2) {
            phaser.arriveAndAwaitAdvance();
        }
        r.run();


    }
}
