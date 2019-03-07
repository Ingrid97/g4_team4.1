package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import java.util.ArrayList;

public class Map {

    private ArrayList[][] map;

    public Map(int x, int y){
        if (x < 0 || y < 0) {
                throw new IllegalArgumentException("the x or y value of the map cannot be less than zero");
        }
        map = new ArrayList[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                map[i][j] = new ArrayList<IBoardObject>();
            }

        }
    }

    int getX(){
        return map.length;
    }

    int getY(){
        return map[0].length;
    }

    void add(IBoardObject c, int x, int y){
        if (x < 0 || y < 0) {
            if (x > getX() || y > getY()) {
                throw new IllegalArgumentException("Cannot place object with these values: x= " + x +" y= "+y);
            }
        }
        map[x][y].add(c);

    }

    //test
    IBoardObject getBoardObject(Position position) {
        if (isValidPosition(position)) {
            return (IBoardObject) map[position.getX()][position.getY()].get(0);
        }
        return null;
    }

    public ArrayList[][] getMap() {
        ArrayList[][] copyOfMap = new ArrayList[getX()][getY()];
        for (int x = 0; x < getX(); x++) {
            for (int y = 0; y < getY(); y++) {
                copyOfMap[x][y] = map[x][y];
            }
        }
        return map;
    }

    public boolean isEmpty(int x, int y) {
        return map[x][y].contains(null);
    }


    public boolean isValidPosition(Position position) {
        return (position.getY() >= 0 || position.getX() >= 0 || position.getX() <= getX() || position.getY() <= getY());
    }

    public void moveRobot(Robot robot, Position newPosition) {
        map[robot.getPosition().getX()][robot.getPosition().getY()].remove(robot);
        map[newPosition.getX()][newPosition.getY()].add(robot);
    }
}
