package inf112.skeleton.app;

public interface IRobot extends IBoardObject {


        /**
         *
         * @return Position of the robot
         */
        Position getPosition();

        /**
         *
         * @return A boolean variable checking if the robot is alive or dead
         */
        boolean isAlive();

        /**
         * Shoot your deadly laser at a robot
         * @param theRobotBeingShot
         */
        void shootLaser(Robot theRobotBeingShot);

        /**
         * Move the robot in a given Direction
         * @param direction
         */
        void move (int numberOfSteps);

        /**
         *
         * @return The current Position of the robot
         */
        Position getPositionOfRobot();


}

