package inf112.skeleton.app;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;


public class Player implements KeyListener {
    private ArrayList<MovementCard> theProgramForTheRobotToExecute;
    private boolean[] flagsWhichHasBeenVisited;
    private Robot robot;
    private ArrayList<MovementCard> theCardsToChooseYourProgramFrom;




    public Player(int numberOfFlags, Robot robot) {
        this.flagsWhichHasBeenVisited = new boolean[numberOfFlags];
        this.theProgramForTheRobotToExecute = new ArrayList<>();
        this.robot = robot;
        theCardsToChooseYourProgramFrom = new ArrayList<>();
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
        System.out.println("write down your program with number and no spaces, to be executed from left to right, and press enter");
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> arr = new ArrayList<Integer>();
        boolean notDone = false;
        System.out.println("choose your movementcards by typing the number of the card you would like to use first, then press enter ");
        System.out.println("When you are finished write a number greater then 100");
        while (!notDone) {
            if (sc.hasNextInt()) {
                int number = sc.nextInt();
                if (number > 100) {
                    notDone = true;
                } else {
                    programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(number - 1));
                }
            }

            for (int i = 0; i < programForRobotToExecute.size(); i++) {
                System.out.println("your choises so far:");
                System.out.println(programForRobotToExecute.get(i).toString());
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

    public Robot getRobot() {
        return robot;
    }

}


