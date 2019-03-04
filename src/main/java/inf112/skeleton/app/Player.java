package inf112.skeleton.app;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class Player implements KeyListener {
    private ArrayList<MovementCard> theProgramForTheRobotToExecute;
    private boolean[] flagsWhichHasBeenVisited;
    private Robot robot;
    private ArrayList<MovementCard> theCardsToChooseYourProgramFrom;




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
        for (int i = 0; i < theCardsToChooseYourProgramFrom.size(); i++) {
            System.out.println("Alt" + (i + 1));
            System.out.println(theCardsToChooseYourProgramFrom.get(i).toString());
        }
        ArrayList<MovementCard> programForRobotToExecute = new ArrayList<>();
        while (!Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(1 - 1));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(2 - 1));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(3 - 1));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
                programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(4 - 1));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
                programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(5 - 1));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
                programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(6 - 1));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
                programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(7 - 1));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
                programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(8 - 1));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
                programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(9 - 1));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
                programForRobotToExecute.remove(programForRobotToExecute.size());
            }
            for (int i = 0; i < theProgramForTheRobotToExecute.size(); i++) {
                System.out.println(theProgramForTheRobotToExecute.get(i).toString());
            }
        }

        return theProgramForTheRobotToExecute;
    }

    public int memoryCapacityForThisPlayer() {
        return this.robot.getMemoryCapacity();
    }





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
    public void keyReleased(KeyEvent key) {
        if(key.getKeyChar() == KeyEvent.CHAR_UNDEFINED){
            canGO = null;
        }

    }


    public boolean noticeWalls(Directions dir, Map map) {
        //Map map = MapGUI.map;


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
            return !(map.getBoardObject(getX() - 1, getY()) instanceof Wall);
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


