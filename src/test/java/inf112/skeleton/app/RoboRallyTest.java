package inf112.skeleton.app;

import boardObjects.Flag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoboRallyTest {

    public RoboRally roboRally;

    @Before
    public void initiate() {
        this.roboRally = new RoboRally("testMap2.txt");
    }

    @After
    public void dump() {
        this.roboRally = null;
    }

    @Test
    public void updateFlagsWinCondition() {
        assertFalse(roboRally.updateFlag(roboRally.getPlayers().get(0), new Flag(6, 6, 0)));
        assertFalse(roboRally.updateFlag(roboRally.getPlayers().get(0), new Flag(6, 6, 1)));
        assertTrue(roboRally.updateFlag(roboRally.getPlayers().get(0), new Flag(6, 6, 2)));
    }

    @Test
    public void robotsFire() {
        roboRally.robotLasersFire();
        ArrayList<Player> players = roboRally.getPlayers();
        assertEquals(8, players.get(0).memoryCapacityForThisPlayer());
    }

    @Test
    public void robotsFireKills() {
        for (int i = 0; i < 9; i++) {
            roboRally.robotLasersFire();
        }
        ArrayList<Player> players = roboRally.getPlayers();
        assertEquals(2, players.get(0).getRobot().getHealthPoints());
    }

    @Test
    public void robotsFireKillsAndMovesToBackup() {
        ArrayList<Player> players = roboRally.getPlayers();
        players.get(0).getRobot().setPosition(new Position(4, 4));
        players.get(0).getRobot().dropBackUpAtCurrentPosition();
        players.get(0).getRobot().setPosition(new Position(0, 0));
        for (int i = 0; i < 9; i++) {
            roboRally.robotLasersFire();
        }
        assertEquals(4, players.get(0).getRobot().getPosition().getX());
    }

    @Test
    public void legalPostionTest() {
        assertEquals("ok", roboRally.legalPosition(new Position(5, 5)));
    }

    @Test
    public void legalPostionTestRobot() {
        assertEquals("robot", roboRally.legalPosition(new Position(0, 0)));
    }

    @Test
    public void legalPostionTestVoid() {
        assertEquals("dead", roboRally.legalPosition(new Position(0, 1)));
    }

    @Test
    public void legalPostionTestLaser() {
        assertEquals("laser", roboRally.legalPosition(new Position(0, 2)));
    }

    @Test
    public void legalPostionTestConveyourBelt() {
        assertEquals("conveyor_belt", roboRally.legalPosition(new Position(0, 3)));
    }

    @Test
    public void legalPostionTestRotatingBelt() {
        assertEquals("rotating_belt", roboRally.legalPosition(new Position(0, 4)));
    }

    @Test
    public void turningRightUp() {
        assertEquals(CalculatePosition.turningRight(Directions.UP), Directions.RIGHT);
    }

    @Test
    public void turningRightDown() {
        assertEquals(CalculatePosition.turningRight(Directions.DOWN), Directions.LEFT);
    }

    @Test
    public void turningRightLeft() {
        assertEquals(CalculatePosition.turningRight(Directions.LEFT), Directions.UP);
    }

    @Test
    public void turningRight() {
        assertEquals(CalculatePosition.turningRight(Directions.RIGHT), Directions.DOWN);
    }

    @Test
    public void turningLeftUp() {
        assertEquals(CalculatePosition.turningLeft(Directions.UP), Directions.LEFT);
    }

    @Test
    public void turningLeftDown() {
        assertEquals(CalculatePosition.turningLeft(Directions.DOWN), Directions.RIGHT);
    }

    @Test
    public void turningLeftRight() {
        assertEquals(CalculatePosition.turningLeft(Directions.RIGHT), Directions.UP);
    }

    @Test
    public void turningLeft() {
        assertEquals(CalculatePosition.turningLeft(Directions.LEFT), Directions.DOWN);
    }

    @Test
    public void backingUp() {
        Player player = new Player(0, new Robot(5, 5, Directions.UP));
        MovementCard movementCard = new MovementCard(Directions.DOWN, 1, 0);
        assertEquals(CalculatePosition.Uturn(movementCard, player).getX(), 6);
    }

    @Test
    public void uTurn() {
        Player player = new Player(0, new Robot(5, 5, Directions.UP));
        MovementCard movementCard = new MovementCard(Directions.DOWN, 0, 0);
        Position pos = CalculatePosition.Uturn(movementCard, player);
        assertEquals(pos.getY(), 5);
        assertEquals(player.getRobot().getDirection(), Directions.DOWN);
    }

    @Test
    public void movingForwardUp() {
        Position pos = CalculatePosition.movingForward(new Position(5, 5), Directions.UP);
        assertEquals(pos.getX(), 4);
    }

    @Test
    public void movingForwardRight() {
        Position pos = CalculatePosition.movingForward(new Position(5, 5), Directions.RIGHT);
        assertEquals(pos.getY(), 6);
    }

    @Test
    public void movingForwardLeft() {
        Position pos = CalculatePosition.movingForward(new Position(5, 5), Directions.LEFT);
        assertEquals(pos.getY(), 4);
    }

    @Test
    public void movingForwardDown() {
        Position pos = CalculatePosition.movingForward(new Position(5, 5), Directions.DOWN);
        assertEquals(pos.getX(), 6);
    }
}
