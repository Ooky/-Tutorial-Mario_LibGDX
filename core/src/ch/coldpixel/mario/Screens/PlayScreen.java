package ch.coldpixel.mario.Screens;

import ch.coldpixel.mario.MarioBros;
import ch.coldpixel.mario.Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {

//==============================================================================
//Initialization
//============================================================================== 
    //Game
    private MarioBros game;
    //Cam
    private OrthographicCamera gameCam;
    //Viewport
    private Viewport gamePort;
    //HUD
    private Hud hud;
//==============================================================================
//Methods
//==============================================================================

    public PlayScreen(MarioBros game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        
        //All different ViewPorts
//        gamePort = new StretchViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, gameCam);//Stretches to the whole screen
        gamePort = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, gameCam); //Maintains aspect Ratio, sets border if it cannot use all the screen
//        gamePort = new ScreenViewport(gameCam); //Larger Screen = More World, 
        //1 World unit == 1 screen Pixel
        //Could give an unfair advantage to players with huge Screens
        
        hud = new Hud(game.batch);
        
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}//public class PlayScreen implements Screen {
