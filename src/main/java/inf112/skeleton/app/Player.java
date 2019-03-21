package inf112.skeleton.app;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class Player implements KeyListener {
    private boolean[] flagsWhichHasBeenVisited;
    private Robot robot;
    private ArrayList<MovementCard> theCardsToChooseYourProgramFrom;
    private ArrayList<MovementCard> programForRobotToExecute = new ArrayList<>();



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
        System.out.println("Choose your movement cards by typing the number of the card you would like to use first, then press enter");

        // Printer ut alle kortene først
        for (int i = 0; i < this.robot.getMemoryCapacity(); i++) {
            System.out.println("\nCard number " + (i + 1) + "\n" + theCardsToChooseYourProgramFrom.get(i).toString());
        }


        int counter = 0;

        // Gjør at man bare velger antallet kort man kan ut i fra skaden roboten har tatt
        int numberOfSlotsInRegister;
        if (this.robot.getMemoryCapacity() > 4) {
            numberOfSlotsInRegister = 5;
            this.programForRobotToExecute.clear();
        } else {
            // Nå må MemoryCapacity være 4 eller mindre
            numberOfSlotsInRegister = this.robot.getMemoryCapacity();
            // Skal slette alt utenom det som er over MemoryCapacity
            for (int i = 0; i < this.robot.getMemoryCapacity(); i++) {
                this.programForRobotToExecute.remove(i);
            }
        }
        HashSet<Integer> list = new HashSet<>(); // Et HashSet for å sjekke at det ikke velges samme kort flere ganger


        do {
            // Input from user, and add it to the list of chosen cards
            if (sc.hasNextInt()) {
                int number = sc.nextInt();


                if (number <= 0 || number > this.robot.getMemoryCapacity()) {
                    System.out.println("Illegal input: " + number);
                    continue;
                } else if (list.contains(number)) {
                    System.out.println("You have already chosen this card!");
                    continue;
                } else {
                    programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(number - 1));
                    counter++;
                    list.add(number);
                }
            }

            // Printing out the choices the player has done this far
            System.out.println("Your choices so far:");
            for (MovementCard movementCard : programForRobotToExecute) {
                System.out.println(movementCard.toString());
            }
            System.out.print("\nYou have " + (numberOfSlotsInRegister - counter) + " cards left to choose\n");
        } while (counter < numberOfSlotsInRegister);
        theCardsToChooseYourProgramFrom.clear();


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


