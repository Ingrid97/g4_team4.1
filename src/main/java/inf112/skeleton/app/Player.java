package inf112.skeleton.app;

import java.util.ArrayList;

import static org.lwjgl.input.Mouse.getY;
import static org.lwjgl.opengl.Display.getX;

public class Player {
    private ArrayList<MovementCard> theProgramForTheRobotToExecute;
    private boolean[] flagsWhichHasBeenVisited;
    private Robot robot;
    private ArrayList<MovementCard> theCardsToChooseYourProgramFrom;
    private Position positionOfRobot;
    private Directions direction;

    protected Position askedToGo;


    public Player(int numberOfFlags, Robot robot) {
        this.flagsWhichHasBeenVisited = new boolean[numberOfFlags];
        this.theProgramForTheRobotToExecute = new ArrayList<>();
        this.robot = robot;
    }

    public void giveMovementCardsToThePlayer(MovementCard card) {
        this.theCardsToChooseYourProgramFrom.add(card);
    }

    // TODO! Choosing the cards as a player
    public ArrayList<MovementCard> theMovementCardsThePlayerChose() {

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
/*
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
    } */

    public boolean noticeWalls(Directions dir) {
        Map map = MapGUI.map;


        if (dir == Directions.UP) {
            if (map.getBoardObject(getX(), getY() + 1) instanceof Wall) {
            }
            return false;

        } else if (dir == Directions.DOWN) {
            if (map.getBoardObject(getX(), getY() - 1) instanceof Wall) {

            }
            return false;
        } else if (dir == Directions.RIGHT) {
            if (map.getBoardObject(getX() + 1, getY()) instanceof Wall) {


            }
            return false;
        } else if (dir == Directions.LEFT) {
            if (map.getBoardObject(getX() - 1, getY()) instanceof Wall) {
                return false;
            }
        }



        return true;
    }
}


