package screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import com.my.game.WBGame;

public class SplashScreen extends AbstractScreen{
    private Texture splashImg;

    public SplashScreen(final WBGame game) {
        super(game);
        init();

        Timer.schedule(new Task() {
            @Override
            public void run() {
                game.setScreen(new GameplayScreen(game));
            }
        }, 2);
    }

    private void init() {
        // TODO implement better assets loading when game grows
        splashImg = new Texture("mount1.jpg");

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        spriteBatch.begin();
        spriteBatch.draw(splashImg, 0, 0,WBGame.WIDTH, WBGame.HEIGHT);
        spriteBatch.end();
    }
}
