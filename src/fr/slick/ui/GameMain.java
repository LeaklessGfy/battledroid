package fr.slick.ui;

import fr.battledroid.core.Settings;
import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.adaptee.SpriteFactory;
import fr.battledroid.core.adapter.DefaultAssetFactory;
import fr.battledroid.core.engine.Engine;
import fr.battledroid.core.engine.EngineFactory;
import fr.battledroid.core.engine.ViewContext;
import fr.battledroid.core.map.Biome;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.map.MapFactory;
import fr.battledroid.core.player.Droid;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.player.PlayerFactory;
import fr.slick.adapter.CanvasAdapter;
import fr.slick.adapter.ColorAdapter;
import fr.slick.adapter.SlickSpriteFactory;
import org.newdawn.slick.*;

import java.nio.file.Paths;
import java.util.Objects;

public class GameMain extends BasicGame {
    private final AssetFactory factory;
    private ViewContext context;
    private CanvasAdapter adapter;

    private GameMain(AssetFactory factory) {
        super("Battledroid");
        this.factory = Objects.requireNonNull(factory);
    }

    public static GameMain create() {
        SpriteFactory spriteFactory = new SlickSpriteFactory();
        AssetFactory assetFactory = DefaultAssetFactory.create(spriteFactory);

        return new GameMain(assetFactory);
    }

    public static void main(String[] arguments) {
        try {
            Settings settings = new Settings.Builder()
                    .setScreenWidth(640)
                    .setScreenHeight(480)
                    .build();

            AppGameContainer app = new AppGameContainer(GameMain.create());
            app.setDisplayMode(settings.screenWidth, settings.screenHeight, false);
            app.setShowFPS(false); // true for display the numbers of FPS
            app.setVSync(true); // false for disable the FPS synchronize
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        factory.registerBiome(Biome.SNOW, Paths.get("resources/tiles/snow.png"));
        factory.registerBiome(Biome.GRASS, Paths.get("resources/tiles/grass.png"));
        factory.registerBiome(Biome.DARK_GRASS, Paths.get("resources/tiles/dark_grass.png"));
        factory.registerBiome(Biome.ROCK, Paths.get("resources/tiles/rock.png"));
        factory.registerBiome(Biome.LIGHT_ROCK, Paths.get("resources/tiles/light_rock.png"));
        factory.registerBiome(Biome.SAND, Paths.get("resources/tiles/sand.png"));
        factory.registerPlayer(Droid.class, Paths.get("resources/players/droid.png"));

        Map map = MapFactory.createRandom(factory);
        Player player = PlayerFactory.createDroid(factory);

        Engine engine = EngineFactory.create(map, new ColorAdapter(Color.black));
        engine.addPlayer(player);

        context = new ViewContext(engine, player);
        adapter = new CanvasAdapter(container.getWidth(), container.getHeight());
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        context.tick();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        context.draw(adapter.wrap(g));
    }
}