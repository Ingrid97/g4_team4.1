package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private static ArrayList<Player> players;
    private static ArrayList<MovementCard> theFullDeckOfAllMovementCards;
    private static boolean gameOver;
    public static int[][] robotPositions;
    public static int numberOfRobots;
    private static int numberOfPlayers;
    private static Map map;


    public static void playGame() {
        // Lager alle kortene
        setUpTheFullDeckOfCards();

        players = new ArrayList<>();

        //leser inn map fra fil
        robotPositions = new int[4][2];
        numberOfRobots = 0;
        numberOfPlayers = 0;
        players = new ArrayList<>();
        Map map = new Map(10, 10);
        map = Map.makeMap("testMap1.txt", players);
        if (map == null)
            System.exit(0);

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo Rally";
        cfg.width = 640;
        cfg.height = 940;
        MapGUI mapGUI = new MapGUI(map, players);
        new LwjglApplication(mapGUI, cfg);//instantiating MapGUI and updating the map it prints

        gameOver = true;
        setUpTheFullDeckOfCards();
        dealOutMovementCards();
        while (gameOver) {
            //getting movement cards from player/s
            ArrayList<ArrayList> listOfPrioritizedListsOfMovementCardsFromPlayers = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                ArrayList<MovementCard> movementCardsToBeExecuted;
                System.out.println("Player " + (i + 1) + " choose your cards!");
                movementCardsToBeExecuted = players.get(i).theMovementCardsThePlayerChose();
                listOfPrioritizedListsOfMovementCardsFromPlayers.add(movementCardsToBeExecuted);
            }
            dealOutMovementCards();

            //playing movement cards from players
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < players.size(); i++) {
                    playMovementCard((MovementCard) listOfPrioritizedListsOfMovementCardsFromPlayers.get(i).get(j), players.get(i));
                    mapGUI.updateMap(map);
                }
            }
        }


    }

    /**
     * ececuting a movement card by checking the position/s to move to, if available the robot gets moved
     *
     * @param movCard the movement card to be executed
     * @param player  the player that should be moved
     */
    public static void playMovementCard(MovementCard movCard, Player player) {
        Position currentPos = player.getRobot().getPosition();
        Position newPos = new Position(1000, 1000);
        if (movCard.getDirection() == Directions.NODIRECTION) {//moving forward
            try {
                for (int i = 0; i < movCard.getNumberOfSteps(); i++) {
                    Directions direction = player.getRobot().getDirection();
                    switch (direction) {
                        case UP:
                            newPos = new Position(currentPos.getX(), (currentPos.getY() - 1));
                            break;
                        case RIGHT:
                            newPos = new Position((currentPos.getX() + 1), currentPos.getY());
                            break;
                        case LEFT:
                            newPos = new Position((currentPos.getX() - 1), currentPos.getY());
                            break;
                        case DOWN:
                            newPos = new Position(currentPos.getX(), (currentPos.getY() + 1));
                            break;
                    }
                    if (!legalPosition(newPos).equals("dead")) break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("A robot has fallen"); //robot fell outside map, should be returned to backup position
            }
        } else if (movCard.getDirection() == Directions.DOWN) {//moving backward or turning 180 degrees
            try {
                if (movCard.getNumberOfSteps() == 1) {
                    newPos = new Position(currentPos.getX(), (currentPos.getY() - 1));
                } else {
                    Directions direction = player.getRobot().getDirection();
                    newPos = currentPos;
                    switch (direction) {
                        case UP:
                            player.getRobot().setDirection(Directions.DOWN);
                            break;
                        case RIGHT:
                            player.getRobot().setDirection(Directions.LEFT);
                            break;
                        case LEFT:
                            player.getRobot().setDirection(Directions.RIGHT);
                            break;
                        case DOWN:
                            player.getRobot().setDirection(Directions.UP);
                            break;
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("A robot has fallen");//robot fell outside map, should be returned to backup position
            }
        } else if (movCard.getDirection() == Directions.LEFT) {//turning left, no movement
            Directions direction = player.getRobot().getDirection();
            newPos = currentPos;
            switch (direction) {
                case UP:
                    player.getRobot().setDirection(Directions.LEFT);
                    break;
                case RIGHT:
                    player.getRobot().setDirection(Directions.UP);
                    break;
                case LEFT:
                    player.getRobot().setDirection(Directions.DOWN);
                    break;
                case DOWN:
                    player.getRobot().setDirection(Directions.RIGHT);
                    break;
            }
        } else {//turning right, no movement
            Directions direction = player.getRobot().getDirection();
            newPos = currentPos;
            switch (direction) {
                case UP:
                    player.getRobot().setDirection(Directions.RIGHT);
                    break;
                case RIGHT:
                    player.getRobot().setDirection(Directions.DOWN);
                    break;
                case LEFT:
                    player.getRobot().setDirection(Directions.UP);
                    break;
                case DOWN:
                    player.getRobot().setDirection(Directions.LEFT);
                    break;
            }
        }

        String result = legalPosition(newPos);
        switch (result) {
            case "robot":
                //what do to if a robot collides with another robot
                break;
            case "wall":
                //what do to if a robot collides with a wall
                break;
            case "laser":
                //what do to if a robot collides with a laser
                break;
            case "wrench_hammer":
                //what do to if a robot collides with a wrench:hammer
                break;
            case "rotating_belt":
                //what do to if a robot collides with a rotating_belt
                break;
            case "void":
                //what do to if a robot collides with a void
                break;
            case "nothing":
                //what do to if a robot collides with a nothing
                break;
            default://default is when none of the other case occurs, then it moves the robot to the actual position
                map.moveRobot(player.getRobot(), newPos);
                player.getRobot().setPosition(newPos);
                break;
        }
    }

    /**
     * checking that given position is inside map, or not occupied by a wall or robot
     *
     * @param position
     * @return name of object
     */
    public static String legalPosition(Position position) {
        if (!map.isValidPosition(position)) {
            return "dead";
        } else if (map.getBoardObject(position) instanceof Robot) {
            return "robot";
        } else if ((map.getBoardObject(position) instanceof Wall)) {
            return "wall";
        } else if (map.getBoardObject(position) instanceof Laser) {
            return "laser";
        } else if (map.getBoardObject(position) instanceof Wrench) {
            return "wrench";
        } else if (map.getBoardObject(position) instanceof Wrench_hammer) {
            return "wrench_hammer";
        } else if (map.getBoardObject(position) instanceof Rotating_belt) {
            return "rotating_belt";
        } else if (map.getBoardObject(position) instanceof Void) {
            return "void";
        } else if (map.getBoardObject(position) instanceof Nothing) {
            return "nothing";
        } else {
            return "ok";
        }
    }

    private static void setUpTheFullDeckOfCards() {
        theFullDeckOfAllMovementCards = new ArrayList<>();

        // Rotate cards
        // Left rotation, right rotation, forward movement 1
        int priorityForLeft = 80;
        int priorityForRight = 70;
        int priorityForMovementForward = 490;
        for (int i = 0; i < 18; i++) {
            theFullDeckOfAllMovementCards.add(new MovementCard(Directions.LEFT, 0, priorityForLeft));
            theFullDeckOfAllMovementCards.add(new MovementCard(Directions.RIGHT, 0, priorityForRight));
            theFullDeckOfAllMovementCards.add(new MovementCard(Directions.NODIRECTION, 1, priorityForMovementForward));
            priorityForLeft += 20;
            priorityForRight += 20;
            priorityForMovementForward += 20;
        }

        // Movement forward 2
        priorityForMovementForward = 670;
        for (int i = 0; i < 12; i++) {
            theFullDeckOfAllMovementCards.add(new MovementCard(Directions.NODIRECTION, 2, priorityForMovementForward));
            priorityForMovementForward += 20;
        }

        // Movement forward 3, movement backwards 1 & 180 turn
        priorityForMovementForward = 790;
        int priorityForMovementBackwards = 430;
        int priorityFor180Turn = 10;
        for (int i = 0; i < 6; i++) {
            theFullDeckOfAllMovementCards.add(new MovementCard(Directions.NODIRECTION, 3, priorityForMovementForward));
            theFullDeckOfAllMovementCards.add(new MovementCard(Directions.DOWN, 0, priorityFor180Turn));
            theFullDeckOfAllMovementCards.add(new MovementCard(Directions.DOWN, 1, priorityForMovementBackwards));
            priorityForMovementForward += 10;
            priorityFor180Turn += 10;
            priorityForMovementBackwards += 10;
        }
    }

    private static void dealOutMovementCards() {
        ArrayList<MovementCard> copy = new ArrayList<>(theFullDeckOfAllMovementCards);
        Collections.shuffle(copy);

        for (int i = 0; i < players.size(); i++) {

            for (int j = 0; j < players.get(i).memoryCapacityForThisPlayer(); j++) {
                players.get(i).giveMovementCardsToThePlayer(copy.get(j));
                copy.remove(j);
            }
        }
    }
}