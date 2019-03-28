package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class MapGUI extends ApplicationAdapter {

    private Texture robotImage, voidImage, yellowConveyor_beltImage, blueConveyor_beltImage, laserImage, wrenchImage, wrench_hammer, rotatin_plateImage, flagImage, nothingImage, cardTester;

    private Sound dropSound;
    private Music rainMusic;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Rectangle[][] tile;
    private Rectangle[] robot;
    private Rectangle[] cards;
    private int roboid;
    public static Map map;
    public static boolean drawnTable = false;
    public static int witchRobot = 0;
    public ArrayList<Player> listOfPLayers;

    public MapGUI(Map map, ArrayList<Player> listOfPLayers) {
        MapGUI.map = map;
        this.listOfPLayers = listOfPLayers;
    }

    public void updateMap(Map map) {
        MapGUI.map = map;
    }

    @Override
    public void create() {


        // load the images for the objects on the map, 64x64 pixels each
        /*robotImage = getImage("wall_e.png");
        voidImage = getImage("void.png");
        yellowConveyor_beltImage = getImage("yellow_arrow.png");
        blueConveyor_beltImage = getImage("blue_arrow.png");
        laserImage = getImage("laser.png");
        wrenchImage = getImage("Wrench.png");
        wrench_hammer = getImage("Wrench_hammer.png");
        rotatin_plateImage = getImage("Rotating_bond.png");
        flagImage = getImage("flag.png");
        nothingImage = getImage("nothing.png");*/

        //Testing with other picture
        robotImage = getImage("r.png");
        voidImage = getImage("v.png");
        yellowConveyor_beltImage = getImage("y.png");
        blueConveyor_beltImage = getImage("b.png");
        laserImage = getImage("l.png");
        wrenchImage = getImage("s.png");
        wrench_hammer = getImage("s_h.png");
        rotatin_plateImage = getImage("g.png");
        flagImage = getImage("f.png");
        nothingImage = getImage("n.png");
        cardTester = getImage("card_Tester.png");

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 940);

        batch = new SpriteBatch();


        // create a list of Rectangles to logically represent the objects
        tile = new Rectangle[10][10];
        robot = new Rectangle[10];
        cards = new Rectangle[9];

        for (int i = 0; i < 10; i++) {
            robot[i] = new Rectangle();
            robot[i].x = -1;
            robot[i].y = -1;
            robot[i].height = 64;
            robot[i].width = 64;
        }


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tile[i][j] = new Rectangle();
                tile[i][j].x = i * 64;
                tile[i][j].y = j * 64;
                tile[i][j].width = 64;
                tile[i][j].height = 64;
            }
        }

        for (int i = 0; i < 9; i++) {
            cards[i] = new Rectangle();
            cards[i].x = 32 + i * 64;
            cards[i].y = 700;
            cards[i].height = 96;
            cards[i].width = 64;
        }


    }

    public Texture getImage(String s) {
        return new Texture(Gdx.files.internal(s));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the board
        batch.begin();


        drawTable();

        roboid = 0;
        // For loop som går gjennom players og position til robotene
        for (int i = 0; i < listOfPLayers.size(); i++) {
            batch.draw(robotImage, listOfPLayers.get(i).getRobot().getX() * 64, listOfPLayers.get(i).getRobot().getY() * 64);
        }

        /*
        for (int i = 0, k = 9; i < 2; i++, k--) {
            batch.draw(robotImage, robot[roboid].x, robot[roboid].y);
            roboid++;
        }
        */

        for (int i = 0; i < 9; i++) {
            batch.draw(cardTester, cards[i].x, cards[i].y);
        }

        batch.end();

        //stuff for moving
        witchRobot++;
        if (witchRobot > 2) {
            witchRobot = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) robot[1].y += 64;
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) robot[1].y -= 64;
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) robot[1].x -= 64;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) robot[1].x += 64;
    }

    public void drawTable() {


        drawnTable = true;
        roboid = 0;

        /* TODO! Logic in drawTable vs makeMap in RoboRally
         * Here there is one type of logic of how to place objects on the map, this logic is not the same
         * which is used when placing the robots at the map. Therefore the start placement of the robot
         * is inconsistent, furthermore the movement is not correct
         */
        for (int i = 0; i < 10; i++) {
            for (int j = 0, k = 9; j < 10; j++, k--) {
                if (map.getBoardObject(new Position(j, i)) instanceof Robot) {

                    batch.draw(nothingImage, tile[i][k].x, tile[i][k].y);
                    /*
                    if (robot[roboid].x == -1) {
                        robot[roboid].x = i * 64;
                        robot[roboid].y = k * 64;

                    batch.draw(robotImage, robot[roboid].x, robot[roboid].y); */
                    roboid++;
                } else if (map.getBoardObject(new Position(j, i)) instanceof Void) {
                    batch.draw(voidImage, tile[i][k].x, tile[i][k].y);
                } else if (map.getBoardObject(new Position(j, i)) instanceof Laser) {
                    batch.draw(laserImage, tile[i][k].x, tile[i][k].y);
                } else if (map.getBoardObject(new Position(j, i)) instanceof Conveyor_belt) {
                    Conveyor_belt c = (Conveyor_belt) map.getBoardObject(new Position(j, i));
                    if (c.isBlueBelt)
                        batch.draw(blueConveyor_beltImage, tile[i][k].x, tile[i][k].y);
                    else
                        batch.draw(yellowConveyor_beltImage, tile[i][k].x, tile[i][k].y);
                } else if (map.getBoardObject(new Position(j, i)) instanceof Wrench) {
                    batch.draw(wrenchImage, tile[i][k].x, tile[i][k].y);
                } else if (map.getBoardObject(new Position(j, i)) instanceof Wrench_hammer) {
                    batch.draw(wrench_hammer, tile[i][k].x, tile[i][k].y);
                } else if (map.getBoardObject(new Position(j, i)) instanceof Flag) {
                    batch.draw(flagImage, tile[i][k].x, tile[i][k].y);
                } else if (map.getBoardObject(new Position(j, i)) instanceof Rotating_belt) {
                    batch.draw(rotatin_plateImage, tile[i][k].x, tile[i][k].y);
                } else {
                    batch.draw(nothingImage, tile[i][k].x, tile[i][k].y);
                }
            }
        }

    }

    @Override
    public void dispose() {
        // dispose of all
        robotImage.dispose();
        voidImage.dispose();
        blueConveyor_beltImage.dispose();
        yellowConveyor_beltImage.dispose();
        laserImage.dispose();
        nothingImage.dispose();
        rotatin_plateImage.dispose();
        wrench_hammer.dispose();
        wrenchImage.dispose();
        flagImage.dispose();
        batch.dispose();
    }
}
