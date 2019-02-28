package inf112.skeleton.app;

import org.graalvm.compiler.loop.InductionVariable;

import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;





public class Player implements KeyListener {
    private ArrayList<MovementCard> theProgramForTheRobotToExecute;
    private boolean[] flagsWhichHasBeenVisited;
    private Robot robot;
    private ArrayList<MovementCard> theCardsToChooseYourProgramFrom;
    private Position move;
    private Directions direction;



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


    @Override
    public void keyTyped(KeyEvent e) {

    }
    public Directions canGO;

    public void keyPressed(KeyEvent key) {

     if(key.getKeyChar() == KeyEvent.VK_W){
         canGO = Directions.UP;

     }
     else if(key.getKeyChar() == KeyEvent.VK_S){
         canGO = Directions.DOWN;

     }
     else if(key.getKeyChar() == KeyEvent.VK_D){
         canGO = Directions.RIGHT;

     }
     else if(key.getKeyChar() == KeyEvent.VK_A){
         canGO = Directions.LEFT;

     }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

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



    public int getX() {
        return robot.getX();
    }

    public int getY() {
        return robot.getY();
    }

}


