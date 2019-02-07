package inf112.skeleton.app;

public interface IRobot extends IBoardObject {

        /*IRobot can get killed*/
        public boolean isKillable();

        /* Gets the position of the object */
        Position getPosition();

        /*Checking if the robot is Alive*/
        public boolean isAlive();

        /*Method for killing other robots*/
        public void shootLaser(Robot theRobotBeingShot);

        /**
         * Move the robot in a given Direction
         * @param direction
         */
        public void move (Directions direction);

        /**
         *
         * @return The current Position of the robot
         */
        public Position getPositionOfRobot();


}

