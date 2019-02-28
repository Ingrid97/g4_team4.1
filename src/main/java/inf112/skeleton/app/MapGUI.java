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

import static inf112.skeleton.app.Game.playGame;

public class MapGUI extends ApplicationAdapter {

    private Texture robotImage, voidImage, yellowConveyor_beltImage, blueConveyor_beltImage, laserImage, wrenchImage, wrench_hammer, rotatin_plateImage, flagImage, nothingImage;

    private Sound dropSound;
    private Music rainMusic;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Rectangle[][] tile;
    private Rectangle robot;

    public static Map map;

    @Override
    public void create() {


        map = playGame();

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

        //Testinf with other picture
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

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 640);
        batch = new SpriteBatch();


        // create a list of Rectangles to logically represent the objects
        tile = new Rectangle[10][10];

        //make the moving robot
        robot = new Rectangle();
        robot.x = 0;
        robot.y = 0;
        robot.height = 64;
        robot.width = 64;


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j ++) {
                tile[i][j] = new Rectangle();
                tile[i][j].x = i*64;
                tile[i][j].y = j*64;
                tile[i][j].width = 64;
                tile[i][j].height = 64;
            }
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

        //TODO: make switch
        for (int i = 0,x = 0; i < 10; i++, x++) {
            for (int j = 0, y = 9; j < 10; j++, y--) {
                if (map.getBoardObject(y, x) instanceof Robot){
                    batch.draw(robotImage, tile[i][j].x, tile[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Void){
                    batch.draw(voidImage, tile[i][j].x, tile[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Laser){
                    batch.draw(laserImage, tile[i][j].x, tile[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Conveyor_belt){
                    Conveyor_belt c = (Conveyor_belt) map.getBoardObject(y, x);
                    if (c.isBlueBelt)
                        batch.draw(blueConveyor_beltImage, tile[i][j].x, tile[i][j].y);
                    else
                        batch.draw(yellowConveyor_beltImage, tile[i][j].x, tile[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Wrench){
                    batch.draw(wrenchImage, tile[i][j].x, tile[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Wrench_hammer){
                    batch.draw(wrench_hammer, tile[i][j].x, tile[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Flag){
                    batch.draw(flagImage, tile[i][j].x, tile[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Rotating_belt){
                    batch.draw(rotatin_plateImage, tile[i][j].x, tile[i][j].y);
                } else {
                    batch.draw(nothingImage, tile[i][j].x, tile[i][j].y);
                }
            }
        }

        batch.draw(robotImage, robot.x, robot.y);

        batch.end();


        //stuff for moving
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) robot.y += 64;
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) robot.y -= 64;
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) robot.x -= 64;
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) robot.x += 64;


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
