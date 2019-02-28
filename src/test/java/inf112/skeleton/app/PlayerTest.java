package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.input.Keyboard;

import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

/* Test for player */


public class PlayerTest {
    private MapGUI map;
    private static Wall wallsOnTheMap;



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


        Player testRobot = new Player(5, 5,5, Directions.UP  );
        testRobot.keyPressed(KeyEvent.VK_W);
        testRobot.keyReleased(KeyEvent.VK_W);

        assertTrue(true, movingUP);


    }




    @Test

    //Todo check if player goes through walls

    public void robotDoesNotGoThroughWallsDown(){
       Player testRobot = new Player(0, 5, 5, Directions.DOWN);
      (testRobot.noticeWalls(Directions.DOWN))

      }

       if(IBoardObject  = instanceOf Wall){
       assertFalse(testRobot.canGO = true);
       assertTrue(testRobot.canGO = false);


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
