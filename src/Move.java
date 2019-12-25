/**
 * Represents a single move from a log file
 */
public class Move {
    private int x;
    private int y;
    private String property;

    public void setProperty(String property) {
        this.property = property;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the coordinate pair for this move
     * @return  String in the form of "x,y"
     */
    public String getMove() {
        return (x + "," + y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getProperty() {
        return property;
    }
}
