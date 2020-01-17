package game.randomizers;

import java.util.Random;

public class DefaultRandomizer extends Randomizer {
    private Random random;

    public DefaultRandomizer() {
        super();
    }

    public DefaultRandomizer(long seed) {
        this.seed = seed;
    }

    @Override
    public void generateBoard(int[] args) {
        random = new Random(seed);
    }
}
