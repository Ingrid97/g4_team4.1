package inf112.skeleton.app;

/**
 * This class is useful for assigning x and y positions in the map
 */
public class Position {

    private int x;
    private int y;

    /**
     * Constructer that object can correspond with
     *
     * @param x
     * @param y
     */
    public Position(int x, int y) throws IllegalArgumentException {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("position values cannot be less then zero : x = " + x + " y = " + y);
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Create a copy of the this position and makes a copy
     *
     * @return position
     */
    public Position copy() {
        return new Position(x, y);
    }


    /**
     * get X coordinate
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * get Y coordinate
     *
     * @return y
     */
    public int getY() {
        return y;
    }


    /**
     * if Y and X is bigger than zero make a move
     *
     * @param dir
     * @return canGo
     */
    public boolean canGoHere(Directions dir) {
        if (dir == Directions.RIGHT)
            return x > 0;

        if (dir == Directions.DOWN)
            return y > 0;

        return true;
    }

    /**
     * Robot movement position
     *
     * @param dir
     * @return position
     */
    public Position moveDirection(Directions dir) {
        switch (dir) {
            case RIGHT:
                return new Position(x + 1, y);
            case LEFT:
                return new Position(x - 1, y);
            case UP:
                return new Position(x, y + 1);
            case DOWN:
                return new Position(x, y - 1);
            default:
                throw new IllegalArgumentException();
        }
    }


}

