import java.util.ArrayList;
import java.util.List;

/**
 * Holds information for scores
 * @param <E>   String
 */
public class Scores<E> {
    private List<E> values = new ArrayList<>();

    /**
     * Creates a new score object
     * @param name          Name of the player
     * @param score         The players score
     * @param difficulty    The difficulty of the board
     */
    public Scores(E name, E score, E difficulty) {
        values.add(name);
        values.add(score);
        values.add(difficulty);
    }

    public List<E> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return ("\n" + values.get(0) + "\n" + values.get(1) + "\n" + values.get(2) + "\n");
    }
}