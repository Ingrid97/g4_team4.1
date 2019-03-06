package inf112.skeleton.app;//Created by ingridjohansen on 06/02/2019.

public class Conveyor_belt implements IBoardObject{

    boolean isBlueBelt;
    boolean isYelloBelt;
    Directions dir;
    int placeDir;

    public Conveyor_belt(int x, int y){
        isBlueBelt = false;
        isYelloBelt = false;

    }

    public void isBlueBelt(){
        isBlueBelt = true;
    }

    public void isYelloBelt(){
        isYelloBelt = true;
    }

    public int move(){
        if (isYelloBelt)
            return 1;
        return 2;
    }

    public void getDirection(){

    }

    public void setPlaceDir(int d){
        this.placeDir = d;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int color() {
        return 0;
    }
}
