package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static inf112.skeleton.app.Game.playGame;

public class MapGUI extends ApplicationAdapter {

    private Texture robotImage, voidImage, yellowImage, blueImage, laserImage, screwImage, hammer_ScrewImage, gearImmage, flagImage, nothingImage;

    private Sound dropSound;
    private Music rainMusic;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Rectangle[][] bucket;

    private Map map;

    @Override
    public void create() {


        map = playGame();

        // load the images for the droplet and the bucket, 64x64 pixels each
        robotImage = new Texture(Gdx.files.internal("wall_e.png"));
        voidImage = new Texture(Gdx.files.internal("void.png"));
        yellowImage = new Texture(Gdx.files.internal("Yello_Arrow.png"));
        blueImage = new Texture(Gdx.files.internal("Blue_Arrow.png"));
        laserImage = new Texture(Gdx.files.internal("Laser.png"));
        screwImage = new Texture(Gdx.files.internal("screw.png"));
        hammer_ScrewImage = new Texture(Gdx.files.internal("hammer_screw.png"));
        gearImmage = new Texture(Gdx.files.internal("gear.png"));
        flagImage = new Texture(Gdx.files.internal("Flag.png"));
        nothingImage = new Texture(Gdx.files.internal("Nothing.png"));

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 640);
        batch = new SpriteBatch();


        // create a list of Rectangles to logically represent the objects
        bucket = new Rectangle[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j ++) {
                bucket[i][j] = new Rectangle();
                bucket[i][j].x = i*64;
                bucket[i][j].y = j*64;
                bucket[i][j].width = 64;
                bucket[i][j].height = 64;
            }
        }
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
                    batch.draw(robotImage, bucket[i][j].x, bucket[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Void){
                    batch.draw(voidImage, bucket[i][j].x, bucket[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Laser){
                    batch.draw(laserImage, bucket[i][j].x, bucket[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Blue_Bond){
                    batch.draw(blueImage, bucket[i][j].x, bucket[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Yellow_bond){
                    batch.draw(yellowImage, bucket[i][j].x, bucket[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Screwdriver){
                    batch.draw(screwImage, bucket[i][j].x, bucket[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Hammer_Screwdriver){
                    batch.draw(hammer_ScrewImage, bucket[i][j].x, bucket[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Flag){
                    batch.draw(flagImage, bucket[i][j].x, bucket[i][j].y);
                } else if (map.getBoardObject(y, x) instanceof Rotating_Plate){
                    batch.draw(gearImmage, bucket[i][j].x, bucket[i][j].y);
                } else {
                    batch.draw(nothingImage, bucket[i][j].x, bucket[i][j].y);
                }
            }
        }

        batch.end();


        //stuff for mooving
        //if(Gdx.input.isKeyJustPressed(Keys.UP)) bucket.y += 64;
        //if(Gdx.input.isKeyJustPressed(Keys.DOWN)) bucket.y -= 64;
        //if(Gdx.input.isKeyJustPressed(Keys.LEFT)) bucket.x -= 64;
        //if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) bucket.x += 64;

    }

    @Override
    public void dispose() {
        // dispose of all the native resources
        robotImage.dispose();
        voidImage.dispose();
        blueImage.dispose();
        yellowImage.dispose();
        laserImage.dispose();
        nothingImage.dispose();
        gearImmage.dispose();
        hammer_ScrewImage.dispose();
        screwImage.dispose();
        flagImage.dispose();
        batch.dispose();
    }
}
