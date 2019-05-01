package inf112.skeleton.app;

public class CalculatePosition {

    /**
     * Moving a robot forward,
     *
     * @param currentPos current position of robot
     * @param direction  direction to move
     * @return the final new position, ex the third position if the movCard said 3 steps
     * @throws IllegalArgumentException throws if the robot moves outside the board
     */
    public static Position movingForward(Position currentPos, Directions direction) throws IllegalArgumentException {
        Position newPos;
        switch (direction) {
            case UP:
                newPos = new Position((currentPos.getX() - 1), currentPos.getY());
                break;
            case RIGHT:
                newPos = new Position(currentPos.getX(), (currentPos.getY() + 1));
                break;
            case LEFT:
                newPos = new Position(currentPos.getX(), (currentPos.getY() - 1));
                break;
            default:
                newPos = new Position((currentPos.getX() + 1), currentPos.getY());
                break;
        }
        return newPos;
    }

    /**
     * Moving a robot backward or turns it 180 degrees depends on movCard
     *
     * @param movCard movement card for knowing if to turn or move
     * @param player  player to move
     * @return the final new position
     * @throws IllegalArgumentException if robot is outside map
     */
    // if number of steps is 1 backup else trun 180 Degress
    public static Position Uturn(MovementCard movCard, Player player) throws IllegalArgumentException {
        Position newPos;
        Position currentPos = player.getRobot().getPosition();
        if (movCard.getNumberOfSteps() == 1) {
            Directions direction = player.getRobot().getDirection();
            switch (direction) {
                case UP:
                    newPos = new Position((currentPos.getX() + 1), currentPos.getY());
                    break;
                case RIGHT:
                    newPos = new Position(currentPos.getX(), (currentPos.getY() - 1));
                    break;
                case LEFT:
                    newPos = new Position(currentPos.getX(), (currentPos.getY() + 1));
                    break;
                default:
                    newPos = new Position((currentPos.getX() - 1), currentPos.getY());
                    break;
            }
        } else {
            Directions direction = player.getRobot().getDirection();
            newPos = currentPos;
            switch (direction) {
                case UP:
                    player.getRobot().setDirection(Directions.DOWN);
                    break;
                case RIGHT:
                    player.getRobot().setDirection(Directions.LEFT);
                    break;
                case LEFT:
                    player.getRobot().setDirection(Directions.RIGHT);
                    break;
                case DOWN:
                    player.getRobot().setDirection(Directions.UP);
                    break;
            }
        }
        return newPos;
    }

    /**
     * calculating the new direction for a robot given a direction it returns the direction left for the current direction
     *
     * @param direction current direction
     * @return direction left from current
     * @throws IllegalArgumentException
     */
    public static Directions turningLeft(Directions direction) throws IllegalArgumentException {
        switch (direction) {
            case UP:
                return Directions.LEFT;
            case RIGHT:
                return Directions.UP;
            case LEFT:
                return Directions.DOWN;
            default:
                return Directions.RIGHT;
        }
    }

    /**
     * calculating the new direction for a robot given a direction it returns the direction right for the current direction
     *
     * @param direction current direction
     * @return direction right from current
     * @throws IllegalArgumentException
     */
    public static Directions turningRight(Directions direction) throws IllegalArgumentException {
        switch (direction) {
            case UP:
                return Directions.RIGHT;
            case RIGHT:
                return Directions.DOWN;
            case LEFT:
                return Directions.UP;
            default:
                return Directions.LEFT;
        }
    }

}
