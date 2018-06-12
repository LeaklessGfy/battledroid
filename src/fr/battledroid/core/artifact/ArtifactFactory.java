package fr.battledroid.core.artifact;

import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.function.Supplier;

import java.util.ArrayList;
import java.util.Random;

public final class ArtifactFactory {
    private final Random rand = new Random();
    private final ArrayList<Supplier<Artifact>> suppliers = new ArrayList<>();

    private ArtifactFactory() {}

    public static ArtifactFactory create(AssetFactory assetFactory) {
        ArtifactFactory factory = new ArtifactFactory();
        factory.suppliers.add(new Supplier<Artifact>() {
            @Override
            public Artifact supply() {
                return new BombMalus(assetFactory.getArtifact(BombMalus.class));
            }
        });
        /*
        factory.suppliers.add(new Supplier<Artifact>() {
            @Override
            public Artifact supply() {
                return new HealthBonus(assetFactory.getArtifact(HealthBonus.class));
            }
        });
        factory.suppliers.add(new Supplier<Artifact>() {
            @Override
            public Artifact supply() {
                return new SpeedBonus(assetFactory.getArtifact(SpeedBonus.class));
            }
        });
        */

        return factory;
    }

    public Artifact createRandom() {
        int index = rand.nextInt(suppliers.size()) % suppliers.size();
        return suppliers.get(index).supply();
    }
}
