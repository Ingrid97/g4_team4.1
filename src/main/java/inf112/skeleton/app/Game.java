package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

        //leser inn map fra fil
        robotPositions = new int[4][2];
        numberOfRobots = 0;
        numberOfPlayers = 0;
        map = makeMap("testMap1.txt");
        if (map == null)
            System.exit(0);

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo Rally";
        cfg.width = 640;
        cfg.height = 640;
        new LwjglApplication(new MapGUI(map), cfg);//instantiating MapGUI and updating the map it prints

        players = new ArrayList<>();
        gameOver = true;
        setUpTheFullDeckOfCards();
        dealOutMovementCards();
        while (gameOver) {
            ArrayList<ArrayList> listOfPrioritizedListsOfMovementCardsFromPlayers = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                ArrayList<MovementCard> movementCardsToBeExecuted = new ArrayList<>();
                movementCardsToBeExecuted = players.get(i).theMovementCardsThePlayerChose();
                listOfPrioritizedListsOfMovementCardsFromPlayers.add(movementCardsToBeExecuted);
            }
            int j = 0;
            for (int i = 0; i < numberOfPlayers; i++) {
                listOfPrioritizedListsOfMovementCardsFromPlayers.get(i).get(j);
            }
        }


    }


    public static void playMovementCard(MovementCard movCard, Player player) {
        Position currentPos = player.getRobot().getPosition();
        if (movCard.getNumberOfSteps() != 0 && movCard.getDirection() == Directions.NODIRECTION) {
            Position newPos;
            if (movCard.getDirection() == Directions.NODIRECTION) {
                newPos = new Position(currentPos.getX(), (currentPos.getY() + movCard.getNumberOfSteps()));
            } else if (movCard.getDirection() == Directions.DOWN) {
                newPos = new Position(currentPos.getX(), (currentPos.getY() - movCard.getNumberOfSteps()));
            } else {
                player.getRobot().setDirection(movCard.getDirection());
                newPos = currentPos;
            }
            if (legalPosition(newPos)) {
                //MOVE THE PIECES
            }

        }

    }

    public static boolean legalPosition(Position position) {
        if (map.getBoardObject(position) instanceof Wall) {
            return false;
        } else if (map.getBoardObject(position) instanceof Robot) {
            return false;
        } else return !map.isValidPosition(position);
    }


    public static void printMap(Map map){
        System.out.println("Map:");
        //TODO: make switch and fix GUI stuffs
        for (int i = 0; i < map.getX(); i++) {
            for (int j = 0; j < map.getY(); j++) {
                if (map.getBoardObject(new Position(i, j)) instanceof Wall) {
                    System.out.print('*');
                } else if (map.getBoardObject(new Position(i, j)) instanceof Robot) {
                    System.out.print('r');
                } else if (map.getBoardObject(new Position(i, j)) instanceof Void) {
                    System.out.print('v');
                } else if (map.getBoardObject(new Position(i, j)) instanceof Laser) {
                    System.out.print('l');
                } else if (map.getBoardObject(new Position(i, j)) instanceof Conveyor_belt) {
                    System.out.print('b');
                } else if (map.getBoardObject(new Position(i, j)) instanceof Wrench) {
                    System.out.print('s');
                } else if (map.getBoardObject(new Position(i, j)) instanceof Wrench_hammer) {
                    System.out.print('h');
                } else if (map.getBoardObject(new Position(i, j)) instanceof Flag) {
                    System.out.print('f');
                } else if (map.getBoardObject(new Position(i, j)) instanceof Rotating_belt) {
                    System.out.print('p');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }


    /**
     * making the map from a given file
     * @param filename
     * @return
     */
    public static Map makeMap(String filename){
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("this file does not exist: " + filename);
            return null;
        }


        //temporary solution since br.read() is fucked
        String xy;
        int split;
        try {
            xy = br.readLine();
            split = 0;
            for (int i = 0; i < xy.length(); i++) {
                if (xy.substring(i, i+1).equals(" ")){
                    split = i;
                }
            }
            System.out.println("x: " + xy.substring(0, split) + " y: " + xy.substring(split+1));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("the x and y in the file are wrong");
            return null;
        }

        System.out.println("Making the map...");
        int x = Integer.parseInt(xy.substring(0, split));
        int y = Integer.parseInt(xy.substring(split+1));
        Map map = new Map(x, y);
        //TODO: make switch
        try {
            String line = br.readLine();
            for (int i = 0; i < x; i++) {
                line = br.readLine();
                char[] lines = line.toCharArray();
                for (int j = 0; j < y; j++) {
                    if (lines[j+1] == '*'){
                        map.add(new Wall(i, j), i, j);
                    } else if (lines[j+1] == 'r'){
                        Player player = new Player(0, new Robot(i, j, Directions.UP));
                        players.add(player);
                        map.add(player.getRobot(), i, j);
                    } else if  (lines[j+1] == 'v'){
                        map.add(new Void(i, j), i, j);
                    } else if  (lines[j+1] == 'l'){
                        map.add(new Laser(i, j), i, j);
                    } else if  (lines[j+1] == 'b' || lines[j+1] == 'y'){
                        Conveyor_belt c = new Conveyor_belt(i, j);
                        if (lines[j+1] == 'y')
                            c.isYelloBelt();
                        else
                            c.isBlueBelt();
                        map.add(c, i, j);
                    }  else if  (lines[j+1] == 's'){
                        map.add(new Wrench(i, j), i, j);
                    } else if  (lines[j+1] == 'h'){
                        map.add(new Wrench_hammer(i, j), i, j);
                    }  else if  (lines[j+1] == 'f'){
                        map.add(new Flag(i, j), i, j);
                    } else if  (lines[j+1] == 'p'){
                        map.add(new Rotating_belt(i, j), i, j);
                    } else {
                        map.add(new Nothing(i, j), i, j);
                    }
                  }
              }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There's something wrong with the map");
            return null;
        }

        System.out.println("Adding stuff to the map...");

        return map;
    }

    private static void setUpTheFullDeckOfCards() {
        theFullDeckOfAllMovementCards = new ArrayList<>();

        // Rotate cards
        // Left rotation, right rotation, forward movement 1
        int priorityForLeft = 80;
        int priorityForRight = 70;
        int priorityForMovementForward = 490;
        for (int i = 0; i < 18; i++) {
            theFullDeckOfAllMovementCards.add(new MovementCard(Directions.LEFT, 0,  priorityForLeft));
            theFullDeckOfAllMovementCards.add(new MovementCard(Directions.RIGHT, 0,  priorityForRight));
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
            // TODO Make it take a part of the list instead of individual cards
            for (int j = 0; j < players.get(i).memoryCapacityForThisPlayer(); j++) {
                players.get(i).giveMovementCardsToThePlayer(copy.get(j));
            }
        }
    }
}