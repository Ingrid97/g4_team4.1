package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

public class Robot implements IRobot{

    public Robot(int x, int y){

    }


    @Override
    public boolean isKillable() {
        return false;
    }

    @Override
    public Position getPostion() {
        return null;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void shot() {}

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
