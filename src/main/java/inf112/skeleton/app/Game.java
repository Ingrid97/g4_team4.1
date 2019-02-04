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
        for (int i = 0; i < map.getX(); i++) {
            for (int j = 0; j < map.getY(); j++) {
                if (map.getChar(i, j) instanceof Player) {
                    System.out.print('r');
                } else if (map.getChar(i, j) instanceof Wall) {
                    System.out.print('*');
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
            System.out.println("the file does not exist");
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

        try {
            for (int i = 0; i < x; i++) {
                String line = br.readLine();
                char[] lines = line.toCharArray();
                for (int j = 0; j < y; j++) {
                    if (lines[j] == 'r'){
                        map.add(new Player(i, j), i, j);
                    } else if (lines[j] == '*'){
                        map.add(new Wall(i, j), i, j);
                    } else {
                        map.add(new Norhing(i, j), i, j);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There ar something wrong with the map");
            return null;
        }

        System.out.println("Adding stuff to the map...");

        return map;
    }
}

/**
 * Classes for testing
 */
class Player implements IBoardObject{
    int x;
    int y;

    public Player(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int color() {
        return 0;
    }
}

class Wall implements IBoardObject{
    int x;
    int y;

    public Wall(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int color() {
        return 0;
    }
}

class Norhing implements IBoardObject{
    int x;
    int y;

    public Norhing(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int color() {
        return 0;
    }
}