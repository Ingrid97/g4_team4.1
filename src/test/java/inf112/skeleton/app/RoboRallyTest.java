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
        Player player = new Player(0, new Robot(5, 5, Directions.UP));
        Position pos = CalculatePosition.movingForward(new Position(5, 5), Directions.UP);
        assertEquals(pos.getX(), 4);
    }

    @Test
    public void movingForwardRight() {
        Player player = new Player(0, new Robot(5, 5, Directions.RIGHT));
        Position pos = CalculatePosition.movingForward(new Position(5, 5), Directions.RIGHT);
        assertEquals(pos.getY(), 6);
    }

    @Test
    public void movingForwardLeft() {
        Player player = new Player(0, new Robot(5, 5, Directions.LEFT));
        Position pos = CalculatePosition.movingForward(new Position(5, 5), Directions.LEFT);
        assertEquals(pos.getY(), 4);
    }

    @Test
    public void movingForwardDown() {
        Player player = new Player(0, new Robot(5, 5, Directions.DOWN));
        Position pos = CalculatePosition.movingForward(new Position(5, 5), Directions.DOWN);
        assertEquals(pos.getX(), 6);
    }
}
