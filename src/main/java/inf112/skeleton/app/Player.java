package inf112.skeleton.app;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;


public class Player implements KeyListener {
    private boolean[] flagsWhichHasBeenVisited;
    private Robot robot;
    private ArrayList<MovementCard> theCardsToChooseYourProgramFrom;


    public Player(int numberOfFlags, Robot robot) {
        this.flagsWhichHasBeenVisited = new boolean[numberOfFlags];
        this.robot = robot;
        theCardsToChooseYourProgramFrom = new ArrayList<>();
    }

    /**
     * Gives a movement card to the player
     *
     * @param card movement card to be delt to the player
     */
    public void giveMovementCardsToThePlayer(MovementCard card) {
        this.theCardsToChooseYourProgramFrom.add(card);
    }

    /**
     * gives movement cards for the player to choose from and returns a prioritized list to execute, by taking input from player
     *
     * @return prioritized list of movement cars to be executed
     */
    public ArrayList<MovementCard> theMovementCardsThePlayerChose() {
        Scanner sc = new Scanner(System.in);

        // Informasjon til bruker
        System.out.println("Choose your movement cards by typing the number of the card you would like to use first, then press enter\nYour robot have "
                + this.robot.getMemoryCapacity() + " slots in its memory");

        // Printer ut alle kortene først
        for (int i = 0; i < theCardsToChooseYourProgramFrom.size(); i++) {
            System.out.println("\nCard number " + (i + 1) + "\n" + theCardsToChooseYourProgramFrom.get(i).toString());
        }

        // Listen vi legger inn kortene som blir valgt av bruker at skal kjøre
        ArrayList<MovementCard> programForRobotToExecute = new ArrayList<>();


        int counter = 0;
        do {
            // Input from user, and add it to the list of chosen cards
            if (sc.hasNextInt()) {
                int number = sc.nextInt();

                // Sjekker ikke om du har valgt et kort to ganger, da kaster den exception
                if (number <= 0 || number > this.robot.getMemoryCapacity()) {
                    System.out.println("Illegal input: " + number);
                    continue;
                } else {
                    programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(number - 1));
                    theCardsToChooseYourProgramFrom.remove(number - 1);
                    counter++;
                }
            }

            // Printing out the choices the player has done this far
            System.out.println("Your choices so far:");
            for (MovementCard movementCard : programForRobotToExecute) {
                System.out.println(movementCard.toString());
            }
            System.out.print("\nYou have " + (this.robot.getMemoryCapacity() - counter) + " cards left\n");
        } while (counter < this.robot.getMemoryCapacity());


        return programForRobotToExecute;
    }

    /**
     * @return int memorycapasity for this player
     */
    public int memoryCapacityForThisPlayer() {
        return this.robot.getMemoryCapacity();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    public Directions canGO;

    public void keyPressed(KeyEvent key) {

        if (key.getKeyChar() == KeyEvent.VK_W) {
            canGO = Directions.UP;

        } else if (key.getKeyChar() == KeyEvent.VK_S) {
            canGO = Directions.DOWN;

        } else if (key.getKeyChar() == KeyEvent.VK_D) {
            canGO = Directions.RIGHT;

        } else if (key.getKeyChar() == KeyEvent.VK_A) {
            canGO = Directions.LEFT;

        }

    }

    @Override
    public void keyReleased(KeyEvent key) {
        if (key.getKeyChar() == KeyEvent.CHAR_UNDEFINED) {
            canGO = null;
        }

    }


    public boolean noticeWalls(Directions dir, Map map) {
        //Map map = MapGUI.map;


        if (dir == Directions.UP) {
            if (map.getBoardObject(new Position(getX(), getY() + 1)) instanceof Wall) {
            }
            return false;

        } else if (dir == Directions.DOWN) {
            if (map.getBoardObject(new Position(getX(), getY() - 1)) instanceof Wall) {

            }
            return false;
        } else if (dir == Directions.RIGHT) {
            if (map.getBoardObject(new Position(getX() + 1, getY())) instanceof Wall) {


            }
            return false;
        } else if (dir == Directions.LEFT) {
            return !(map.getBoardObject(new Position(getX() - 1, getY())) instanceof Wall);
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


