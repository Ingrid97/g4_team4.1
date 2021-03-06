package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.


public class Robot implements IRobot {
    private Position positionOfRobot;
    private Position backUpPosition;
    private int healthPoints;
    private int memoryCapacity;
    private boolean alive;
    private int laserStrength;
    private Directions direction;
    private int pictureDir;
    private final int memCap = 9;
    private boolean powerdown;
    private Player owner;

    public Robot(int x, int y, Directions direction) {
        this.positionOfRobot = new Position(x, y);
        this.backUpPosition = positionOfRobot;
        this.healthPoints = 3;
        this.memoryCapacity = memCap;
        this.laserStrength = 1;
        this.direction = direction;
        this.pictureDir = 1;
        this.alive = true;
        this.powerdown = false;

    }

    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    // Getters
    @Override
    public Position getPosition() {
        return this.positionOfRobot;
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    public Position getBackUpPosition() {
        return backUpPosition;
    }

    @Override
    public int getX() {
        return this.positionOfRobot.getX();
    }

    @Override
    public int getY() {
        return this.positionOfRobot.getY();
    }

    public Position getPositionOfRobot() {
        return this.positionOfRobot;
    }

    public int getMemoryCapacity() {
        return this.memoryCapacity;
    }

    public Directions getDirection() {
        return direction;
    }

    public int getPictureDir() {
        return pictureDir;
    }

    public boolean getPowerDown(){
        return this.powerdown;
    }


    // Setters
    public void setDirection(Directions direction) {
        this.direction = direction;
        switch (direction) {
            case LEFT:
                this.pictureDir = 0;
                break;
            case UP:
                this.pictureDir = 1;
                break;
            case RIGHT:
                this.pictureDir = 2;
                break;
            default:
                this.pictureDir = 3;
        }
    }

    public void setPosition(Position position) {
        this.positionOfRobot = position;
    }

    public void setMaxMemCap() {
        this.memoryCapacity = memCap;
    }

    public void takePowerdown() {
        this.powerdown = true;
    }

    public void finishPowerdown() {
        this.powerdown = false;
    }

    /**
     * Move the robot when it has walked out of bounds / in a pit
     * It is not needed to call this method if the robot is being shot dead by a laser from another robot / the map
     */
    public void setPositionToBackUp() {

        if (this.healthPoints > 1) {
            healthPoints--;
            this.memoryCapacity = memCap;
            this.alive = true;

            this.positionOfRobot = this.backUpPosition;
            this.direction = Directions.UP;
        } else {
            this.alive = false;
        }
    }


    // Methods
    public void move(int numberOfSteps) {
        for (int i = 0; i < numberOfSteps; i++) {
            this.positionOfRobot = this.positionOfRobot.moveDirection(this.direction);
        }
        if (this.alive = false) this.positionOfRobot = backUpPosition;


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

    public void takeDamage(int laserStrength) {

        this.memoryCapacity -= laserStrength;

        if (memoryCapacity < 1) {
            this.healthPoints--;
            this.memoryCapacity = memCap;
            this.positionOfRobot = this.backUpPosition;
        }
        if (this.healthPoints < 1) {
            this.alive = false;
            this.positionOfRobot = this.backUpPosition;
        }
    }

}
