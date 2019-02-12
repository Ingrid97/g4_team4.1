package inf112.skeleton.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Unit test for Robot.
 */
public class RobotTest {

    @Test
    public void xValueOfANewRobotIsCorrectX() {
        Robot robot = new Robot(5, 5);
        assertEquals(5, robot.getX());
    }

    @Test
    public void yValueOfANewRobotIsCorrectY() {
        Robot robot = new Robot(5, 5);
        assertEquals(5, robot.getY());
    }


    // Testing X and Y value when moving up, down, right, left

    // Up
    @Test
    public void movingUpGiveRightX() {
        Robot robot = new Robot(5, 5);
        robot.move(Directions.UP);
        assertEquals(5, robot.getX());
    }

    @Test
    public void movingUpGiveRightY() {
        Robot robot = new Robot(5, 5);
        robot.move(Directions.UP);
        assertEquals(6, robot.getY());
    }

    // Down
    @Test
    public void movingDownGiveRightX() {
        Robot robot = new Robot(5, 5);
        robot.move(Directions.DOWN);
        assertEquals(5, robot.getX());
    }

    @Test
    public void movingDownGiveRightY() {
        Robot robot = new Robot(5, 5);
        robot.move(Directions.DOWN);
        assertEquals(4, robot.getY());
    }

    // Right
    @Test
    public void movingRightGiveRightX() {
        Robot robot = new Robot(5, 5);
        robot.move(Directions.RIGHT);
        assertEquals(6, robot.getX());
    }

    @Test
    public void movingRightGiveRightY() {
        Robot robot = new Robot(5, 5);
        robot.move(Directions.RIGHT);
        assertEquals(5, robot.getY());
    }

    // Left
    @Test
    public void movingLeftGiveRightX() {
        Robot robot = new Robot(5, 5);
        robot.move(Directions.LEFT);
        assertEquals(4, robot.getX());
    }

    @Test
    public void movingLeftGiveRightY() {
        Robot robot = new Robot(5, 5);
        robot.move(Directions.LEFT);
        assertEquals(5, robot.getY());
    }


    // Testing healthPoints of robots
    @Test
    public void takingOneDamageResultInOneHealthPointReduction() {
        Robot robotShooting = new Robot(5, 5);
        Robot theRobotGettingShot = new Robot(2, 2);

        // 1 shot
        robotShooting.shootLaser(theRobotGettingShot);

        assertEquals(2, theRobotGettingShot.getHealthPoints());
    }

    @Test
    public void takingTwoDamageResultInTwoHealthPointReduction() {
        Robot robotShooting = new Robot(5, 5);
        Robot theRobotGettingShot = new Robot(2, 2);

        // 2 shots
        robotShooting.shootLaser(theRobotGettingShot);
        robotShooting.shootLaser(theRobotGettingShot);

        assertEquals(1, theRobotGettingShot.getHealthPoints());
    }

    @Test
    public void takingThreeDamageResultInThreeHealthPointReduction() {
        Robot robotShooting = new Robot(5, 5);
        Robot theRobotGettingShot = new Robot(2, 2);

        // 3 shots
        robotShooting.shootLaser(theRobotGettingShot);
        robotShooting.shootLaser(theRobotGettingShot);
        robotShooting.shootLaser(theRobotGettingShot);

        assertEquals(0, theRobotGettingShot.getHealthPoints());
    }

    @Test
    public void takingThreeDamageResultInAliveStatusChanging() {
        Robot robotShooting = new Robot(5, 5);
        Robot theRobotGettingShot = new Robot(2, 2);

        // 3 shots
        robotShooting.shootLaser(theRobotGettingShot);
        robotShooting.shootLaser(theRobotGettingShot);
        robotShooting.shootLaser(theRobotGettingShot);

        assertFalse(theRobotGettingShot.isAlive());
    }
}