package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import boardObjects.Void;
import boardObjects.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map {

    private ArrayList[][] map;
    private int length;
    private int height;

    /**
     * Map constructor
     *
     * @param x width for the map to make
     * @param y height fo the map to make
     */
    public Map(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("the x or y value of the map cannot be less than zero");
        }
        length = x;
        height = y;
        map = new ArrayList[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                map[i][j] = new ArrayList<IBoardObject>();
            }

        }
    }

    /**
     * translator from string to int
     *
     * @param s string to be converted
     * @return 1, 2, 3, 4(to be used for Direction)
     */
    private int getDir(String s) {
        if (s.contains("1"))
            return 1;
        if (s.contains("2"))
            return 2;
        if (s.contains("3"))
            return 3;
        if (s.contains("4"))
            return 4;
        if (s.contains("5"))
            return 5;
        return 6;
    }

    /**
     * @return the maps length
     */
    int getX() {
        return length;
    }

    /**
     * @return the maps height
     */
    int getY() {
        return height;
    }

    /**
     * Adding a IBoardObject to the map
     *
     * @param c the IBoardObject to add
     * @param x coordinate for object(must be greater than 0, and lower than getX()
     * @param y coordinate for object(must be greater than 0, and lower than getY()
     */
    public void add(IBoardObject c, int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Cannot place object with these values: x= " + x + " y= " + y);
        } else if (x > getX() || y > getY()) {
            throw new IllegalArgumentException("Cannot place object with these values: x= " + x + " y= " + y);
        }
        map[x][y].add(c);

    }

    /**
     * Finding a IBoardObject in a Position on the map
     *
     * @param position the position to check
     * @return a robot if there is one, otherwise any other object if none returns null
     */
    public IBoardObject getBoardObject(Position position) {
        // Dont know if the problem is here or, if it is in the logic moving the robot ?¿
        // This needs to be changed! Throws exception
        if (isValidPosition(position)) {
            ArrayList listOfObjects = map[position.getX()][position.getY()];
            if (listOfObjects.size() == 0) {
                return null;
            } else {
                return (IBoardObject) listOfObjects.get(0);
            }
        }
        return null;
    }

    /**
     * making the map from a given file
     *
     * @param filename name of the map file
     * @return the map built from the map file
     */
    public Map makeMap(String filename, ArrayList<Player> players) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("this file does not exist: " + filename);
            return null;
        }

        // Identifier for the flags
        int identifier = 0;

        System.out.println("Making the map...");
        Map map = new Map(16, 16);
        try {
            for (int i = 0; i < 16; i++) {
                String[] line = br.readLine().split(",");
                int j = 0;
                for (String l : line) {
                    if (l.contains("*")) {
                        map.add(new Wall(i, j), i, j);
                    } else if (l.contains("r")) {
                        Player player = new Player(3, new Robot(i, j, Directions.UP));
                        players.add(player);
                        map.add(player.getRobot(), i, j);
                    } else if (l.contains("v")) {
                        map.add(new Void(i, j), i, j);
                    } else if (l.contains("l")) {
                        Laser newLaser = new Laser(i, j);
                        newLaser.setPictureDir(getDir(l));
                        map.add(newLaser, i, j);
                    } else if (l.contains("b") || l.contains("y")) {
                        Conveyor_belt c = new Conveyor_belt(i, j);
                        c.setPlaceDir(getDir(l));
                        if (l.contains("y"))
                            c.isYellowBelt();
                        else
                            c.isBlueBelt();

                        c.setPictureDir(getDir(l));
                        map.add(c, i, j);
                    } else if (l.contains("s")) {
                        map.add(new Wrench(i, j), i, j);
                    } else if (l.contains("h")) {
                        map.add(new Wrench_hammer(i, j), i, j);
                    } else if (l.contains("f")) {
                        map.add(new Flag(i, j, identifier++), i, j);
                    } else if (l.contains("p")) {
                        map.add(new Rotating_belt(i, j), i, j);
                    } else {
                        map.add(new Tile(i, j), i, j);
                    }
                    j++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There's something wrong with the map");
            return null;
        }

        //printMap();
        System.out.println("Adding stuff to the map...");

        return map;
    }

    /**
     * @return copy of the map
     */
    public ArrayList[][] getMap() {
        ArrayList[][] copyOfMap = new ArrayList[getX()][getY()];
        for (int x = 0; x < getX(); x++) {
            for (int y = 0; y < getY(); y++) {
                copyOfMap[x][y] = map[x][y];
            }
        }
        return map;
    }

    /**
     * @param x coordinate to check
     * @param y coordinate to check
     * @return true if empty
     */
    public boolean isEmpty(int x, int y) {
        return map[x][y].size() == 0;
    }

    /**
     * check if a Position is inside the map or invalid
     *
     * @param position coordinates to check
     * @return true if valid
     */
    public boolean isValidPosition(Position position) {
        if (position.getY() < 0) return false;
        if (position.getX() < 0) return false;
        if (position.getX() > getX() - 1) return false;
        return position.getY() <= getY() - 1;
    }

    /**
     * moves a robot on the map
     *
     * @param robot       robot to be moved
     * @param newPosition where to move given robot
     */
    public void moveRobot(Robot robot, Position newPosition) {
        map[newPosition.getX()][newPosition.getY()].add(robot);
        map[robot.getPosition().getX()][robot.getPosition().getY()].remove(robot);
    }

    public ArrayList<IBoardObject> getBoardObjects(Position position) {
        if (isValidPosition(position)) {
            return map[position.getX()][position.getY()];
        } else {
            return null;
        }
    }


}
