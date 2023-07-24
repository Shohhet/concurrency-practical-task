public class Worker implements Runnable {
    private final String outputString;

    public Worker(String otputString) {
        this.outputString = otputString;
    }
    @Override
    public void run() {
        System.out.print(outputString);
    }
}
