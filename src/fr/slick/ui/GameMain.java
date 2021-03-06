package fr.slick.ui;

import fr.battledroid.core.Direction;
import fr.battledroid.core.Settings;
import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.adaptee.SpriteFactory;
import fr.battledroid.core.artifact.ArtifactFactory;
import fr.battledroid.core.artifact.BombMalus;
import fr.battledroid.core.artifact.HealthBonus;
import fr.battledroid.core.artifact.SpeedBonus;
import fr.battledroid.core.engine.Engine;
import fr.battledroid.core.engine.EngineFactory;
import fr.battledroid.core.engine.ViewContext;
import fr.battledroid.core.map.Biome;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.map.MapFactory;
import fr.battledroid.core.particle.Laser;
import fr.battledroid.core.player.*;
import fr.battledroid.core.player.item.Item;
import fr.slick.adapter.CanvasAdapter;
import fr.slick.adapter.ColorAdapter;
import fr.slick.adapter.SlickSpriteFactory;
import org.newdawn.slick.*;

import java.nio.file.Paths;
import java.util.Objects;

public final class GameMain extends BasicGame {
    private final AssetFactory factory;
    private ViewContext context;
    private CanvasAdapter adapter;

    private GameMain(AssetFactory factory) {
        super("Battledroid");
        this.factory = Objects.requireNonNull(factory);
    }

    public static GameMain create() {
        SpriteFactory spriteFactory = new SlickSpriteFactory();
        AssetFactory assetFactory = new AssetFactory(spriteFactory);

        return new GameMain(assetFactory);
    }

    public static void main(String[] arguments) {
        try {
            Settings settings = new Settings.Builder()
                    .setScreenWidth(1000)
                    .setScreenHeight(700)
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
        factory.registerBiome(Biome.SNOW, Paths.get("resources/tiles/snow.png"));
        factory.registerBiome(Biome.GRASS, Paths.get("resources/tiles/grass.png"));
        factory.registerBiome(Biome.DARK_GRASS, Paths.get("resources/tiles/dark_grass.png"));
        factory.registerBiome(Biome.ROCK, Paths.get("resources/tiles/rock.png"));
        factory.registerBiome(Biome.LIGHT_ROCK, Paths.get("resources/tiles/light_rock.png"));
        factory.registerBiome(Biome.SAND, Paths.get("resources/tiles/sand.png"));

        factory.registerObstacle(Biome.SNOW, Paths.get("resources/overlays/snow_mountain.png"));
        factory.registerObstacle(Biome.GRASS, Paths.get("resources/overlays/building.png"));
        factory.registerObstacle(Biome.GRASS, Paths.get("resources/overlays/airport.png"));
        factory.registerObstacle(Biome.GRASS, Paths.get("resources/overlays/tower.png"));
        factory.registerObstacle(Biome.DARK_GRASS, Paths.get("resources/overlays/satellite.png"));
        factory.registerObstacle(Biome.ROCK, Paths.get("resources/overlays/rock_mountain.png"));
        factory.registerObstacle(Biome.ROCK, Paths.get("resources/overlays/rock.png"));
        factory.registerObstacle(Biome.LIGHT_ROCK, Paths.get("resources/overlays/pyramid.png"));
        factory.registerObstacle(Biome.SAND, Paths.get("resources/overlays/sand_mountain.png"));

        factory.registerPlayer(Droid.class, Paths.get("resources/players/droid.png"));
        factory.registerPlayer(Monster.class, Paths.get("resources/players/monster.png"));

        factory.registerArtifact(BombMalus.class, Paths.get("resources/artifacts/bomb_malus.png"));
        factory.registerArtifact(SpeedBonus.class, Paths.get("resources/artifacts/speed_bonus.png"));
        factory.registerArtifact(HealthBonus.class, Paths.get("resources/artifacts/health_bonus.png"));

        factory.registerParticle(Laser.class, Paths.get("resources/particles/laser_down_eye.png"));

        Map map = MapFactory.createRandom(factory);
        Player player = PlayerFactory.createDroid(factory);
        player.attach(new PlayerObserver() {
            @Override
            public void updateHealth(double health) {
                System.out.println("New health " + health);
            }

            @Override
            public void updateDefense(int defense) {
            }

            @Override
            public void updateSpeed(int speed) {

            }

            @Override
            public void updateItem(Item item, boolean isNewItem) {

            }
        });
        Player monster = PlayerFactory.createMonster(factory);

        ArtifactFactory artifactFactory = ArtifactFactory.create(factory);

        Engine engine = EngineFactory.create(map, new ColorAdapter(Color.black));
        engine.addPlayer(player);
        engine.addPlayer(monster);
        engine.generateArtifact(artifactFactory);

        context = new ViewContext(engine, player);
        adapter = new CanvasAdapter(container.getWidth(), container.getHeight());

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