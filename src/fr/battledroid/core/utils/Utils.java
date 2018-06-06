package fr.battledroid.core.utils;

public final class Utils {
    /**
     * Check if parameter t is null. Throw IllegalArgumentException if it's the case
     * @param t param to check
     * @param <T> every object
     * @return t parameter
     */
    public static <T> T requireNonNull(T t) {
        if (t == null) {
            throw new IllegalArgumentException("Non null argument require");
        }
        return t;
    }

    /**
     * Check if value is superior to min
     * @param value value to test
     * @param min minimum value should be
     * @return value if correct
     */
    public static int requireMin(int value, int min) {
        if (value < min) {
            throw new IllegalArgumentException("Bad value, should be > " + min);
        }
        return value;
    }

    /**
     * Check if value is superior to min
     * @param value value to test
     * @param min minimum value should be
     * @return value if correct
     */
    public static double requireMin(double value, double min) {
        if (value < min) {
            throw new IllegalArgumentException("Bad value, should be > " + min);
        }
        return value;
    }

    /**
     * Check if value is between min and max
     * @param value value to test
     * @param min minimum value should be
     * @param max maximum value should be
     * @return value if correct
     */
    public static int requireMinMax(int value, int min, int max) {
        if (value < min || value > max) {
            throw new IllegalArgumentException("Bad value, should be > " + min + " and < " + max);
        }
        return value;
    }
}
