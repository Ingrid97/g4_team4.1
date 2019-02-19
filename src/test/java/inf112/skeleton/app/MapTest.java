package inf112.skeleton.app;

import org.junit.Test;

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
    public void testThatGivenInputXOnMapIsCorrect(){
        assertEquals(build(10,20).getX(), 10);
    }

    @Test
    public void testThatGivenInputYOnMapIsCorrect(){
        assertEquals(build(10,20).getY(), 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitializingMapWithXValueLessThanZeroThrowsException() {
        build(-1,3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitializingMapWithYValueLessThanZeroThrowsException() {
        build(3,-1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void placingObjectOutsideMapThrowsException(){
        Map map = build(10, 20);
        map.add(new Robot(15, 10, Directions.UP), 15, 10 );
    }

    @Test
    public void placingObjectOnBorderOfMapDontThrowException(){
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
    public void RobotOnMap(){
        Map map = build(10, 20);
        map.add(new Robot(5, 5, Directions.UP), 5, 5);
        assertTrue(map.getBoardObject(5, 5) instanceof Robot);
    }

    @Test
    public void allSquaresInGetMapEqualsSquaresInOriginal() {
        Map map = build(10, 10);
        map = addRobotToAllSquaresOnTheMap(map);
        IBoardObject[][] copyOfMap = map.getMap();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                assertEquals(map.getBoardObject(x, y), copyOfMap[x][y]);
            }
        }
    }


}
