package boardObjects;//Created by ingridjohansen on 06/02/2019.

import inf112.skeleton.app.Directions;
import inf112.skeleton.app.Position;

public class Conveyor_belt implements IBoardObject{

    public boolean isBlueBelt;
    private Directions dir;
    private int placeDir;
    private boolean isYellowBelt;
    private Position position;
    public int pictureDir;

    public Conveyor_belt(int x, int y){
        isBlueBelt = false;
        isYellowBelt = false;
        this.position = new Position(x, y);
    }

    public void isBlueBelt(){
        isBlueBelt = true;
    }

    public void isYellowBelt(){
        isYellowBelt = true;
    }

    public int move(){
        if (isYellowBelt)
            return 1;
        return 2;
    }

    public Directions getDirection(){
        switch (pictureDir) {
            case 1:
                return Directions.LEFT;
            case 2:
                return Directions.UP;
            case 3:
                return Directions.RIGHT;
            default:
                return Directions.DOWN;
        }
    }

    public void setPlaceDir(int d){
        this.placeDir = d;
    }

    @Override
    public int getX() {
        return this.position.getX();
    }

    public void setPictureDir(int d){
        this.pictureDir = d;
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
