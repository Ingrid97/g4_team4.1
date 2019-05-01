package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import boardObjects.Void;
import boardObjects.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RoboRally {
    private Map map;
    private MapGUI mapGUI;
    private ArrayList<Player> players;


    public RoboRally() {
        // Lager alle kortene
        //leser inn map fra fil
        this.players = new ArrayList<>();
        map = new Map(16, 16);
        map = map.makeMap("testMap1.txt", players);
        //map.printMap();
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
    public void playGame() {
        boolean gameOver = false;
        while (!gameOver) {
            //getting movement cards from player/s making array List of prioritized listing of our movement cards from player class
            ArrayList<ArrayList> PrioritizedMovementCards = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                if (!players.get(i).getRobot().isAlive()) {
                    players.remove(i);
                    continue;
                }
                ArrayList<MovementCard> movementCardsToBeExecuted;
                if(!players.get(i).getRobot().getPowerDown()){
                    System.out.println("Player " + (i + 1) + " choose your cards!");
                    movementCardsToBeExecuted = players.get(i).theMovementCardsThePlayerChose();
                    PrioritizedMovementCards.add(movementCardsToBeExecuted);
                }
            }
            MovementCardDeck.dealOutMovementCards(players);

            //playing movement cards from players'
            boolean[] playersWhosDead = new boolean[players.size()];
            for (int j = 0; j < 5; j++) {//the max number for this for loop chooses how many movementcards is supposed to be played
                //between phases
                robotLasersFire();
                for (int i = 0; i < players.size(); i++) {
                    if(!players.get(i).getRobot().getPowerDown()){
                        if (playersWhosDead[i]) {
                            continue;
                        }
                        System.out.println("position: x: " + players.get(i).getRobot().getX() + " y: " + players.get(i).getRobot().getY());
                        boolean isRobotAlive = playMovementCard((MovementCard) PrioritizedMovementCards.get(i).get(j), players.get(i));
                        if (!isRobotAlive) {
                            playersWhosDead[i] = true;
                        }
                    }
                }
            }

            //end of round
            for (Player player : players) {
                if(player.getRobot().getPowerDown()){
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

                    if(playerPowerDown){
                        player.getRobot().takePowerdown();
                    }
                }
            }
        }
    }

    /**
     * Method for the robots lasers to fire and deal damage to other robots
     * This happens between phases
     */
    private void robotLasersFire() {
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
                    for (int i = 0; i < boardObjects.size(); i++) {
                        if (boardObjects.get(i) instanceof Robot) {
                            ((Robot) boardObjects.get(i)).takeDamage(1);
                            if (!((Robot) boardObjects.get(i)).isAlive()) {
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

    // Litt usikker på om denne er helt riktig

    /**
     * Returns true if someone has won
     *
     * @param player
     * @param flag
     * @return
     */
    private boolean updateFlag(Player player, Flag flag) {
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
                        if (legalPosition(newPos).equals("dead")) {
                            map.moveRobot(player.getRobot(), player.getRobot().getBackUpPosition());
                            player.getRobot().setPositionToBackUp();
                            return false;
                        } else {
                            moveTheRobotAndUpdateMapGUI(player, newPos);
                            currentPos = newPos;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("A robot has fallen1"); //robot fell outside map, should be returned to backup position
                }
                break;
            case DOWN://moving backward or turning 180degrees
                try {
                    newPos = CalculatePosition.Uturn(movCard, player);
                } catch (IllegalArgumentException e) {
                    System.out.println("A robot has fallen2");    //robot fell outside map, should be returned to backup position
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
        } else {
            moveTheRobotAndUpdateMapGUI(player, result);
        }
        return true;
    }

    private Position playingBoardElements(Position newPos, Player player) {
        String result = legalPosition(newPos);
        System.out.println("RESULT: " + result);
        switch (result) {
//            case "robot":
//                //what do to if a robot collides with another robot
//                break;
//            case "wall":
//                //what do to if a robot collides with a wall
//                break;
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
                        moveTheRobotAndUpdateMapGUI(player, p);
                        return CalculatePosition.movingForward(p, direction);
                    }
                    try {

                        return CalculatePosition.movingForward(newPos, direction);
                    } catch (IllegalArgumentException e) {
                        map.moveRobot(player.getRobot(), player.getRobot().getBackUpPosition());
                        player.getRobot().setPositionToBackUp();
                        System.out.println("deadPosition: x: " + newPos.getX() + " y: " + newPos.getY());
                        System.out.println("ROBOT DEAD");
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
                System.out.println("deadPosition: x: " + newPos.getX() + " y: " + newPos.getY());
                System.out.println("ROBOT DEAD");
                return null;
            default://default is when none of the other case occurs, then it moves the robot to the actual position
                moveTheRobotAndUpdateMapGUI(player, newPos);
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
            System.out.println("ERROROROROR");
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
}
