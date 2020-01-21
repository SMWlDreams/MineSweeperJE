package game.randomizers;

import java.util.Random;

public class DoubleSeededRandomizer extends Randomizer {
    private Random seed1;
    private Random seed2;
    private long secondSeed;

    public DoubleSeededRandomizer() {
        super();
        secondSeed = seed % Integer.MAX_VALUE;
        if (secondSeed == 0) {
            secondSeed = Integer.MAX_VALUE;
        }
        while (seed == secondSeed) {
            secondSeed %= (secondSeed/2);
        }
    }

    public DoubleSeededRandomizer(long seed) {
        this.seed = seed;
        secondSeed = seed % Integer.MAX_VALUE;
        if (secondSeed == 0) {
            secondSeed = Integer.MAX_VALUE;
        }
    }

    /**
     * Generates a board from the given arguments
     * @param args 4 arguments as double: Number of columns, number of rows, tile dimension scale,
     *             number of mines
     */
    @Override
    public void generateBoard(double[] args) {
        seed1 = new Random(seed);
        seed2 = new Random(secondSeed);
        super.generateBoard(args);
    }

    @Override
    public void placeMines(double[] args) {
        placeMines(args, this);
    }

    @Override
    protected int[] getCoordinatePair(int x, int y) {
        return new int[]{seed1.nextInt(x), seed2.nextInt(y)};
    }
}
