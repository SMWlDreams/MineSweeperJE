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
    public void generateBoard(double[] args) {
        random = new Random(seed);
        generateBoard(args, this);
    }

    @Override
    protected int[] getCoordinatePair(int x, int y) {
        return new int[]{random.nextInt(x), random.nextInt(y)};
    }
}
