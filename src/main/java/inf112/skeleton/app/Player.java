package inf112.skeleton.app;

import java.util.ArrayList;

public class Player {
    private ArrayList<MovementCard> theProgramForTheRobotToExecute;
    private boolean[] flagsWhichHasBeenVisited;
    private Robot robot;


    public Player (int numberOfFlags, Robot robot) {
        this.flagsWhichHasBeenVisited = new boolean[numberOfFlags];
        this.theProgramForTheRobotToExecute = new ArrayList<>();
        this.robot = robot;
    }
}
