package fr.slick;

import fr.slick.scene.Menu;
import fr.slick.scene.Play;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public final class App extends StateBasedGame {
    private App(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new Menu());
        addState(Play.create());
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new App("Battledroid"));
        app.setDisplayMode(1008, 800, false);
        app.setShowFPS(false); // true for display the numbers of FPS
        app.setVSync(true); // false for disable the FPS synchronize
        app.start();
    }
}
