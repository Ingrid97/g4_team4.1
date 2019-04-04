package inf112.skeleton.app;

import boardObjects.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Unit test for Map.
 */
public class MapTest {


    public Map build(int x, int y) {
        Map map = new Map(x, y);
        return map;
    }

    public Map addRobotToAllSquaresOnTheMap(Map map) {
        for (int x = 0; x < map.getX(); x++) {
            for (int y = 0; y < map.getY(); y++) {
                map.add(new Robot(x, y, Directions.UP), x, y);
            }
        }
        return map;
    }

    @Test
    public void GivenInputXOnMapIsCorrect() {
        assertEquals(build(10, 20).getX(), 10);
    }

    @Test
    public void GivenInputYOnMapIsCorrect() {
        assertEquals(build(10, 20).getY(), 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void initializingWithXValueLessThanZeroThrowsException() {
        build(-1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void initializingWithYValueLessThanZeroThrowsException() {
        build(3, -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void placingObjectOutsideMapThrowsException() {
        Map map = build(10, 20);
        map.add(new Robot(15, 10, Directions.UP), 15, 10);
    }

    @Test
    public void placingObjectOnBorderOfMapDontThrowException() {
        Map map = build(10, 20);
        map.add(new Robot(9, 19, Directions.UP), 9, 19);
        assertTrue(true);
    }

    @Test
    public void isEmptyGivesFalseOnMapFilledWithRobots10x10() {
        Map map = build(10, 10);
        map = addRobotToAllSquaresOnTheMap(map);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                assertFalse(map.isEmpty(x, y));
            }
        }
    }

    @Test
    public void isEmptyGivesTrueOnMapEmptyMap10x10() {
        Map map = build(10, 10);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                assertTrue(map.isEmpty(x, y));
            }
        }
    }

    @Test
    public void RobotOnMap() {
        Map map = build(10, 20);
        map.add(new Robot(5, 5, Directions.UP), 5, 5);
        assertTrue(map.getBoardObject(new Position(5, 5)) instanceof Robot);
    }

    @Test
    public void copyOfMapGivesIdenticalMap() {
        Map map = build(10, 10);
        map = addRobotToAllSquaresOnTheMap(map);
        ArrayList[][] copyOfMap = map.getMap();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                assertEquals(map.getBoardObject(new Position(x, y)), copyOfMap[x][y].get(0));
            }
        }
    }

    @Test
    public void moveRobotLeavesAEmptySquare() {
        Map map = build(10, 10);
        Robot robot = new Robot(5, 5, Directions.UP);
        map.add(robot, 5, 5);
        map.moveRobot(robot, new Position(6, 6));
        assertTrue(map.isEmpty(5, 5));
    }

    @Test
    public void moveRobotMovesTheRobot() {
        Map map = build(10, 10);
        Robot robot = new Robot(5, 5, Directions.UP);
        map.add(robot, 5, 5);
        map.moveRobot(robot, new Position(6, 6));
        assertEquals(robot, map.getBoardObject(new Position(6, 6)));
    }

    @Test
    public void getBoardObjectsReturnsNullIfEmpty() {
        Map map = build(10, 10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertNull(map.getBoardObject(new Position(i, j)));
            }
        }
    }

    @Test
    public void allSquaresAreValidPositions() {
        Map map = build(10, 10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertTrue(map.isValidPosition(new Position(i, j)));
            }
        }
    }

    @Test
    public void isValidPosition() {
        Map map = build(10, 10);
        for (int i = 0; i < 10; i++) {
            assertTrue(map.isValidPosition(new Position(i, 5)));
        }
    }

    @Test
    public void isInvalidIfOutsideTheMapX() {
        Map map = build(10, 10);
        for (int i = 10; i < 30; i++) {
            assertFalse(map.isValidPosition(new Position(i, 5)));
        }
    }


    @Test
    public void testingRobotMovement() {
        Map map = build(10, 10);
        Robot robot = new Robot(5, 5, Directions.UP);
        Player player = new Player(0, robot);
        RoboRally roboRally = new RoboRally();
        map.add(robot, 5, 5);
        map.moveRobot(robot, new Position(2, 5));
        assertTrue(map.getBoardObject(new Position(2, 5)) instanceof Robot);
    }


    @Test
    public void severalObjectsOnOneTile() {
        Map map = build(10, 10);
        map.add(new Tile(5, 5), 5, 5);
        map.add(new Flag(5, 5, 0), 5, 5);
        map.add(new Robot(5, 5, Directions.UP), 5, 5);
        ArrayList<IBoardObject> list = map.getBoardObjects(new Position(5, 5));
        assertEquals(list.size(), 3);
    }

    @Test
    public void severalObjectsOnOneTile2() {
        Map map = build(10, 10);
        map.add(new Conveyor_belt(5, 5), 5, 5);
        map.add(new Tile(5, 5), 5, 5);
        map.add(new Wall(5, 5), 5, 5);
        map.add(new Flag(5, 5, 0), 5, 5);
        map.add(new Robot(5, 5, Directions.UP), 5, 5);
        ArrayList<IBoardObject> list = map.getBoardObjects(new Position(5, 5));
        assertEquals(list.size(), 5);
    }

    @Test
    public void getBoardObjects() {
        Map map = build(10, 10);
        map.add(new Tile(5, 5), 5, 5);
        map.add(new Flag(5, 5, 0), 5, 5);
        map.add(new Robot(5, 5, Directions.UP), 5, 5);
        ArrayList<IBoardObject> list = map.getBoardObjects(new Position(5, 5));
        boolean flagFound = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof Flag) {
                flagFound = true;
            }
        }
        assertTrue(flagFound);
    }

}
