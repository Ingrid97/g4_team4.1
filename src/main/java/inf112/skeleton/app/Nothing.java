package inf112.skeleton.app;//Created by ingridjohansen on 05/02/2019.

public class Nothing implements IBoardObject{
    int x;
    int y;

    public Nothing(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int color() {
        return 0;
    }
}
