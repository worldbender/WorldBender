package server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.*;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.graphics.GL20;
import static org.mockito.Mockito.mock;

public class ServerCreator {
    public static void main (String[] arg) {
        HeadlessNativesLoader.load();
        Gdx.graphics = new MockGraphics();
        Gdx.net = new HeadlessNet();
        Gdx.gl = mock(GL20.class);
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new Server(), config);
    }
}
