package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.input.Keyboard;

import java.awt.event.KeyEvent;

import static org.junit.Assert.*;


public class GametTest {
    private MapGUI map;
    private Robot robot;



    //Set up game and objects for test in this class
    @Before

    public static void setupMapBefore() throws Exception{
        Map map = new Map(10, 20);
    }







    @Test

    //TODO check if flags been visited
    public void playerCollectsFlags(){
        Player robot = new Player(10, 1);


       //one flag

      //  assertEquals(2, flagsWhichHasBeenVisited.getFlagsVisited());

    }



    //TODO check if an KeyBoardEvent is a legal input
   @Test (expected = IllegalArgumentException.class)

   public void illegalKeyBoardCode(){

   }



    @Test

    //TODO check if legal inputs are okay

    public void playerMovement(KeyEvent key){
        Player testRobot = new Player(2, )




    }




    @Test

    //Todo check if player goes through walls

    public void robotDoesNotGoThroughWalls(){
       Player testRobot = new Player(0, 5, 5, Directions.DOWN);


       assertFalse(testRobot.noticeWalls(Directions.DOWN));


       Player noticeWalls = new Player(int numberOfFlags, Robot robot);
        robot.keyPressed(Directions.RIGHT);

        Robot robot = new Robot(5, 5, Directions.UP);

     assertFalse(noticeWalls.canGO(Directions.UP, instanceOf Wall));


    }

    @Test

    //TODO make the method and then make the test

    public void givingCardsCorrectly(){

    }

    @Test

    //TODO make the method and then make the test

    public void  testMemoryCapacityForThisPlayer(){

    }



}
