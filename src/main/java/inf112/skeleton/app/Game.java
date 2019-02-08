package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Game {

    public static Map playGame() {
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
                } else if (map.getBoardObject(i, j) instanceof Conveyor_belt) {
                    System.out.print('y');
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
                for (int j = 0; j < y; j++) {
                    if (lines[j+1] == '*'){
                        map.add(new Wall(i, j), i, j);
                    } else if (lines[j+1] == 'r'){
                       map.add(new Robot(i, j), i, j);
                    } else if  (lines[j+1] == 'v'){
                        map.add(new Void(i, j), i, j);
                    } else if  (lines[j+1] == 'l'){
                        map.add(new Laser(i, j), i, j);
                    } else if  (lines[j+1] == 'b'){
                        map.add(new Conveyor_belt(i, j), i, j);
                    } else if  (lines[j+1] == 'y'){
                        map.add(new Conveyor_belt(i, j), i, j);
                    } else if  (lines[j+1] == 's'){
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
}