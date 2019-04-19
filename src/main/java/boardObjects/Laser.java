package boardObjects;//Created by ingridjohansen on 06/02/2019.

import inf112.skeleton.app.Position;

public class Laser implements IBoardObject{

    private Position position;
    public int pictureDir;

    public Laser(int x, int y){
        this.position = new Position(x, y);
        this.pictureDir = 0;
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


    public void setPictureDir(int d) {
        this.pictureDir = d;
    }

    @Override
    public int color() {
        return 0;
    }
}
