package inf112.skeleton.app;//Created by ingridjohansen on 06/02/2019.

public class Conveyor_belt implements IBoardObject{

    boolean isBlueBelt;
    Directions dir;
    int placeDir;
    boolean isYellowBelt;
    private Position position;

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
        return this.dir;
    }

    public void setPlaceDir(int d){
        this.placeDir = d;
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
