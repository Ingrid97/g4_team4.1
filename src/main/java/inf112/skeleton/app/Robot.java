package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

public class Robot implements IRobot{
    private Position positionOfRobot;
    private Position backUpPosition;
    private int healthPoints;
    private boolean alive;
    private int laserStrength;
    private Directions direction;


    public Robot(int x, int y, Directions direction){
        this.positionOfRobot = new Position(x, y);
        this.backUpPosition = positionOfRobot;
        this.healthPoints = 3;
        this.alive = true;
        this.laserStrength = 1;
        this.direction = direction;
    }


    @Override
    public Position getPosition() {
        return this.positionOfRobot;
    }

    public int getHealthPoints() { return this.healthPoints;}

    public Position getBackUpPosition() {
        return backUpPosition;
    }

    @Override
    public boolean isAlive() {
        return this.alive;
    }

    @Override
    public void shootLaser(Robot theRobotBeingShot) {
        theRobotBeingShot.takeDamage(this.laserStrength);
    }

    public void dropBackUpAtCurrentPosition() {
        this.backUpPosition = this.positionOfRobot;
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

    public void move (int numberOfSteps) {
        for (int i = 0; i < numberOfSteps; i++) {
            this.positionOfRobot = this.positionOfRobot.moveDirection(this.direction);
        }
    }

    private void takeDamage (int laserStrength) {

        this.healthPoints -= laserStrength;

        if (healthPoints < 1) {
            this.alive = false;
            this.positionOfRobot = this.backUpPosition;
        }
    }

    @Override
    public int color() {
        return 0;
    }
}
