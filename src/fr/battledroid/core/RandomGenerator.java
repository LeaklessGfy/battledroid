package fr.battledroid.core;

import java.util.Random;

public final class RandomGenerator {
    private final static Random rand = new Random();

    public static int randomInt(int min, int max) {
        Utils.requireMinMax(min, 0, max);
        Utils.requireMin(max, min);

        return rand.nextInt(max + 1 - min) + min;
    }

    public static boolean randomBoolean() {
        return rand.nextBoolean();
    }
}
