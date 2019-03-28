package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import boardObjects.*;
import boardObjects.Void;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RoboRally {
    private static Map map;
    private static MapGUI mapGUI;


    public static void playGame() {
        // Lager alle kortene
        //leser inn map fra fil
        ArrayList<Player> players = new ArrayList<>();
        map = new Map(10, 10);
        map = Map.makeMap("testMap1.txt", players);
        if (map == null)
            System.exit(0);



        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo Rally";
        cfg.width = 640;
        cfg.height = 940;
        mapGUI = new MapGUI(map, players);
        new LwjglApplication(mapGUI, cfg);//instantiating MapGUI and updating the map it prints

        boolean gameOver = false;
        MovementCardDeck.setUpTheFullDeckOfCards();
        MovementCardDeck.dealOutMovementCards(players);
        while (!gameOver) {
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
                    System.out.println("position: x: " + players.get(i).getRobot().getX() + " y: " + players.get(i).getRobot().getY());
                    playMovementCard((MovementCard) listOfPrioritizedListsOfMovementCardsFromPlayers.get(i).get(j), players.get(i));
                }
            }

            //end of round
            /* TODO! Sjekke om robot står på flag
             *
             */
            for (int i = 0; i < players.size(); i++) {
                Position pos = players.get(i).getRobot().getPosition();

                ArrayList list = map.getBoardObjects(pos);

                for (int j = 0; j < list.size(); j++) {

                    // Sjekker om det er et flag i posisjonen roboten står
                    if (list.get(j) instanceof Flag) {
                        if (updateFlag(players.get(i), (Flag) list.get(j))) gameOver = true;
                    }
                }

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
    private static boolean updateFlag(Player player, Flag flag) {
        boolean[] temp = player.getFlagsWhichHasBeenVisited();
        int identifier = flag.identifier;

        int i = 0;
        while (temp[i++] && i <= identifier) ;

        // Må senke verdien av i med 1 etter while-loopen
        i = i - 1;

        if (i == identifier) {
            player.setFlagsWhichHasBeenVisitedTrue(identifier);
            return identifier == 2;
        }
        return false;
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
                    for (int i = 0; i < movCard.getNumberOfSteps(); i++) {
                        newPos = movingForward(player, currentPos);
                        if (!legalPosition(newPos).equals("dead")) break;
                        moveTheRobotAndUpdateMapGUI(player, newPos);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("A robot has fallen1"); //robot fell outside map, should be returned to backup position
                }
                break;
            case DOWN://moving backward or turning 180degrees
                try {
                    newPos = backingUpOrTurning180Degrees(movCard, player, currentPos, newPos);
                } catch (IllegalArgumentException e) {
                    System.out.println("A robot has fallen2");    //robot fell outside map, should be returned to backup position
                }
                break;
            case LEFT: //turning left
                newPos = currentPos;
                Directions newDirection1 = turningLeft(player.getRobot().getDirection());
                player.getRobot().setDirection(newDirection1);
                break;
            default: //turning right
                newPos = currentPos;
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
                System.out.println("deadPosition: x: " + newPos.getX() + " y: " + newPos.getY() + "   Direction on MovCard: " + movCard.getDirection());
                System.out.println("ROBOT DEAD");
                return;
            default://default is when none of the other case occurs, then it moves the robot to the actual position
                moveTheRobotAndUpdateMapGUI(player, newPos);
                break;

        }
    }

    /**
     * moving the robot both in map and updating the robots own position
     *
     * @param player player to move
     * @param newPos position to move to
     */
    private static void moveTheRobotAndUpdateMapGUI(Player player, Position newPos) {
        if (newPos.getY() == 1000 || newPos.getX() == 1000) {
            return;
        }
        map.moveRobot(player.getRobot(), newPos);
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
        } else if (map.getBoardObject(position) instanceof Tile) {
            return "nothing";
        } else if (map.getBoardObject(position) instanceof Flag) {
            return "flag";
        } else {
            return "ok";
        }
    }


    /**
     * Moving a robot forward,
     *
     * @param player     player to move
     * @param currentPos current position of robot
     * @return the final new position, ex the third position if the movCard said 3 steps
     * @throws IllegalArgumentException throws if the robot moves outside the board
     */
    public static Position movingForward(Player player, Position currentPos) throws IllegalArgumentException {
        Directions direction = player.getRobot().getDirection();
        Position newPos;
        switch (direction) {
                case UP:
                    newPos = new Position(currentPos.getX(), (currentPos.getY() + 1));
                    break;
                case RIGHT:
                    newPos = new Position((currentPos.getX() + 1), currentPos.getY());
                    break;
                case LEFT:
                    newPos = new Position((currentPos.getX() - 1), currentPos.getY());
                    break;
            default:
                newPos = new Position(currentPos.getX(), (currentPos.getY() - 1));
                    break;
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
     * @throws IllegalArgumentException if robot is outside map
     */
    public static Position backingUpOrTurning180Degrees(MovementCard movCard, Player player, Position currentPos, Position newPos) throws IllegalArgumentException {
        if (movCard.getNumberOfSteps() == 1) {
            Directions direction = player.getRobot().getDirection();
            switch (direction) {
                case UP:
                    newPos = new Position(currentPos.getX(), (currentPos.getY() - 1));
                    break;
                case RIGHT:
                    newPos = new Position(currentPos.getX() - 1, currentPos.getY());
                    break;
                case LEFT:
                    newPos = new Position(currentPos.getX() + 1, currentPos.getY());
                    break;
                case DOWN:
                    newPos = new Position(currentPos.getX(), (currentPos.getY() + 1));
                    break;
            }
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
    public static Directions turningLeft(Directions direction) throws IllegalArgumentException {
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
    public static Directions turningRight(Directions direction) throws IllegalArgumentException {
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