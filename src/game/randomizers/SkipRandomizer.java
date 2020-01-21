package game.randomizers;

import java.util.Random;

public class SkipRandomizer extends Randomizer {
    private Random random;

    public SkipRandomizer() {
        super();
    }

    public SkipRandomizer(long seed) {
        this.seed = seed;
    }

    @Override
    public void generateBoard(double[] args) {
        random = new Random(seed);
        super.generateBoard(args);
    }

    @Override
    public void placeMines(double[] args) {
        placeMines(args, this);
    }

    @Override
    protected int[] getCoordinatePair(int x, int y) {
        int[] i = new int[2];
        i[0] = random.nextInt(x);
        random.nextInt(x);
        i[1] = random.nextInt(y);
        random.nextInt(y);
        return i;
    }
}
