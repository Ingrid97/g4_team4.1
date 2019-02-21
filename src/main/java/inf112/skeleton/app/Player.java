package inf112.skeleton.app;

import java.util.ArrayList;

public class Player {
    private ArrayList<MovementCard> theProgramForTheRobotToExecute;
    private boolean[] flagsWhichHasBeenVisited;
    private Robot robot;
    private ArrayList<MovementCard> theCardsToChooseYourProgramFrom;


    public Player (int numberOfFlags, Robot robot) {
        this.flagsWhichHasBeenVisited = new boolean[numberOfFlags];
        this.theProgramForTheRobotToExecute = new ArrayList<>();
        this.robot = robot;
    }

    public void giveMovementCardsToThePlayer(MovementCard card) {
        this.theCardsToChooseYourProgramFrom.add(card);
    }

    // TODO! Choosing the cards as a player
    public ArrayList<MovementCard> theMovementCardsThePlayerChose () {

        /*
         * Metode for å vise & velge kortene du kan velge mellom
         * Returnere disse kortene i rekkefølge
         */

        return null;
    }

    public int memoryCapacityForThisPlayer() {
        return this.robot.getMemoryCapacity();
    }


}
