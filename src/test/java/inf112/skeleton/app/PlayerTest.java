package inf112.skeleton.app;

import org.junit.Before;

/* Test for player */


public class PlayerTest {
    private static Wall wallsOnTheMap;


    //Method used to set up a map that can be used in all test
    @Before
    //can be smart to creat map here so that i don't need it to do so in every test
    public static Map setupMapBefore(Map map) {
        map = new Map(10, 20);
        return map;
    }
/*

    @Test

    //TODO check if legal inputs are okay

    public void playerMovementUP() {
        //creating map
        map = setupMapBefore(map);

        //Creating robot and  a player
        Robot robot = new Robot(10, 20, Directions.UP);

        for(int x = 0; x > 10; x++ ){
            for(int y = 0; y > 20; y++ ){
                assertEquals(map.getBoardObject(new Position(x, y)) instanceof Robot);
            }
        }

        map.add(new Robot())
        assertFalse(map.getBoardObject(new Position(10, 20)) instanceof Wall);


    }
    
    

    @Test

    public void playerMovmentDown() {
        map = setupMapBefore(map);

        Robot robot = new Robot(10,20, Directions.DOWN );
        Player testRobot = new Player(0, robot);
        for(int x = 0; x > 10; x++ ){
            for(int y = 0; y > 20; y++ ){
                assertEquals(map.getBoardObject(new Position(x, y)) instanceof Player);
            }
        }
        map.add(Player(null, testRobot));
        assertFalse(map.getBoardObject(new Position(10, 20)) instanceof Wall);


    }

    @Test

    public void playerMovmentRight() {
        map = setupMapBefore(map);

        Robot robot = new Robot(10, 20, Directions.RIGHT);
        Player testRobot = new Player(0, robot);
        for(int x = 0; x > 10; x++ ){
            for(int y = 0; y > 20; y++ ){
                assertEquals(map.getBoardObject(new Position(x, y)) instanceof Player);
            }
        }
        map.add(Player(null, testRobot));
        assertFalse(map.getBoardObject(new Position(10,20)) instanceof  Wall);
    }

    private void assertEquals(boolean b) {
    }


    @Test

    public void playerMovmentLeft() {
     map = setupMapBefore(map);

     Robot robot = new Robot(10,20, Directions.LEFT);
        Player testRobot = new Player(0, robot);
        for(int x = 0; x > 10; x++ ){
            for(int y = 0; y > 20; y++ ){
                assertEquals(map.getBoardObject(new Position(x, y)) instanceof Player);
            }
        }
        map.add(Player(null, testRobot));
        assertFalse(map.getBoardObject(new Position(10,20)) instanceof Wall);
    }









    //TODO check if an KeyBoardEvent is a legal input
    @Test (expected = IllegalArgumentException.class)

    public void illegalKeyBoardCode(KeyEvent keyEvent) {



        @Test

        //TODO make the method and then make the test

        public void givingCardsCorrectly () {

        }

        @Test

        //TODO make the method and then make the test

        public void testMemoryCapacityForThisPlayer() {

        }


    }
   
*/
}
