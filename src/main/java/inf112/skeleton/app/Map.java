package main.java.inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

public class Map {

    char[][] map;

    public Map(int x, int y){
        map = new char[x][y];
    }

    int getX(){
        return map.length;
    }

    int getY(){
        return map[0].length;
    }

    void add(char c, int x, int y){
        map[x][y] = c;
    }

    char getChar(int x, int y){
        return map[x][y];
    }

    boolean isInstance(char c, int x, int y){
        return map[x][y] == c;
    }

    public char[][] getMap() {
        return map;
    }
}
