package ch.coldpixel.mario.Screens;

import ch.coldpixel.mario.MarioBros;
import ch.coldpixel.mario.Scenes.Hud;
import ch.coldpixel.mario.Sprites.Mario;
import ch.coldpixel.mario.Tools.B2WorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {

//==============================================================================
//Initialization
//============================================================================== 
    //Game
    private MarioBros game;
    private TextureAtlas atlas;

    //Cam
    private OrthographicCamera gameCam;
    //Viewport
    private Viewport gamePort;
    //HUD
    private Hud hud;
    //Sprites
    private Mario player;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr; //Graphical represatition of our bodies

//==============================================================================
//Methods
//==============================================================================
    public PlayScreen(MarioBros game) {
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;
        gameCam = new OrthographicCamera();

        //All different ViewPorts
//        gamePort = new StretchViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, gameCam);//Stretches to the whole screen
        gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gameCam); //Maintains aspect Ratio, sets border if it cannot use all the screen
//        gamePort = new ScreenViewport(gameCam); //Larger Screen = More World, 
        //1 World unit == 1 screen Pixel
        //Could give an unfair advantage to players with huge Screens

        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);
        //set gameCam to be centered correctly
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);//Vector = gravity, true= sleep objects
        b2dr = new Box2DDebugRenderer();

        //create mario in our game world
        world = new World(new Vector2(0, -10), true);
        player = new Mario(world, this);

        new B2WorldCreator(world, map);
    }

    @Override
    public void show() {
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void update(float dt) {
        //handle user input first
        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        player.update(dt);

        gameCam.position.x = player.b2body.getPosition().x;

        //update our gamecam with correct coordinates after changes
        gameCam.update();
        //tell our renderer to draw only what our camera can see in our game world
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //render our Box2DDebugLines
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
        hud.stage.draw();

        //Set our batch to now draw what the Hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

}//public class PlayScreen implements Screen {
