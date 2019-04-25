package boardObjects;//Created by ingridjohansen on 06/02/2019.

import inf112.skeleton.app.Position;

public class Flag implements IBoardObject{

    private Position position;
    public final int identifier;

    /**
     * Starts the counting from 0, done this way for the use of boolean[] in Player
     *
     * @param x          x-coordinate
     * @param y          y-coordinate
     * @param identifier Is this flag 0, 1, or 2?
     */
    public Flag(int x, int y, int identifier) {
        this.position = new Position(x, y);
        this.identifier = identifier;
    }

    @Override
    public int getX() {
        return this.position.getX();
    }

    @Override
    public int getY() {
        return this.position.getY();
    }

    public Position getPosition() {
        return this.position;
    }

}
