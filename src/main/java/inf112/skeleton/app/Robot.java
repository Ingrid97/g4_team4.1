package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

public class Robot implements IRobot{
    private Position positionOfRobot;
    private int healthPoints;
    private boolean alive;


    public Robot(int x, int y){
        this.positionOfRobot = new Position(x, y);
        this.healthPoints = 3;
        this.alive = true;
    }


    @Override
    public boolean isKillable() {
        return healthPoints == 1;
    }

    @Override
    public Position getPosition() {
        return this.positionOfRobot;
    }

    @Override
    public boolean isAlive() {
        return this.healthPoints > 0;
    }

    @Override
    public void shootLaser(Robot theRobotBeingShot) {
        theRobotBeingShot.takeDamage();

    }

    @Override
    public int getX() {
        return this.positionOfRobot.getX();
    }

    @Override
    public int getY() {
        return this.positionOfRobot.getY();
    }

    public Position getPositionOfRobot()  {
        return this.positionOfRobot;
    }

    public void move (Directions direction) {
        this.positionOfRobot = this.positionOfRobot.moveDirection(direction);
    }

    private void takeDamage () {
        if (this.isKillable()) this.alive = false;
        this.healthPoints--;
    }

    @Override
    public int color() {
        return 0;

    }
}
