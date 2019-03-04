package inf112.skeleton.app;

public class MovementCard {
    private Directions direction;
    private int numberOfSteps;
    private int priority;

    public MovementCard (Directions direction, int numberOfSteps, int priority) {
        this.direction = direction;
        this.numberOfSteps = numberOfSteps;
        this.priority = priority;
    }

    @Override
    public String toString() {
        String stringToPrint = ("Direction: " + direction + " Number of steps: " + numberOfSteps + " Priority" + priority);
        return stringToPrint;
    }
}
