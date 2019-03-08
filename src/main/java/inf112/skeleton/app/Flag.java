package inf112.skeleton.app;//Created by ingridjohansen on 06/02/2019.

public class Flag implements IBoardObject{

    private Position position;

    public Flag(int x, int y){
        this.position = new Position(x, y);
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

    @Override
    public int color() {
        return 0;
    }
}
