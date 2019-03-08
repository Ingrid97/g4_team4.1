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

        players = new ArrayList<>();

        //leser inn map fra fil
        robotPositions = new int[4][2];
        numberOfRobots = 0;
        numberOfPlayers = 0;
        players = new ArrayList<>();
        map = makeMap("testMap1.txt");
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
            //getting movement cars from player/s
            ArrayList<ArrayList> listOfPrioritizedListsOfMovementCardsFromPlayers = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                ArrayList<MovementCard> movementCardsToBeExecuted;
                System.out.println("Player " + (i + 1) + " choose your cards!");
                movementCardsToBeExecuted = players.get(i).theMovementCardsThePlayerChose();
                listOfPrioritizedListsOfMovementCardsFromPlayers.add(movementCardsToBeExecuted);
                System.out.print("\n\n\n");
            }

            //playing movement cards from players
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < players.size(); i++) {
                    playMovementCard((MovementCard) listOfPrioritizedListsOfMovementCardsFromPlayers.get(i).get(j), players.get(i));
                    mapGUI.updateMap(map);
                }
                //printMap(map);
            }
        }


    }


    public static void playMovementCard(MovementCard movCard, Player player) {
        Position currentPos = player.getRobot().getPosition();
        Position newPos = new Position(1000, 1000);
            if (movCard.getDirection() == Directions.NODIRECTION) {
                try {
                    for (int i = 0; i < movCard.getNumberOfSteps(); i++) {
                        newPos = new Position(currentPos.getX(), (currentPos.getY() + 1));
                        if (!legalPosition(newPos)) break;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("A robot has fallen");
                }
            } else if (movCard.getDirection() == Directions.DOWN) {
                try {
                    if (movCard.getNumberOfSteps() == 1) {
                        newPos = new Position(currentPos.getX(), (currentPos.getY() - 1));
                    } else {
                        Directions direction = player.getRobot().getDirection();
                        newPos = currentPos;
                        switch (direction) {
                            case UP:
                                player.getRobot().setDirection(Directions.DOWN);
                            case RIGHT:
                                player.getRobot().setDirection(Directions.LEFT);
                            case LEFT:
                                player.getRobot().setDirection(Directions.RIGHT);
                            case DOWN:
                                player.getRobot().setDirection(Directions.UP);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("A robot has fallen");
                }
            } else if (movCard.getDirection() == Directions.LEFT) {
                newPos = currentPos;
                Directions direction = player.getRobot().getDirection();
                newPos = currentPos;
                switch (direction) {
                    case UP:
                        player.getRobot().setDirection(Directions.LEFT);
                    case RIGHT:
                        player.getRobot().setDirection(Directions.UP);
                    case LEFT:
                        player.getRobot().setDirection(Directions.DOWN);
                    case DOWN:
                        player.getRobot().setDirection(Directions.RIGHT);
                }
            } else {
                newPos = currentPos;
                Directions direction = player.getRobot().getDirection();
                newPos = currentPos;
                switch (direction) {
                    case UP:
                        player.getRobot().setDirection(Directions.RIGHT);
                    case RIGHT:
                        player.getRobot().setDirection(Directions.DOWN);
                    case LEFT:
                        player.getRobot().setDirection(Directions.UP);
                    case DOWN:
                        player.getRobot().setDirection(Directions.LEFT);
                }
            }

            if (legalPosition(newPos)) {
                map.moveRobot(player.getRobot(), newPos);
                player.getRobot().setPosition(newPos);
            }
    }

    public static boolean legalPosition(Position position) {
        if (!map.isValidPosition(position)) {
            return false;
        } else if (map.getBoardObject(position) instanceof Robot) {
            return false;
        } else return !(map.getBoardObject(position) instanceof Wall);
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


        System.out.println("Making the map...");
        Map map = new Map(10, 10);
        try {
            for (int i = 0; i < 10; i++){
                String[] line = br.readLine().split(",");
                int j = 0;
                for (String l : line) {
                    System.out.println(l);
                    if (l.contains("*")){
                        map.add(new Wall(i, j), i, j);
                    } else if (l.contains("r")){
                        Player player = new Player(0, new Robot(i, j, Directions.UP));
                        players.add(player);
                        map.add(player.getRobot(), i, j);
                    } else if  (l.contains("v")){
                        map.add(new Void(i, j), i, j);
                    } else if  (l.contains("l")){
                        map.add(new Laser(i, j), i, j);
                    } else if  (l.contains("b") || l.contains("y")){
                        Conveyor_belt c = new Conveyor_belt(i, j);
                        c.setPlaceDir(getDir(l));
                        if (l.contains("y"))
                            c.isYellowBelt();
                        else
                            c.isBlueBelt();
                        map.add(c, i, j);

                    }  else if  (l.contains("s")){
                        map.add(new Wrench(i, j), i, j);
                    } else if  (l.contains("h")){
                        map.add(new Wrench_hammer(i, j), i, j);
                    }  else if  (l.contains("f")){
                        map.add(new Flag(i, j), i, j);
                    } else if  (l.contains("p")){
                        map.add(new Rotating_belt(i, j), i, j);
                    } else {
                        map.add(new Nothing(i, j), i, j);
                    }
                    j++;
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

    private static int getDir(String s){
        if (s.contains("1"))
            return 1;
        if (s.contains("2"))
            return 2;
        if (s.contains("3"))
            return 3;
        return 4;
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

            for (int j = 0; j < players.get(i).memoryCapacityForThisPlayer(); j++) {
                players.get(i).giveMovementCardsToThePlayer(copy.get(j));
            }
        }
    }
}