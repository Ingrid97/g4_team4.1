package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import boardObjects.*;
import boardObjects.Void;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class MapGUI extends ApplicationAdapter {

    private Texture robotImage, voidImage, wrenchImage, wrench_hammer, rotatin_plateImage, flagImage, nothingImage, cardTester;
    private Texture blueConveyor_beltImage1, blueConveyor_beltImage2, blueConveyor_beltImage3, blueConveyor_beltImage4;
    private Texture yellowConveyor_beltImage1, yellowConveyor_beltImage2, yellowConveyor_beltImage3, yellowConveyor_beltImage4;
    private Texture laserImage1, laserImage2, laserImage3, laserImage4, laserImage5, laserImage6;

    //private Texture[] lasers = {laserImage1, laserImage2, laserImage3, laserImage4, laserImage5, laserImage6};
    private Sound dropSound;
    private Music rainMusic;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Rectangle[][] tile;
    private Rectangle[] robot;
    private Rectangle[] cards;
    private int roboid;
    public Map map;
    public boolean drawnTable = false;
    public int witchRobot = 0;
    public ArrayList<Player> listOfPLayers;
    public int moveonce;



    public MapGUI(Map map, ArrayList<Player> listOfPLayers) {
        this.map = map;
        this.listOfPLayers = listOfPLayers;
    }

    public void updateMap(Map map) {
        this.map = map;
    }

    @Override
    public void create() {

        //TODO make manu screen appear first
        //((MenuBar)Gdx.app.getApplicationListener()).setScreen(new MenuBar());


        //Testing with other picture
        robotImage = getImage("r.png");
        voidImage = getImage("v.png");
        yellowConveyor_beltImage1 = getImage("y1.png");
        yellowConveyor_beltImage2 = getImage("y2.png");
        yellowConveyor_beltImage3 = getImage("y3.png");
        yellowConveyor_beltImage4 = getImage("y4.png");
        blueConveyor_beltImage1 = getImage("b1.png");
        blueConveyor_beltImage2 = getImage("b2.png");
        blueConveyor_beltImage3 = getImage("b3.png");
        blueConveyor_beltImage4 = getImage("b4.png");
        wrenchImage = getImage("s.png");
        wrench_hammer = getImage("s_h.png");
        rotatin_plateImage = getImage("g.png");
        flagImage = getImage("f.png");
        nothingImage = getImage("n.png");
        cardTester = getImage("card_Tester.png");

        laserImage1 = getImage("l1.png");
        laserImage2 = getImage("l2.png");
        laserImage3 = getImage("l3.png");
        laserImage4 = getImage("l4.png");
        laserImage5 = getImage("l5.png");
        laserImage6 = getImage("l6.png");

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 768, 1024);

        batch = new SpriteBatch();


        // create a list of Rectangles to logically represent the objects
        tile = new Rectangle[16][16];
        robot = new Rectangle[10];
        cards = new Rectangle[9];

        for (int i = 0; i < 10; i++) {
            robot[i] = new Rectangle();
            robot[i].x = -1;
            robot[i].y = -1;
            robot[i].height = 64;
            robot[i].width = 64;
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) { //swithced from tile[i][j] to tile[j][i]
                tile[j][i] = new Rectangle();
                tile[j][i].x = i * 64;
                tile[j][i].y = (15 - j) * 64;
                tile[j][i].width = 64;
                tile[j][i].height = 64;
            }
        }
/*
        for (int i = 0; i < 9; i++) {
            cards[i] = new Rectangle();
            cards[i].x = 32 + i * 64;
            cards[i].y = 700;
            cards[i].height = 96;
            cards[i].width = 64;
        }
*/
        moveonce = 0;


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
            batch.draw(robotImage, listOfPLayers.get(i).getRobot().getY() * 64, 960 - listOfPLayers.get(i).getRobot().getX() * 64);
            //batch.draw(robotImage, listOfPLayers.get(i).getRobot().getX() * 64, listOfPLayers.get(i).getRobot().getY() * 64);
        }
        //
        batch.end();
/*
        for (int i = 0; i < 9; i++) {
            batch.draw(cardTester, cards[i].x, cards[i].y);
        }*/



        //stuff for moving
        witchRobot++;
        if (witchRobot > 2) {
            witchRobot = 0;
        }

        if (moveonce < 64*5){
            robot[1].y += 1;
        }

        moveonce++;
    }

    public void drawTable() {


        drawnTable = true;
        roboid = 0;

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                IBoardObject object = map.getBoardObject(new Position(i, j));

                if (object instanceof Void) {
                    batch.draw(voidImage, tile[i][j].x, tile[i][j].y);
                } else if (object instanceof Laser) {
                    Laser l = (Laser) map.getBoardObject(new Position(i, j));
                    if (l.pictureDir == 1)
                        batch.draw(laserImage1, tile[i][j].x, tile[i][j].y);
                    else if (l.pictureDir == 2)
                        batch.draw(laserImage2, tile[i][j].x, tile[i][j].y);
                    else if (l.pictureDir == 3)
                        batch.draw(laserImage3, tile[i][j].x, tile[i][j].y);
                    else if (l.pictureDir == 4)
                        batch.draw(laserImage4, tile[i][j].x, tile[i][j].y);
                    else if (l.pictureDir == 5)
                        batch.draw(laserImage5, tile[i][j].x, tile[i][j].y);
                    else
                        batch.draw(laserImage6, tile[i][j].x, tile[i][j].y);
                } else if (object instanceof Conveyor_belt) {
                    Conveyor_belt c = (Conveyor_belt) map.getBoardObject(new Position(i, j));
                    if (c.isBlueBelt) {
                        if (c.pictureDir == 1)
                            batch.draw(blueConveyor_beltImage1, tile[i][j].x, tile[i][j].y);
                        else if (c.pictureDir == 2)
                            batch.draw(blueConveyor_beltImage2, tile[i][j].x, tile[i][j].y);
                        else if (c.pictureDir == 3)
                            batch.draw(blueConveyor_beltImage3, tile[i][j].x, tile[i][j].y);
                        else
                            batch.draw(blueConveyor_beltImage4, tile[i][j].x, tile[i][j].y);
                    } else {
                        if (c.pictureDir == 4)
                            batch.draw(yellowConveyor_beltImage4, tile[i][j].x, tile[i][j].y);
                        else if (c.pictureDir == 2)
                            batch.draw(yellowConveyor_beltImage2, tile[i][j].x, tile[i][j].y);
                        else if (c.pictureDir == 1)
                            batch.draw(yellowConveyor_beltImage1, tile[i][j].x, tile[i][j].y);
                        else
                            batch.draw(yellowConveyor_beltImage3, tile[i][j].x, tile[i][j].y);
                    }
                } else if (object instanceof Wrench) {
                    batch.draw(wrenchImage, tile[i][j].x, tile[i][j].y);
                } else if (object instanceof Wrench_hammer) {
                    batch.draw(wrench_hammer, tile[i][j].x, tile[i][j].y);
                } else if (object instanceof Flag) {
                    batch.draw(flagImage, tile[i][j].x, tile[i][j].y);
                } else if (object instanceof Rotating_belt) {
                    batch.draw(rotatin_plateImage, tile[i][j].x, tile[i][j].y);
                } else if (object instanceof Robot) {
                    //batch.draw(nothingImage, tile[i][k].x, tile[i][k].y);
                    batch.draw(nothingImage, tile[i][j].x, tile[i][j].y);
                    roboid++;
                } else {
                    batch.draw(nothingImage, tile[i][j].x, tile[i][j].y);
                }
            }
        }
    }

    @Override
    public void dispose() {
        // dispose of all
        robotImage.dispose();
        voidImage.dispose();
        nothingImage.dispose();
        rotatin_plateImage.dispose();
        wrench_hammer.dispose();
        wrenchImage.dispose();
        flagImage.dispose();
        batch.dispose();

        yellowConveyor_beltImage1.dispose();
        yellowConveyor_beltImage2.dispose();
        yellowConveyor_beltImage3.dispose();
        yellowConveyor_beltImage4.dispose();

        blueConveyor_beltImage1.dispose();
        blueConveyor_beltImage2.dispose();
        blueConveyor_beltImage3.dispose();
        blueConveyor_beltImage4.dispose();


        laserImage1.dispose();
        laserImage2.dispose();
        laserImage3.dispose();
        laserImage4.dispose();
        laserImage5.dispose();
        laserImage6.dispose();
    }
}