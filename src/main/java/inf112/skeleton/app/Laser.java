package inf112.skeleton.app;//Created by ingridjohansen on 06/02/2019.

public class Laser implements IBoardObject{
private Position position;
    public Laser(int x, int y){
    this.position = new Position(x, y);
    }


    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    public Position getPosition() {
        return this.position;
    }

    @Override
    public int color() {
        return 0;
    }
}
