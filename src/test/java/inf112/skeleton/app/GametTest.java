package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import org.junit.Test;
import org.lwjgl.input.Keyboard;

import static org.junit.Assert.*;


public class GametTest {
    private MapGUI map;

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

    public void keyBoardInputTest{


    }




    @Test

    //Todo check if player goes through walls

    public void robotDoesNotGoThroughWalls(){
       Player robot = new Player()
        robot.keyPressed(Directions.RIGHT);

        map =  new LwjglApplication(new MapGUI(), cfg);
        Robot robot = new Robot(5, 5, Directions.UP);


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
