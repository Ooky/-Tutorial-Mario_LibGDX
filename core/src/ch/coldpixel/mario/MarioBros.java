package ch.coldpixel.mario;

import ch.coldpixel.mario.Screens.PlayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MarioBros extends Game {

//==============================================================================
//Initialization
//============================================================================== 
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100; //Pixels per meter

    public SpriteBatch batch; //All of our different screen can take this Spritebatch
    Texture img;

//==============================================================================
//Methods
//==============================================================================
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render(); //Delegate the render method to the Play
    }
}//public class MarioBros extends Game {
