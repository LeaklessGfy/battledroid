package fr.slick.ui;

import fr.battledroid.core.Direction;
import fr.battledroid.core.Settings;
import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.adaptee.SpriteFactory;
import fr.battledroid.core.artifact.ArtifactFactory;
import fr.battledroid.core.engine.Engine;
import fr.battledroid.core.engine.EngineFactory;
import fr.battledroid.core.engine.ViewContext;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.map.MapFactory;
import fr.battledroid.core.map.tile.math.IsometricDaniloff;
import fr.battledroid.core.player.*;
import fr.slick.adapter.CanvasAdapter;
import fr.slick.adapter.ColorAdapter;
import fr.slick.adapter.SlickSpriteFactory;
import fr.slick.bridge.AssetFacade;
import fr.slick.bridge.SysObserver;
import org.newdawn.slick.*;

import java.util.Objects;

public final class GameMain extends BasicGame {
    private final SpriteFactory spriteFactory;
    private final AssetFactory assetFactory;
    private ViewContext context;
    private CanvasAdapter adapter;

    private GameMain(SpriteFactory spriteFactory, AssetFactory assetFactory) {
        super("Battledroid");
        this.spriteFactory = Objects.requireNonNull(spriteFactory);
        this.assetFactory = Objects.requireNonNull(assetFactory);
    }

    public static GameMain create() {
        SpriteFactory spriteFactory = new SlickSpriteFactory(4);
        AssetFactory assetFactory = new AssetFactory(spriteFactory);

        return new GameMain(spriteFactory, assetFactory);
    }

    public static void main(String[] arguments) {
        try {
            Settings settings = new Settings.Builder()
                    .setTileWidth(56)
                    .setTileHeight(74)
                    .setTileAlphaWidth(0)
                    .setTileAlphaHeight(0)
                    .setMapSize(4)
                    .setScreenWidth(1008)
                    .setScreenHeight(740)
                    .build();

            AppGameContainer app = new AppGameContainer(GameMain.create());
            app.setDisplayMode(settings.screenWidth, settings.screenHeight, false);
            app.setShowFPS(false); // true for display the numbers of FPS
            app.setVSync(true); // false for disable the FPS synchronize
            app.start();
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        AssetFacade.initAsset(assetFactory);
        Map map = MapFactory.createRandom(assetFactory, new IsometricDaniloff());

        Player player = PlayerFactory.createDroid(assetFactory);
        Player monster = PlayerFactory.createMonster(assetFactory);

        player.attach(new SysObserver());
        monster.attach(new SysObserver());

        ArtifactFactory artifactFactory = ArtifactFactory.create(assetFactory);

        Engine engine = EngineFactory.create(map, new ColorAdapter(Color.black));
        engine.addPlayer(player);
        engine.addPlayer(monster);
        //engine.generateArtifact(artifactFactory);

        context = new ViewContext(engine, player);
        adapter = new CanvasAdapter(container.getWidth(), container.getHeight());
        context.center();

        container.getInput().enableKeyRepeat();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        context.tick();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        context.draw(adapter.wrap(g));
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_Q:
                context.move(Direction.LEFT);
                break;
            case Input.KEY_D:
                context.move(Direction.RIGHT);
                break;
            case Input.KEY_Z:
                context.move(Direction.UP);
                break;
            case Input.KEY_S:
                context.move(Direction.DOWN);
                break;
            case Input.KEY_LEFT:
                context.shoot(Direction.LEFT);
                break;
            case Input.KEY_RIGHT:
                context.shoot(Direction.RIGHT);
                break;
            case Input.KEY_UP:
                context.shoot(Direction.UP);
                break;
            case Input.KEY_DOWN:
                context.shoot(Direction.DOWN);
                break;
        }
    }
}