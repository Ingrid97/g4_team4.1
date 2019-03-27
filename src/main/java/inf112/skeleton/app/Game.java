package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.util.ArrayList;

public class Game {
    private static ArrayList<Player> players;
    private static boolean gameOver;
    private static Map map;


    public static void playGame() {
        // Lager alle kortene
        //leser inn map fra fil
        players = new ArrayList<>();
        map = new Map(10, 10);
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
        MovementCardDeck.setUpTheFullDeckOfCards();
        MovementCardDeck.dealOutMovementCards(players);
        while (gameOver) {
            //getting movement cards from player/s
            ArrayList<ArrayList> listOfPrioritizedListsOfMovementCardsFromPlayers = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                ArrayList<MovementCard> movementCardsToBeExecuted;
                System.out.println("Player " + (i + 1) + " choose your cards!");
                movementCardsToBeExecuted = players.get(i).theMovementCardsThePlayerChose();
                listOfPrioritizedListsOfMovementCardsFromPlayers.add(movementCardsToBeExecuted);
            }
            MovementCardDeck.dealOutMovementCards();

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

}