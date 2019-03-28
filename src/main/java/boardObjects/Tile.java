package boardObjects;//Created by ingridjohansen on 05/02/2019.

import inf112.skeleton.app.Position;

public class Tile implements IBoardObject {
    int x;
    int y;

    private Position position;

    /*
     * Is an object on the board where there is nothing in the tile!
     */
    public Tile(int x, int y) {
        this.position = new Position(x, y);
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    public Position getPosition() {
        return this.position;
    }

    @Override
    public int color() {
        return 0;
    }
}
