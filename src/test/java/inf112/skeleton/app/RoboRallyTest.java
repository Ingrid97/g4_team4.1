package inf112.skeleton.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoboRallyTest {
    @Test
    public void turningRightUp() {
        assertEquals(RoboRally.turningRight(Directions.UP), Directions.RIGHT);
    }

    @Test
    public void turningRightDown() {
        assertEquals(RoboRally.turningRight(Directions.DOWN), Directions.LEFT);
    }

    @Test
    public void turningRightLeft() {
        assertEquals(RoboRally.turningRight(Directions.LEFT), Directions.UP);
    }

    @Test
    public void turningRight() {
        assertEquals(RoboRally.turningRight(Directions.RIGHT), Directions.DOWN);
    }

    @Test
    public void turningLeftUp() {
        assertEquals(RoboRally.turningLeft(Directions.UP), Directions.LEFT);
    }

    @Test
    public void turningLeftDown() {
        assertEquals(RoboRally.turningLeft(Directions.DOWN), Directions.RIGHT);
    }

    @Test
    public void turningLeftRight() {
        assertEquals(RoboRally.turningLeft(Directions.RIGHT), Directions.UP);
    }

    @Test
    public void turningLeft() {
        assertEquals(RoboRally.turningLeft(Directions.LEFT), Directions.DOWN);
    }

    @Test
    public void backingUp() {
        Player player = new Player(0, new Robot(5, 5, Directions.UP));
        MovementCard movementCard = new MovementCard(Directions.DOWN, 1, 0);
        assertEquals(RoboRally.backingUpOrTurning180Degrees(movementCard, player, new Position(5, 5), new Position(100, 100)).getY(), 4);
    }

    @Test
    public void uTurn() {
        Player player = new Player(0, new Robot(5, 5, Directions.UP));
        MovementCard movementCard = new MovementCard(Directions.DOWN, 0, 0);
        Position pos = RoboRally.backingUpOrTurning180Degrees(movementCard, player, new Position(5, 5), new Position(100, 100));
        assertEquals(pos.getY(), 5);
        assertEquals(player.getRobot().getDirection(), Directions.DOWN);
    }

    @Test
    public void movingForwardUp() {
        Player player = new Player(0, new Robot(5, 5, Directions.UP));
        Position pos = RoboRally.movingForward(player, new Position(5, 5));
        assertEquals(pos.getY(), 6);
    }

    @Test
    public void movingForwardRight() {
        Player player = new Player(0, new Robot(5, 5, Directions.RIGHT));
        Position pos = RoboRally.movingForward(player, new Position(5, 5));
        assertEquals(pos.getX(), 6);
    }

    @Test
    public void movingForwardLeft() {
        Player player = new Player(0, new Robot(5, 5, Directions.LEFT));
        Position pos = RoboRally.movingForward(player, new Position(5, 5));
        assertEquals(pos.getX(), 4);
    }

    @Test
    public void movingForwardDown() {
        Player player = new Player(0, new Robot(5, 5, Directions.DOWN));
        Position pos = RoboRally.movingForward(player, new Position(5, 5));
        assertEquals(pos.getY(), 4);
    }
}
