package inf112.skeleton.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoboRallyTest {

    public RoboRally roboRally;

    @Before
    public void initiate() {
        this.roboRally = new RoboRally();
    }

    @After
    public void dump() {
        this.roboRally = null;
    }

    @Test
    public void turningRightUp() {
        assertEquals(roboRally.turningRight(Directions.UP), Directions.RIGHT);
    }

    @Test
    public void turningRightDown() {
        assertEquals(roboRally.turningRight(Directions.DOWN), Directions.LEFT);
    }

    @Test
    public void turningRightLeft() {
        assertEquals(roboRally.turningRight(Directions.LEFT), Directions.UP);
    }

    @Test
    public void turningRight() {
        assertEquals(roboRally.turningRight(Directions.RIGHT), Directions.DOWN);
    }

    @Test
    public void turningLeftUp() {
        assertEquals(roboRally.turningLeft(Directions.UP), Directions.LEFT);
    }

    @Test
    public void turningLeftDown() {
        assertEquals(roboRally.turningLeft(Directions.DOWN), Directions.RIGHT);
    }

    @Test
    public void turningLeftRight() {
        assertEquals(roboRally.turningLeft(Directions.RIGHT), Directions.UP);
    }

    @Test
    public void turningLeft() {
        assertEquals(roboRally.turningLeft(Directions.LEFT), Directions.DOWN);
    }

    @Test
    public void backingUp() {
        Player player = new Player(0, new Robot(5, 5, Directions.UP));
        MovementCard movementCard = new MovementCard(Directions.DOWN, 1, 0);
        assertEquals(roboRally.backingUpOrTurning180Degrees(movementCard, player, new Position(5, 5), new Position(100, 100)).getY(), 4);
    }

    @Test
    public void uTurn() {
        Player player = new Player(0, new Robot(5, 5, Directions.UP));
        MovementCard movementCard = new MovementCard(Directions.DOWN, 0, 0);
        Position pos = roboRally.backingUpOrTurning180Degrees(movementCard, player, new Position(5, 5), new Position(100, 100));
        assertEquals(pos.getY(), 5);
        assertEquals(player.getRobot().getDirection(), Directions.DOWN);
    }

    @Test
    public void movingForwardUp() {
        Player player = new Player(0, new Robot(5, 5, Directions.UP));
        Position pos = roboRally.movingForward(player, new Position(5, 5));
        assertEquals(pos.getY(), 6);
    }

    @Test
    public void movingForwardRight() {
        Player player = new Player(0, new Robot(5, 5, Directions.RIGHT));
        Position pos = roboRally.movingForward(player, new Position(5, 5));
        assertEquals(pos.getX(), 6);
    }

    @Test
    public void movingForwardLeft() {
        Player player = new Player(0, new Robot(5, 5, Directions.LEFT));
        Position pos = roboRally.movingForward(player, new Position(5, 5));
        assertEquals(pos.getX(), 4);
    }

    @Test
    public void movingForwardDown() {
        Player player = new Player(0, new Robot(5, 5, Directions.DOWN));
        Position pos = roboRally.movingForward(player, new Position(5, 5));
        assertEquals(pos.getY(), 4);
    }
}
