package factories;

import error.ErrorHandler;
import error.exceptions.InvalidSettingsException;
import game.randomizers.DefaultRandomizer;
import game.randomizers.DoubleSeededRandomizer;
import game.randomizers.Randomizer;
import game.randomizers.SkipRandomizer;

public class RandomizerFactory {
    public static Randomizer getRandomizer(String randomizer, long... seed) {
        long randSeed;
        if (seed.length == 0) {
            randSeed = System.nanoTime();
        } else {
            randSeed = seed[0];
        }
        if (randomizer.equalsIgnoreCase("default")) {
            return new DefaultRandomizer(randSeed);
        } else if (randomizer.equalsIgnoreCase("doubleseed")) {
            return new DoubleSeededRandomizer(randSeed);
        } else if (randomizer.equalsIgnoreCase("skip")) {
            return new SkipRandomizer(randSeed);
        } else {
            ErrorHandler.newExpectedExceptionAlert(new InvalidSettingsException("No valid " +
                    "ranomizer selected"), "Invalid Randomizer", true);
            return new DefaultRandomizer(randSeed);
        }
    }
}
