package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;


public class Player {
    private boolean[] flagsVisited; //variable that collects flags that has been visited in a boolean array
    private String name;
    private Robot robot;
    private ArrayList<MovementCard> theCardsToChooseYourProgramFrom;
    private ArrayList<MovementCard> programForRobotToExecute;



    public Player(int numberOfFlags, Robot robot) {
        this.flagsVisited = new boolean[numberOfFlags];
        this.robot = robot;
        this.theCardsToChooseYourProgramFrom = new ArrayList<>();
        programForRobotToExecute = new ArrayList<>();
        this.name = "";
    }

    /**
     * Gives a movement card to the player
     *
     * @param card movement card to be delt to the player
     *             This method gives the movementcards to the player
     */
    public void giveMovementCards(MovementCard card) {
        this.theCardsToChooseYourProgramFrom.add(card);
    }

    public ArrayList<MovementCard> theMovementCardsThePlayerChoseAI() {

        //TODO: AI random cards
        //skla ikke trenge å tenke på memCap

        Random rnd = new Random();
        boolean[] choosen = new boolean[theCardsToChooseYourProgramFrom.size()];
        int cards = 5;
        while (cards > 0) {
            int randomInt = rnd.nextInt(theCardsToChooseYourProgramFrom.size());
            if (!choosen[randomInt]) {
                programForRobotToExecute.add(theCardsToChooseYourProgramFrom.get(randomInt));
                cards--;
                choosen[randomInt] = true;
            }
        }

        return programForRobotToExecute;
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
            // TODO! Funker ikke helt, IndexOutOfBoundsException når memCap går ned
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
            } else {
                sc.next();
                continue;
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


    public int memoryCapacityForThisPlayer() {
        return this.robot.getMemoryCapacity();
    }


    public boolean[] getFlagsVisited() {
        return this.flagsVisited;
    }

    public void setFlagsVisitedTrue(int pos) {
        this.flagsVisited[pos] = true;
    }

    public String getName() {
        return this.name;
    }


    public void powerDown() {
        this.robot.setMaxMemCap();
    }

    public boolean choosePowerdown() {
        Scanner scn = new Scanner(System.in);
        String in = scn.nextLine().toLowerCase();
        return in.equals("yes");
    }


    public boolean choosePowerdownAI() {
        /*if(this.robot.getMemoryCapacity() <= 5){
            return true;
        }*/
        return false;
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

    public void setName(String name) {
        this.name = name;
    }


}


