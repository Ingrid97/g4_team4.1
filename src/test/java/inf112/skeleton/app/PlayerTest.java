package inf112.skeleton.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void referenceOfRobot() {
        Robot robot = new Robot(5, 5, Directions.UP);
        Player player = new Player(0, robot);
        assertEquals(robot, player.getRobot());
    }

    @Test
    public void nameTest() {
        Robot robot = new Robot(5, 5, Directions.UP);
        Player player = new Player(0, robot);
        player.setName("ole");
        assertEquals("ole", player.getName());
    }

    @Test
    public void damageTest() {
        Robot robot = new Robot(5, 5, Directions.UP);
        Player player = new Player(0, robot);
        robot.takeDamage(1);
        assertEquals(8, player.memoryCapacityForThisPlayer());
    }

    @Test
    public void flagsVisited() {
        Robot robot = new Robot(5, 5, Directions.UP);
        Player player = new Player(3, robot);
        player.setFlagsVisitedTrue(0);
        player.setFlagsVisitedTrue(2);
        player.setFlagsVisitedTrue(1);
        boolean[] flags = player.getFlagsVisited();
        for (int i = 0; i < 3; i++) {
            assertTrue(flags[i]);
        }
    }

    @Test
    public void powerdown() {
        Robot robot = new Robot(5, 5, Directions.UP);
        Player player = new Player(0, robot);
        robot.takeDamage(3);
        player.powerDown();
        assertEquals(9, player.memoryCapacityForThisPlayer());
    }

    @Test
    public void positionTest() {
        Robot robot = new Robot(5, 5, Directions.UP);
        Player player = new Player(0, robot);
        robot.takeDamage(1);
        assertEquals(5, player.getX());
        assertEquals(5, player.getY());
    }


}
