package main.java.inf112.skeleton.app;

public interface IRobot {

        /*IRobot can get killed*/
        public boolean isKillable();

        /* Gets the position of the object */
        Position getPostion();

        /*Checking if the robot is Alive*/
        public boolean isAlive();

        /*Method for killing other robots*/
        public void shot();



    }

