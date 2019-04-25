package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;


//implementing screen gives us important functions for making the menu-bar and gives us overrides we might need
// TODO! Is the whole menubar class redundant?
public class MenuBar implements Screen{

    private static final int BackgroundX = 640;
    private static final int BackgroundY = 940;

    private static final int PLAY_BUTTON_HEIGHT = 250;
    private static final int PLAY_BUTTON_WEIGHT = 200;

    private static final int EXIT_BUTTON_HEIGHT = 250;
    private static final int EXIT_BUTTON_WEIGHT = 200;

    //used later
    public static final int EXIT_BUTTON_Y = 100;
    public static final int PLAY_BUTTON_Y = 100;


    //needs a refrence to game

    private Stage stage;

    private SpriteBatch batch;

    //Defining the camera used in our game
    private OrthographicCamera camera;

    Texture backGround;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtoninActive;



    public MenuBar(){
        //this.RoboRally = RoboRally;

           camera = new OrthographicCamera();
           camera.setToOrtho(false, 640, 940);


        batch = new SpriteBatch();

            backGround = new Texture(Gdx.files.internal("starsBackground.jpg"));

            playButtonActive = new Texture(Gdx.files.internal("playbutton"));

            playButtonInactive = new Texture(Gdx.files.internal("playbutton"));

            // playButtonInactive = new Texture("playButton.png");

            exitButtonActive = new Texture(Gdx.files.internal("exitButton.png"));

            //exitButtoninActive = new Texture("exitButton.png");







    }

    @Override
    public void show() {

        //Set what filles the screen

    }

    @Override
    public void render(float delta) {
        //Screen must be ready/clear in order for next set of images to be drawn
        Gdx.gl.glClearColor(1,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        //draws background
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backGround, BackgroundX, BackgroundY); //find out how to use (backGround, 0,0, Game.WIDTH. Game.HEIGHT)

        //draws button for when they are being touched and not

        if(Gdx.input.getX()< 100){
            batch.draw(playButtonActive, PLAY_BUTTON_WEIGHT, PLAY_BUTTON_HEIGHT );
        } else{
            batch.draw(playButtonInactive, PLAY_BUTTON_WEIGHT, PLAY_BUTTON_HEIGHT );
        }
        if(Gdx.input.getX()< 100){
            batch.draw(playButtonActive, PLAY_BUTTON_WEIGHT, PLAY_BUTTON_HEIGHT );
        } else{
            batch.draw(exitButtonActive, EXIT_BUTTON_WEIGHT, EXIT_BUTTON_HEIGHT);
        }



        //Tell our stage to draw itself

        batch.end();


    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);

    }

    @Override
    public void dispose() {
        backGround.dispose();
        batch.dispose();


    }
}






