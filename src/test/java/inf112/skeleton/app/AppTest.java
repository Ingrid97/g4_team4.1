package inf112.skeleton.app;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    //make the map for testing
    int mapX = 10;
    int mapY = 20;
    Map map = new Map(mapX, mapY);



    @Test
    public void testForX(){
        assertEquals(map.getX(), 10);
    }

    @Test
    public void testForY(){
        assertEquals(map.getY(), 20);
    }


    @Test
    public void RobotOnMap(){
        map.add(new Robot(5, 5), 5, 5);
        assertTrue(map.getBoardObject(5, 5) instanceof Robot);
    }


}
