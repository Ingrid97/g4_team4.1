package inf112.skeleton.app;//Created by ingridjohansen on 06/02/2019.

public class Conveyor_belt implements IBoardObject{

    boolean isBlueBelt;
    boolean isYellowBelt;

    public Conveyor_belt(int x, int y){
        isBlueBelt = false;
        isYellowBelt = false;
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
