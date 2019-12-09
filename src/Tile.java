import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * The tiles to make the board
 */
public class Tile extends Rectangle {
    /**
     * The default scale for each of the image
     */
    public static final int SCALE = 20;

    /**
     * The main image for the mines to be used for icons in other menus
     */
    public static final Image MINE_IMAGE = new Image("mine.png");

    private int scaleMultiplier;
    private static final Image[] clickedImages = {new Image("zero.png"), new Image("one.png"),
            new Image(
            "two.png"), new Image("three.png"), new Image("four.png"), new Image("five.png"),
            new Image("six.png"), new Image("seven.png"), new Image("eight.png")};
    private static final Image beforeFlagged = new Image("default.png");
    private static final Image flag = new Image("flag.png");
    private Image afterClicked;
    private final int xcoord;
    private final int ycoord;
    private boolean mine = false;
    private int neighborMines = 0;
    private boolean flagged = false;
    private boolean selected;
    private Board board;
    private int moveSelected;

    /**
     * Creates a new tile with specified coordinates
     * @param x             X Coordinate of the tile
     * @param y             Y Coordinate of the tile
     * @param board         The board the tiles are placed in
     * @param multiplier    The multiplier for the scale based on board size
     */
    public Tile(int x, int y, Board board, int multiplier) {
        scaleMultiplier = multiplier;
        selected = false;
        this.board = board;
        xcoord = x;
        ycoord = y;
        this.setLayoutX(x * SCALE * scaleMultiplier);
        this.setLayoutY(y * SCALE * scaleMultiplier);
        this.setWidth(SCALE * scaleMultiplier);
        this.setHeight(SCALE * scaleMultiplier);
        this.setFill(new ImagePattern(beforeFlagged, 0, 0, SCALE * scaleMultiplier,
                SCALE * scaleMultiplier, false));
    }

    /**
     * Sets this tile to be a mine
     */
    public void setMine() {
        mine = true;
        afterClicked = MINE_IMAGE;
    }

    public boolean isMine() {
        return mine;
    }

    public int getXcoord() {
        return xcoord;
    }

    /**
     * Marks the tile as a flag if it is not already selected, un-flags it if it is already flagged
     */
    public void flag() {
        if (!selected) {
            if (flagged) {
                this.setFill(new ImagePattern(beforeFlagged, 0, 0, SCALE * scaleMultiplier,
                        SCALE * scaleMultiplier, false));
                flagged = false;
            } else {
                this.setFill(new ImagePattern(flag, 0, 0, SCALE * scaleMultiplier,
                        SCALE * scaleMultiplier, false));
                flagged = true;
            }
        }
    }

    /**
     * Selects the tile and updates the image for the tile
     * @return  -1 if it is a mine, otherwise the number of adjacent mines
     */
    public int onClick() {
        if (!selected) {
            updateImage();
            selected = true;
            if (mine) {
                return -1;
            } else {
                return neighborMines;
            }
        } else {
            return 1;
        }
    }

    public int getYcoord() {
        return ycoord;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setMoveSelected(int moveSelected) {
        this.moveSelected = moveSelected;
    }

    public int getMoveSelected() {
        return moveSelected;
    }

    /**
     * Determines how many mines are around this specific tile
     * @param tiles The list of tiles for this board
     */
    public void determineNeighbors(List<List<Tile>> tiles) {
        neighborMines = 0;
        determineNeighbors(tiles, xcoord, ycoord);
        switch (neighborMines) {
            case 0:
                afterClicked = clickedImages[0];
                break;
            case 1:
                afterClicked = clickedImages[1];
                break;
            case 2:
                afterClicked = clickedImages[2];
                break;
            case 3:
                afterClicked = clickedImages[3];
                break;
            case 4:
                afterClicked = clickedImages[4];
                break;
            case 5:
                afterClicked = clickedImages[5];
                break;
            case 6:
                afterClicked = clickedImages[6];
                break;
            case 7:
                afterClicked = clickedImages[7];
                break;
            case 8:
                afterClicked = clickedImages[8];
                break;
        }
    }

    public int getNeighborMines() {
        return neighborMines;
    }

    /**
     * Updates the image for this tile
     */
    public void updateImage() {
        this.setFill(new ImagePattern(afterClicked, 0, 0, SCALE * scaleMultiplier,
                SCALE * scaleMultiplier, false));
    }

    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the tile to its unselected state
     */
    public void reset() {
        this.setFill(new ImagePattern(beforeFlagged, 0, 0, SCALE * scaleMultiplier,
                SCALE * scaleMultiplier, false));
        selected = false;
        flagged = false;
    }

    private void determineNeighbors(List<List<Tile>> tiles, int xcoord, int ycoord) {
        if (ycoord + 1 < board.getColumns()) {
            if (tiles.get(xcoord).get(ycoord + 1).isMine()) {
                neighborMines++;
            }
        }
        if (ycoord + 1 < board.getColumns() && xcoord - 1 >= 0) {
            if (tiles.get(xcoord - 1).get(ycoord + 1).isMine()) {
                neighborMines++;
            }
        }
        if (ycoord + 1 < board.getColumns() && xcoord + 1 < board.getRows()) {
            if (tiles.get(xcoord + 1).get(ycoord + 1).isMine()) {
                neighborMines++;
            }
        }
        if (ycoord - 1 >= 0) {
            if (tiles.get(xcoord).get(ycoord - 1).isMine()) {
                neighborMines++;
            }
        }
        if (ycoord - 1 >= 0 && xcoord - 1 >= 0) {
            if (tiles.get(xcoord - 1).get(ycoord - 1).isMine()) {
                neighborMines++;
            }
        }
        if (ycoord - 1 >= 0 && xcoord + 1 < board.getRows()) {
            if (tiles.get(xcoord + 1).get(ycoord - 1).isMine()) {
                neighborMines++;
            }
        }
        if (xcoord - 1 >= 0) {
            if (tiles.get(xcoord - 1).get(ycoord).isMine()) {
                neighborMines++;
            }
        }
        if (xcoord + 1 < board.getRows()) {
            if (tiles.get(xcoord + 1).get(ycoord).isMine()) {
                neighborMines++;
            }
        }
    }
}