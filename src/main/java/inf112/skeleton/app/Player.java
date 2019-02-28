package inf112.skeleton.app;

import java.util.ArrayList;

public class Player {
    private ArrayList<MovementCard> theProgramForTheRobotToExecute;
    private boolean[] flagsWhichHasBeenVisited;
    private Robot robot;
    private ArrayList<MovementCard> theCardsToChooseYourProgramFrom;
    private Position positionOfRobot;
    private Directions direction;


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
    

   //Have to import keyBoard function.

    protected Position askedToGo;
    public void keyPressed(Directions dir) {
        if (key == Directions.LEFT) {
            askedToGo = Directions.LEFT;
        } else if(key == Directions.RIGHT){
            askedToGo = Directions.RIGHT;
        } else if(key == KeyCode.UP){
            askedToGo = Directions.UP;
        } else if(key == KeyCode.DOWN){
            askedToGo = Directions.DOWN;
        }
    }

    public void noticeWalls(){


        //check if the Robot can walk at a given position or if there is an obstacle there
        Position robotPos = this.robot.moveDirection(this.direction);
        boolean robotCanGo = true;


            if(askedToGo != null && this.robot.moveDirection(this.askedToGo);
            IBoardObject obj =  this.robot.get(robotPos.moveDirection(this.askedToGo));
            if(obj instanceof Wall){
                robotCanGo = false;

            }
        }


}
