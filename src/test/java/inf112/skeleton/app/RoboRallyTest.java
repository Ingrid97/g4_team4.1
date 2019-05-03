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
    public void robotOnConveyorBelt() {
        roboRally.playMovementCard(new MovementCard(Directions.NODIRECTION, 1, 1), roboRally.getPlayers().get(4));
        assertEquals(14, roboRally.getPlayers().get(4).getX());
        assertEquals(10, roboRally.getPlayers().get(4).getY());
    }

    @Test
    public void robotOnConveyorBeltBlue() {
        roboRally.playMovementCard(new MovementCard(Directions.NODIRECTION, 1, 1), roboRally.getPlayers().get(3));
        assertEquals(11, roboRally.getPlayers().get(3).getX());
        assertEquals(0, roboRally.getPlayers().get(3).getY());
    }

    @Test
    public void robotMovingBackwardsIntoVoid() {
        ArrayList<Player> players = roboRally.getPlayers();
        players.get(3).getRobot().setPosition(new Position(4, 4));
        players.get(3).getRobot().dropBackUpAtCurrentPosition();
        players.get(3).getRobot().setPosition(new Position(14, 0));
        roboRally.playMovementCard(new MovementCard(Directions.DOWN, 1, 1), roboRally.getPlayers().get(3));
        assertEquals(4, roboRally.getPlayers().get(3).getX());
        assertEquals(4, roboRally.getPlayers().get(3).getY());
    }

    @Test
    public void robotOnLaser() {
        roboRally.playMovementCard(new MovementCard(Directions.NODIRECTION, 1, 1), roboRally.getPlayers().get(2));
        assertEquals(8, roboRally.getPlayers().get(2).getRobot().getMemoryCapacity());
    }

    @Test
    public void robotFaceLeft() {
        roboRally.playMovementCard(new MovementCard(Directions.LEFT, 0, 1), roboRally.getPlayers().get(2));
        assertEquals(Directions.LEFT, roboRally.getPlayers().get(2).getRobot().getDirection());
    }

    @Test
    public void robotFaceRight() {
        roboRally.playMovementCard(new MovementCard(Directions.RIGHT, 0, 1), roboRally.getPlayers().get(2));
        assertEquals(Directions.RIGHT, roboRally.getPlayers().get(2).getRobot().getDirection());
    }

    @Test
    public void robotMoveOutsideMap() {
        roboRally.playMovementCard(new MovementCard(Directions.NODIRECTION, 1, 1), roboRally.getPlayers().get(0));
        assertEquals(0, roboRally.getPlayers().get(0).getRobot().getPosition().getX());
        assertEquals(0, roboRally.getPlayers().get(0).getRobot().getPosition().getY());
        assertEquals(1, roboRally.getPlayers().get(0).getRobot().getHealthPoints());
    }

    @Test
    public void robotMoveOutsideMapConveyorBelt() {
        roboRally.playMovementCard(new MovementCard(Directions.DOWN, 1, 1), roboRally.getPlayers().get(1));
        assertEquals(2, roboRally.getPlayers().get(1).getRobot().getPosition().getX());
        assertEquals(0, roboRally.getPlayers().get(1).getRobot().getPosition().getY());
        assertEquals(2, roboRally.getPlayers().get(1).getRobot().getHealthPoints());
    }

    @Test
    public void robotCollide() {
        roboRally.playMovementCard(new MovementCard(Directions.NODIRECTION, 2, 1), roboRally.getPlayers().get(1));
        assertEquals(1, roboRally.getPlayers().get(1).getRobot().getPosition().getX());
        assertEquals(0, roboRally.getPlayers().get(1).getRobot().getPosition().getY());
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
