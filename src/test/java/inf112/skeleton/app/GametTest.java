package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import org.junit.Test;
import static org.junit.Assert.*;


public class GametTest {
    private MapGUI map;


    //Testing if keyboadinput are regestered
    @Test

    public void keyBoardInputTest{

    }




    @Test

    public void robotDoesNotGoThroughWalls(){
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
