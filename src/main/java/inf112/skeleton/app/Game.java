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
            MovementCardDeck.dealOutMovementCards(players);

            //playing movement cards from players
            for (int j = 0; j < 5; j++) {//the max number for this for loop chooses how many movementcards is supposed to be played
                for (int i = 0; i < players.size(); i++) {
                    System.out.println("EOAFJOAEF");
                    playMovementCard((MovementCard) listOfPrioritizedListsOfMovementCardsFromPlayers.get(i).get(j), players.get(i));
                    mapGUI.updateMap(map);
                }
            }

            //end of round

        }

    }

    /**
     * executing a movement card by checking the position/s to move to, if available the robot gets moved
     *
     * @param movCard the movement card to be executed
     * @param player  the player that should be moved
     */
    public static void playMovementCard(MovementCard movCard, Player player) {
        Position currentPos = player.getRobot().getPosition();
        Position newPos = new Position(1000, 1000);
        switch (movCard.getDirection()) {
            case NODIRECTION: //moving forward
                try {
                    newPos = movingForward(movCard, player, currentPos, newPos);
                } catch (IllegalArgumentException e) {
                    System.out.println("A robot has fallen1"); //robot fell outside map, should be returned to backup position
                }
                break;
            case DOWN://moving backward or turning 180degrees
                try {
                    newPos = movingBackwardOrTurning180Degrees(movCard, player, currentPos, newPos);
                } catch (IllegalArgumentException e) {
                    System.out.println("A robot has fallen2");    //robot fell outside map, should be returned to backup position
                }
                break;
            case LEFT: //turning left
                Directions newDirection1 = turningLeft(player.getRobot().getDirection());
                player.getRobot().setDirection(newDirection1);
                break;
            default: //turning right
                Directions newDirection2 = turningRight(player.getRobot().getDirection());
                player.getRobot().setDirection(newDirection2);
                break;
        }

        String result = legalPosition(newPos);
        switch (result) {
            /*case "robot":
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
                break;*/
            case "dead":
                map.moveRobot(player.getRobot(), player.getRobot().getBackUpPosition());
                player.getRobot().setPositionToBackUp();
                return;
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
        } else if (map.getBoardObject(position) instanceof Wall) {
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


    /**
     * Moving a robot forward,
     *
     * @param movCard    movement card for knowing how many steps
     * @param player     player to move
     * @param currentPos current position of robot
     * @param newPos     new position where the robot is going next step
     * @return the final new position, ex the third position if the movCard said 3 steps
     * @throws IllegalArgumentException
     */
    private static Position movingForward(MovementCard movCard, Player player, Position currentPos, Position newPos) throws IllegalArgumentException {
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
        return newPos;
    }

    /**
     * Moving a robot backward or turns it 180 degrees depends on movCard
     *
     * @param movCard    movement card for knowing if to turn or move
     * @param player     player to move
     * @param currentPos current position of robot
     * @param newPos     new position where the robot is going next step
     * @return the final new position
     * @throws IllegalArgumentException
     */
    private static Position movingBackwardOrTurning180Degrees(MovementCard movCard, Player player, Position currentPos, Position newPos) throws IllegalArgumentException {
        if (movCard.getNumberOfSteps() == 1) {
            newPos = new Position(currentPos.getX(), (currentPos.getY() - 1));
            //should the robot also turn 180 degrees when moving backward?
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
        return newPos;
    }

    /**
     * calculating the new direction for a robot given a direction it returns the direction left for the current direction
     *
     * @param direction current direction
     * @return direction left from current
     * @throws IllegalArgumentException
     */
    private static Directions turningLeft(Directions direction) throws IllegalArgumentException {
        switch (direction) {
            case UP:
                return Directions.LEFT;
            case RIGHT:
                return Directions.UP;
            case LEFT:
                return Directions.DOWN;
            default:
                return Directions.RIGHT;
        }
    }

    /**
     * calculating the new direction for a robot given a direction it returns the direction right for the current direction
     *
     * @param direction current direction
     * @return direction right from current
     * @throws IllegalArgumentException
     */
    private static Directions turningRight(Directions direction) throws IllegalArgumentException {
        switch (direction) {
            case UP:
                return Directions.RIGHT;
            case RIGHT:
                return Directions.DOWN;
            case LEFT:
                return Directions.UP;
            default:
                return Directions.LEFT;
        }
    }

}