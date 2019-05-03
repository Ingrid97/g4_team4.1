package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import boardObjects.*;
import boardObjects.Void;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class RoboRally {
    private Map map;
    private MapGUI mapGUI;
    private ArrayList<Player> players;


    public RoboRally(String filename) {
        // Lager alle kortene
        //leser inn map fra fil
        this.players = new ArrayList<>();
        map = new Map(16, 12);
        map = map.makeMap(filename, players);
        if (map == null)
            System.exit(0);

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo Rally";
        cfg.width = 768;
        cfg.height = 1024;
        mapGUI = new MapGUI(map, players);
        new LwjglApplication(mapGUI, cfg);//instantiating MapGUI and updating the map it prints

        MovementCardDeck.setUpTheFullDeckOfCards();
        MovementCardDeck.dealOutMovementCards(players);
    }
    /**
     * Initiates the game and runs continuosly while the game runs, in loops of rounds and phases of the game.
     */

    //TODO: Veldig rotetet I KNOW!! men eg jobber med det!
    public void playGame() {

        int choice = makeGameChoice();

        if (choice == 1)
            for (int i = 1; i < players.size(); i++)
                players.get(i).setAI();


        playTheGame();

    }


    public void playTheGame() {
        boolean gameOver = false;

        while (!gameOver) {

            //part 1
            //getting movement cards from player/s making array List of prioritized listing of our movement cards from player class
            ArrayList<ArrayList> prioritizedMovementCards = chooseThePlayingCards();

            //part 2
            //playing movement cards from players'
            playingMovementCards(prioritizedMovementCards);

            System.out.println("endGame");
            //part 3
            //end of round
            gameOver = endGame(gameOver);
        }
    }


    public ArrayList<ArrayList> chooseThePlayingCards() {
        ArrayList<ArrayList> prioritizedMovementCards = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).getRobot().isAlive()) {
                players.remove(i);
                continue;
            }
            ArrayList<MovementCard> movementCardsToBeExecuted;
            if (!players.get(i).getRobot().getPowerDown()) {
                System.out.println("Player " + (i + 1) + " choose your cards!");
                movementCardsToBeExecuted = players.get(i).theMovementCardsThePlayerChose();
                prioritizedMovementCards.add(movementCardsToBeExecuted);
            }
        }
        MovementCardDeck.dealOutMovementCards(players);
        return prioritizedMovementCards;
    }

    public void playingMovementCards(ArrayList<ArrayList> prioritizedMovementCards) {

        boolean[] playersWhosDead = new boolean[players.size()];
        for (int j = 0; j < 5; j++) {//the max number for this for loop chooses how many movementcards is supposed to be played
            //between phases
            for (int i = 0; i < players.size(); i++) {
                if (!players.get(i).getRobot().getPowerDown()) {
                    if (playersWhosDead[i]) {
                        continue;
                    }
                    System.out.println("position: x: " + players.get(i).getRobot().getX() + " y: " + players.get(i).getRobot().getY());
                    //System.out.println("1: \n" + "i: " + i + " length: " + prioritizedMovementCards.size());
                    //System.out.println("j: " + j + " length: " + prioritizedMovementCards.get(i).size());
                    //System.out.println("i: " + i + " length: " + players.size());
                    boolean isRobotAlive = playMovementCard((MovementCard) prioritizedMovementCards.get(i).get(j), players.get(i));
                    if (!isRobotAlive) {
                        playersWhosDead[i] = true;
                    }
                }
            }
            robotLasersFire();
        }
    }

    public boolean endGame(boolean gameOver) {
        for (Player player : players) {
            if (player.getRobot().getPowerDown()) {
                player.powerDown();
                player.getRobot().finishPowerdown();
            } else {
                Position pos = player.getRobot().getPosition();
                ArrayList list = map.getBoardObjects(pos);

                for (Object boardObject : list) {

                    // Sjekker om det er et flag i posisjonen roboten står
                    if (boardObject instanceof Flag) {
                        player.getRobot().dropBackUpAtCurrentPosition(); //oppdaterer backup position uansett hvilket flag den står på
                        if (updateFlag(player, (Flag) boardObject)) {
                            gameOver = true;
                            System.out.println("WINNEEEER DING DING DING!");
                        }
                    }
                }
                System.out.println(player.getName() + ": Do you want to take a PowerDown? Yes/No ");
                boolean playerPowerDown = player.choosePowerdown();

                if (playerPowerDown) {
                    player.getRobot().takePowerdown();
                }
            }
        }
        return gameOver;
    }

    /**
     * Make the player choose the playing methode
     *
     * @return methode
     */

    public int makeGameChoice() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Hi and welcome to RoboRally!");
        System.out.println();
        System.out.println("Before we start please choose one of the following:");
        System.out.println("1: Play against AI's");
        System.out.println("2: Play against friends on the same computer");
        int c = scn.nextInt();

        while (c < 1 && c > 2) {
            System.out.println("The number you selected is not a choice. Pleas try again.");
            c = scn.nextInt();
        }
        return c;
    }

    /**
     * Method for the robots lasers to fire and deal damage to other robots
     * This happens between phases
     */
    public void robotLasersFire() {
        for (Player player : this.players) {
            Position pos = player.getRobot().getPosition();
            boolean hitSomeone = false;
            while (true) {
                try {
                    pos = CalculatePosition.movingForward(pos, player.getRobot().getDirection());
                    if (!this.map.isValidPosition(pos)) {
                        break;
                    }
                } catch (IllegalArgumentException e) {
                    break;
                }
                ArrayList boardObjects = this.map.getBoardObjects(pos);
                if (boardObjects != null) {
                    for (Object boardObject : boardObjects) {
                        if (boardObject instanceof Robot) {
                            ((Robot) boardObject).takeDamage(1);
                            if (!((Robot) boardObject).isAlive()) {
                                System.out.println(player.getName() + " killed another robot!");
                            } else {
                                System.out.println(player.getName() + " SHOT ANOTHER ROBOT!");
                            }
                            hitSomeone = true;
                            break;
                        }
                    }
                }
                if (hitSomeone) break;
            }
        }
    }


    /**
     * method for updating the number of flags a player have touched and checks if the player has won
     *
     * @param player player to update
     * @param flag the flag the player reached
     * @return returns true if someone win
     */
    public boolean updateFlag(Player player, Flag flag) {
        boolean[] temp = player.getFlagsVisited();
        int identifier = flag.identifier;
        System.out.println("A player reached flag " + identifier);
        if (identifier == 0) {
            player.setFlagsVisitedTrue(identifier);
            return false;
        }
        if (temp[0]) {
            if (identifier == 1) {
                player.setFlagsVisitedTrue(identifier);
                return false;
            }
        }
        if (temp[0]) {
            if (temp[1]) {
                player.setFlagsVisitedTrue(identifier);
                return true;
            }
        }
        return false;
    }

    /**
     * executing a movement card by checking the position/s to move to, if available the robot gets moved
     *
     * @param movCard the movement card to be executed
     * @param player  the player that should be moved
     * @return false if robot died during execution of movement card
     */
    public boolean playMovementCard(MovementCard movCard, Player player) {
        Position currentPos = player.getRobot().getPosition();
        Position newPos = new Position(1000, 1000);
        switch (movCard.getDirection()) {
            case NODIRECTION: //moving forward
                try {
                    for (int i = 0; i < movCard.getNumberOfSteps(); i++) {
                        Directions direction = player.getRobot().getDirection();
                        newPos = CalculatePosition.movingForward(currentPos, direction);
                        String movingForwardResult = legalPosition(newPos);
                        if (movingForwardResult.equals("dead")) {
                            map.moveRobot(player.getRobot(), player.getRobot().getBackUpPosition());
                            player.getRobot().setPositionToBackUp();
                            return false;
                        } else if (movingForwardResult.equals("robot")) {
                            if (newPos.equals(collidingWithAnotherRobot(newPos, player))) {
                                currentPos = newPos;
                                moveTheRobotAndUpdateMapGUI(player, newPos);
                            } else {
                                newPos = currentPos;
                                System.out.println(player.getName() + " crashed with another robot!");
                                break;
                            }
                        } else {
                            moveTheRobotAndUpdateMapGUI(player, newPos);
                            currentPos = newPos;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    map.moveRobot(player.getRobot(), player.getRobot().getBackUpPosition());
                    player.getRobot().setPositionToBackUp(); //robot fell outside map, should be returned to backup position
                }
                break;
            case DOWN://moving backward or turning 180degrees
                try {
                    newPos = CalculatePosition.Uturn(movCard, player);
                } catch (IllegalArgumentException e) {
                    map.moveRobot(player.getRobot(), player.getRobot().getBackUpPosition());
                    player.getRobot().setPositionToBackUp();    //robot fell outside map, should be returned to backup position
                }
                break;
            case LEFT: //turning left
                newPos = currentPos;
                Directions newDirection1 = CalculatePosition.turningLeft(player.getRobot().getDirection());
                player.getRobot().setDirection(newDirection1);
                break;
            default: //turning right
                newPos = currentPos;
                Directions newDirection2 = CalculatePosition.turningRight(player.getRobot().getDirection());
                player.getRobot().setDirection(newDirection2);
                break;
        }
        Position result = playingBoardElements(newPos, player);
        if (result == null) {
            return false;
        } else if (result.getX() == 1000) {
            moveTheRobotAndUpdateMapGUI(player, currentPos);
        } else {
            moveTheRobotAndUpdateMapGUI(player, result);
        }
        return true;
    }

    /**
     * Methodd for handling a collision between robots
     *
     * @param position The position a player is intending to move to
     * @param player   the player who is intending to move
     * @return returns the position to move to(A position 1000, 1000 if there is a collision)
     */
    private Position collidingWithAnotherRobot(Position position, Player player) {
        ArrayList<IBoardObject> arrayList = map.getBoardObjects(position);
        for (IBoardObject boardObject : arrayList) {
            if (boardObject instanceof Robot) {
                if (boardObject != (player.getRobot())) {
                    return new Position(1000, 1000);
                } else {
                    return position;
                }
            }
        }
        return position;
    }

    /**
     * Method for the different board elements to play
     * @param newPos the position a player is moving to
     * @param player the player who is moving
     * @return the position the player ended up in(plus damage, rotation etc.)
     */
    private Position playingBoardElements(Position newPos, Player player) {
        String result = legalPosition(newPos);
        switch (result) {
            case "robot":
                return collidingWithAnotherRobot(newPos, player);

            case "laser":
                // The laser deals 1 damage to the robots
                player.getRobot().takeDamage(1);
                System.out.println("Robot \nhp: " + player.getRobot().getHealthPoints() + "\nmemCap: " + player.getRobot().getMemoryCapacity());
                return player.getRobot().getPosition();

            case "conveyor_belt":
                if (map.getBoardObject(newPos) instanceof Conveyor_belt) {
                    Directions direction = ((Conveyor_belt) map.getBoardObject(newPos)).getDirection();
                    if (((Conveyor_belt) map.getBoardObject(newPos)).isBlueBelt) {

                        Position p = CalculatePosition.movingForward(newPos, direction);
                        String movingForwardResult = legalPosition(p);
                        if (movingForwardResult.equals("dead")) {
                            Position deadPos = player.getRobot().getBackUpPosition();
                            player.getRobot().setPositionToBackUp();
                            return deadPos;
                        }
                        p = collidingWithAnotherRobot(p, player);
                        moveTheRobotAndUpdateMapGUI(player, p);

                        p = CalculatePosition.movingForward(p, direction);
                        movingForwardResult = legalPosition(p);
                        if (movingForwardResult.equals("dead")) {
                            Position deadPos = player.getRobot().getBackUpPosition();
                            player.getRobot().setPositionToBackUp();
                            return deadPos;
                        }
                        return collidingWithAnotherRobot(p, player);
                    }
                    try {
                        Position p = CalculatePosition.movingForward(newPos, direction);
                        String movingForwardResult = legalPosition(p);
                        if (movingForwardResult.equals("dead")) {
                            Position deadPos = player.getRobot().getBackUpPosition();
                            player.getRobot().setPositionToBackUp();
                            return deadPos;
                        }
                        return collidingWithAnotherRobot(CalculatePosition.movingForward(newPos, direction), player);
                    } catch (IllegalArgumentException e) {
                        map.moveRobot(player.getRobot(), player.getRobot().getBackUpPosition());
                        player.getRobot().setPositionToBackUp();
                        return null;
                    }
                }
                return newPos;
            case "rotating_belt":
                player.getRobot().setDirection(CalculatePosition.turningLeft(player.getRobot().getDirection()));
                return newPos;
            case "dead":
                map.moveRobot(player.getRobot(), player.getRobot().getBackUpPosition());
                player.getRobot().setPositionToBackUp();
                return null;
            default://default is when none of the other case occurs, then it moves the robot to the actual position
                return newPos;
        }
    }

    /**
     * moving the robot both in map and updating the robots own position
     *
     * @param player player to move
     * @param newPos position to move to
     */
    private void moveTheRobotAndUpdateMapGUI(Player player, Position newPos) {
        if (newPos.getY() == 1000 || newPos.getX() == 1000) {
            return;
        }
        if (player.getRobot().isAlive()) {
            map.moveRobot(player.getRobot(), newPos);
        } else {
            // TODO! Hva gjør vi med robotene når de ikke lenger er i live?
        }
        player.getRobot().setPosition(newPos);
        mapGUI.updateMap(map);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * checking that given position is inside map, or not occupied by a wall or robot
     *
     * @param position position to check
     * @return name of object
     */
    public String legalPosition(Position position) {
        if (!map.isValidPosition(position)) {
            return "dead";
        }
        IBoardObject boardObject = map.getBoardObject(position);
        if (boardObject instanceof Robot) {
            return "robot";
        } else if (boardObject instanceof Wall) {
            return "wall";
        } else if (boardObject instanceof Laser) {
            return "laser";
        } else if (boardObject instanceof Rotating_belt) {
            return "rotating_belt";
        } else if (boardObject instanceof Conveyor_belt) {
            return "conveyor_belt";
        } else if (boardObject instanceof Void) {
            return "dead";
        } else {
            return "ok";
        }
    }

    /**
     * method for getting all the players
     * only inteded to be used for testing
     * @return arraylist of all the player in the game
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }
}
