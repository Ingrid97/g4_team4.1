package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Game {
    public static void main(String[] args) {
        playGame();
    }

    public static void playGame(){
        //les inn map fra fil
        Map map = makeMap("testMap1.txt");
        if (map == null)
            System.exit(0);

        //for testing
        printMap(map);

    }


    public static void printMap(Map map){
        System.out.println("Map:");
        //TODO: make switch and fix GUI stuff
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
                } else if (map.getBoardObject(i, j) instanceof Blue_Bond) {
                    System.out.print('b');
                } else if (map.getBoardObject(i, j) instanceof Yellow_bond) {
                    System.out.print('y');
                } else if (map.getBoardObject(i, j) instanceof Screwdriver) {
                    System.out.print('s');
                } else if (map.getBoardObject(i, j) instanceof Hammer_Screwdriver) {
                    System.out.print('h');
                } else if (map.getBoardObject(i, j) instanceof Flag) {
                    System.out.print('f');
                } else if (map.getBoardObject(i, j) instanceof Rotating_Plate) {
                    System.out.print('r');
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
            for (int i = 0; i < x; i++) {
                String line = br.readLine();
                char[] lines = line.toCharArray();
                for (int j = 0; j < y; j++) {
                    if (lines[j] == '*'){
                        map.add(new Wall(i, j), i, j);
                    } else if (lines[j] == 'r'){
                       map.add(new Robot(i, j), i, j);
                    } else if  (lines[j] == 'v'){
                        map.add(new Void(i, j), i, j);
                    } else if  (lines[j] == 'l'){
                        map.add(new Laser(i, j), i, j);
                    } else if  (lines[j] == 'b'){
                        map.add(new Blue_Bond(i, j), i, j);
                    } else if  (lines[j] == 'y'){
                        map.add(new Yellow_bond(i, j), i, j);
                    } else if  (lines[j] == 's'){
                        map.add(new Screwdriver(i, j), i, j);
                    } else if  (lines[j] == 'h'){
                        map.add(new Hammer_Screwdriver(i, j), i, j);
                    }  else if  (lines[j] == 'f'){
                        map.add(new Flag(i, j), i, j);
                    } else if  (lines[j] == 'p'){
                        map.add(new Rotating_Plate(i, j), i, j);
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
}