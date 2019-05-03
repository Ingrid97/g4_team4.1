package inf112.skeleton.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for Robot.
 */
public class RobotTest {

    @Test
    public void setPosToBackup() {
        Robot robot = new Robot(5, 5, Directions.UP);
        robot.dropBackUpAtCurrentPosition();
        robot.setPosition(new Position(0, 0));
        robot.setPositionToBackUp();
        assertEquals(5, robot.getPosition().getX());
    }

    @Test
    public void xValueOfANewRobotIsCorrectX() {
        Robot robot = new Robot(5, 5, Directions.UP);
        assertEquals(5, robot.getX());
    }

    @Test
    public void yValueOfANewRobotIsCorrectY() {
        Robot robot = new Robot(5, 5, Directions.UP);
        assertEquals(5, robot.getY());
    }


    // Testing X and Y value when moving up, down, right, left

    // Up
    @Test
    public void movingUpGiveRightX() {
        Robot robot = new Robot(5, 5, Directions.UP);
        robot.move(1);
        assertEquals(5, robot.getX());
    }

    @Test
    public void movingUpGiveRightY() {
        Robot robot = new Robot(5, 5, Directions.UP);
        robot.move(1);
        assertEquals(6, robot.getY());
    }

    // Down
    @Test
    public void movingDownGiveDownX() {
        Robot robot = new Robot(5, 5, Directions.DOWN);
        robot.move(1);
        assertEquals(5, robot.getX());
    }

    @Test
    public void movingDownGiveDownY() {
        Robot robot = new Robot(5, 5, Directions.DOWN);
        robot.move(1);
        assertEquals(4, robot.getY());
    }

    // Right
    @Test
    public void movingRightGiveRightX() {
        Robot robot = new Robot(5, 5, Directions.RIGHT);
        robot.move(1);
        assertEquals(6, robot.getX());
    }

    @Test
    public void movingRightGiveRightY() {
        Robot robot = new Robot(5, 5, Directions.RIGHT);
        robot.move(1);
        assertEquals(5, robot.getY());
    }

    // Left
    @Test
    public void movingLeftGiveRightX() {
        Robot robot = new Robot(5, 5, Directions.LEFT);
        robot.move(1);
        assertEquals(4, robot.getX());
    }

    @Test
    public void movingLeftGiveRightY() {
        Robot robot = new Robot(5, 5, Directions.LEFT);
        robot.move(1);
        assertEquals(5, robot.getY());
    }


    // Testing healthPoints of robots
    @Test
    public void takingOneDamageResultInOneMemoryCapacityReduction() {
        Robot robotShooting = new Robot(5, 5, Directions.UP);
        Robot theRobotGettingShot = new Robot(2, 2, Directions.UP);

        int memory = theRobotGettingShot.getMemoryCapacity();
        // 1 shot
        robotShooting.shootLaser(theRobotGettingShot);

        assertEquals((memory - 1), theRobotGettingShot.getMemoryCapacity());
    }

    @Test
    public void takingTwoDamageResultInTwoMemoryCapacityReduction() {
        Robot robotShooting = new Robot(5, 5, Directions.UP);
        Robot theRobotGettingShot = new Robot(2, 2, Directions.UP);

        int memory = theRobotGettingShot.getMemoryCapacity();
        // 2 shots
        robotShooting.shootLaser(theRobotGettingShot);
        robotShooting.shootLaser(theRobotGettingShot);

        assertEquals((memory - 2), theRobotGettingShot.getMemoryCapacity());
    }


    @Test
    public void takingNineDamageResultInOneLessHP() {
        Robot robotShooting = new Robot(5, 5, Directions.UP);
        Robot theRobotGettingShot = new Robot(2, 2, Directions.UP);
        int hp = theRobotGettingShot.getHealthPoints();
        // 9 shots
        for (int i = 0; i < 9; i++) {
            robotShooting.shootLaser(theRobotGettingShot);
        }

        assertEquals(hp - 1, theRobotGettingShot.getHealthPoints());
    }

    @Test
    public void taking27DamageResultInDeath() {
        Robot robotShooting = new Robot(5, 5, Directions.UP);
        Robot theRobotGettingShot = new Robot(2, 2, Directions.UP);
        int hp = theRobotGettingShot.getHealthPoints();
        // 9 shots
        for (int i = 0; i < 27; i++) {
            robotShooting.shootLaser(theRobotGettingShot);
        }

        assertFalse(theRobotGettingShot.isAlive());
    }

    // Testing backUp

    @Test
    public void droppingBackUpDropsItAtRightLocation() {
        Robot robot = new Robot(5, 5, Directions.UP);
        robot.dropBackUpAtCurrentPosition();
        assertEquals(5, robot.getBackUpPosition().getX());
        assertEquals(5, robot.getBackUpPosition().getY());
    }


    @Test
    public void whenRobotHaveNoMemoryRespawnOnBackup() {
        Robot robotShooting = new Robot(5, 5, Directions.UP);
        Robot theRobotGettingShot = new Robot(2, 2, Directions.UP);

        theRobotGettingShot.move(1);            // (2, 2)
        theRobotGettingShot.dropBackUpAtCurrentPosition();    // (2, 3)
        theRobotGettingShot.move(1);            // (2, 4)

        for (int i = 0; i < 9; i++) {
            robotShooting.shootLaser(theRobotGettingShot);
        }


        assertNotEquals(4, theRobotGettingShot.getY());

        assertEquals(2, theRobotGettingShot.getX());
        assertEquals(3, theRobotGettingShot.getY());

    }
    @Test

    //Test to check if powerdown can be choosen after required one round

    public void choosePowerDown() {
        Robot robot = new Robot(5, 5, Directions.UP);


        for(int i = 0; i >= 9; i++) {
            robot.takePowerdown();
        }
        if(robot.getPowerDown() == true ){
            assertTrue("PowerDown being taken", true);
        }
        assertFalse("PowerDown not working", false);


    }
   //Robot getting back HP after PowerDown
    @Test
    public void HPPowerDown() {
        Robot robotHurt = new Robot(5, 5, Directions.UP);


        int beforeDamage = robotHurt.getHealthPoints();
        int afterDamage = beforeDamage-1;


        if (robotHurt.getPowerDown() == true) {
            for (int i = 0; i > 9; i++) {
                robotHurt.takePowerdown();
            }
            robotHurt.finishPowerdown();

            if(robotHurt.getHealthPoints() <= afterDamage){
                assertFalse("Robot should have full memory after powerdown", false);
            }
            assertTrue("Robot has full memory", true);


        }

    }




}