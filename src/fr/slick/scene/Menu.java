package fr.slick.scene;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public final class Menu extends BasicGameState {
    private Rectangle btnPlay;
    private StateBasedGame state;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer c, StateBasedGame s) throws SlickException {
        int xM = c.getWidth() / 2;
        int yM = c.getHeight() / 2;

        btnPlay = new Rectangle(xM - 50, yM - 15, 100, 30);
        state = s;
    }

    @Override
    public void render(GameContainer c, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.drawString("Battledroid", btnPlay.getX(), 100);

        g.setColor(Color.lightGray);
        g.fill(btnPlay);

        g.setColor(Color.black);
        g.drawString("Play",  btnPlay.getCenterX() - 15,  btnPlay.getCenterY() - 7);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (btnPlay.contains(x, y)) {
            state.enterState(1);
        }
    }
}
