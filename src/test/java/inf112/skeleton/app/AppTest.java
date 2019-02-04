package inf112.skeleton.app;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    //make map
    int mapX = 10;
    int mapY = 20;
    Map map = new Map(mapX, mapY);
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue( true );
    }

    //test for X-akse
    @Test
    public void testForX(){
        //make map
        assertEquals(map.getX(), 10);
    }

    //test for Y-akse
    @Test
    public void testForY(){
        //make map
        assertEquals(map.getY(), 20);
    }

    //test for robot
    @Test
    public void testForRobot(){
        map.add('r', 5, 5);
        assertTrue(map.isInstance('r', 5, 5));
    }
}
