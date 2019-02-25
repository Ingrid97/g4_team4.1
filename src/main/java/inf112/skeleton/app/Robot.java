package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

public class Robot implements IRobot{
    private Position positionOfRobot;
    private Position backUpPosition;
    private int healthPoints;
    private int memoryCapacity;
    private boolean alive;
    private int laserStrength;
    private Directions direction;
    private Robot robot;


    public Robot(int x, int y, Directions direction){
        this.positionOfRobot = new Position(x, y);
        this.backUpPosition = positionOfRobot;
        this.healthPoints = 3;
        this.memoryCapacity = 9;
        this.alive = true;
        this.laserStrength = 1;
        this.direction = direction;
    }



    // Getters
    @Override
    public Position getPosition() {
        return this.positionOfRobot;
    }

    public int getHealthPoints() { return this.healthPoints;}

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

    public Position getPositionOfRobot()  {
        return this.positionOfRobot;
    }

    public int getMemoryCapacity() {
        return this.memoryCapacity;
    }

    // Setters
    public void setDirection(Directions direction) {
        this.direction = direction;
    }


    // Methods
    public void move (int numberOfSteps) {
        for (int i = 0; i < numberOfSteps; i++) {
            this.positionOfRobot = this.positionOfRobot.moveDirection(this.direction);
        }
        if (this.alive = false) this.positionOfRobot = backUpPosition;
    }
/*
    public void noticeOtherObjects(Robot robotObj){


        //check if the Robot can walk at a given position or if there is an obstacle there
        Position robotPos = this.positionOfRobot.moveDirection(this.direction);
        boolean robotCanGo = true;

        try{
            if(move(!= 0) && this.positionOfRobot.moveDirection(this, this.direction);
            IBoardObject obj =  this.robot.positionOfRobot.moveDirection(this, this.move());
            if(obj instanceof Wall){
                robotCanGo = false;
            }
        } catch(Exception e) {
            System.out.println("Can not go here");


        }
    }
*/
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

    private void takeDamage (int laserStrength) {

        this.memoryCapacity -= laserStrength;

        if (memoryCapacity < 1) {
            this.healthPoints--;
            this.memoryCapacity = 9;
            this.positionOfRobot = this.backUpPosition;
        }
        if (this.healthPoints < 1) {
            this.alive = false;
            this.positionOfRobot = this.backUpPosition;
        }
    }

    @Override
    public int color() {
        return 0;
    }
}
