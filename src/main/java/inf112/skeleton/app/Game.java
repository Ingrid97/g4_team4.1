package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<Player> players;
    private static ArrayList<MovementCard> theFullDeckOfAllMovementCards;


    public static Map playGame() {
        // Lage alle kortene
        setUpTheFullDeckOfCards();

        //les inn map fra fil
        Map map = makeMap("testMap1.txt");
        if (map == null)
            System.exit(0);


        //for testing
        printMap(map);

        return map;
    }


    public static void printMap(Map map){
        System.out.println("Map:");
        //TODO: make switch and fix GUI stuffs
        for (int i = 0; i < map.getX(); i++) {
            for (int j = 0; j < map.getY(); j++) {
                if (map.getBoardObject(i, j) instanceof Wall) {
                    System.out.print('*');
                } else if (map.getBoardObject(i, j) instanceof Robot) {
                    System.out.print('r');
                } else if (map.getBoardObject(i, j) instanceof Void) {
                    System.out.print('v');
                } else if (map.getBoardObject(i, j) instanceof Laser) {
                    System.out.print('l');
                } else if (map.getBoardObject(i, j) instanceof Conveyor_belt) {
                    System.out.print('b');
                } else if (map.getBoardObject(i, j) instanceof Wrench) {
                    System.out.print('s');
                } else if (map.getBoardObject(i, j) instanceof Wrench_hammer) {
                    System.out.print('h');
                } else if (map.getBoardObject(i, j) instanceof Flag) {
                    System.out.print('f');
                } else if (map.getBoardObject(i, j) instanceof Rotating_belt) {
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
                System.out.println(line);
                for (int j = 1; j < y; j += 2) {
                    System.out.println("j: " + j + "   j+1: " + (j+1));
                    System.out.println("i: " + i + "   x: " + x);
                    if (lines[j+1] == '*'){
                        map.add(new Wall(i, j/2), i, j/2);
                    } else if (lines[j+1] == 'r'){
                       map.add(new Robot(i, j/2, Directions.UP), i,j/2);
                    } else if  (lines[j+1] == 'v'){
                        map.add(new Void(i, j/2), i, j/2);
                    } else if  (lines[j+1] == 'l'){
                        map.add(new Laser(i, j/2), i, j/2);
                    } else if  (lines[j+1] == 'b' || lines[j+1] == 'y'){
                        Conveyor_belt c = new Conveyor_belt(i, j/2);
                        c.setPlaceDir((int)lines[j]);
                        if (lines[j+1] == 'y')
                            c.isYelloBelt();
                        else
                            c.isBlueBelt();
                        map.add(c, i, j/2);

                    }  else if  (lines[j+1] == 's'){
                        map.add(new Wrench(i, j/2), i, j/2);
                    } else if  (lines[j+1] == 'h'){
                        map.add(new Wrench_hammer(i, j/2), i, j/2);
                    }  else if  (lines[j+1] == 'f'){
                        map.add(new Flag(i, j/2), i, j/2);
                    } else if  (lines[j+1] == 'p'){
                        map.add(new Rotating_belt(i, j/2), i, j/2);
                    } else {
                        map.add(new Nothing(i, j/2), i, j/2);
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

    private void addPlayers() {
        // Random player
        int numberOfFlags = 3;
        this.players.add(new Player(numberOfFlags, new Robot(2, 3, Directions.UP)));
    }

    private void dealOutMovementCards() {
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