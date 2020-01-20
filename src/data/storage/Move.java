package data.storage;

public class Move {
    private int x;
    private int y;
    private String state;

    public Move() {}

    public Move(int x, int y, String state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getState() {
        return state;
    }

    public String toString() {
        return "<Move>\r\n<X>" + x + "</X>\r\n<Y>" + y + "</Y>\r\n<State>" + state + "</State>\r" +
                "\n</Move>\r\n";
    }
}
