import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The board for this minesweeper game
 */
public class Board {
    private List<List<Tile>> tiles = new ArrayList<>();
    private PrintWriter writer;
    private int rows;
    private int columns;
    private int numClickedTiles;
    private int scaleMultiplier;
    private int numFlags;
    private long seed;
    private boolean log = false;
    private int logCount = 0;
    private boolean saveLogs = false;

    /**
     * Creates the board with a specified number of mines and a pane to draw mines to
     * @param row           The number of rows for the board
     * @param col           The number of columns for the board
     * @param numMines      The number of mines to place in the board
     * @param pane          The pane to draw the mines to
     * @param multiplier    The value to multiply the scale of the images by
     */
    public Board(int row, int col, int numMines, Pane pane, int multiplier) {
        this(row, col, numMines, pane, multiplier, "0");
    }

    /**
     * Creates the board with a specified number of mines and a pane to draw mines to
     * @param row           The number of rows for the board
     * @param col           The number of columns for the board
     * @param numMines      The number of mines to place in the board
     * @param pane          The pane to draw the mines to
     * @param multiplier    The value to multiply the scale of the images by
     * @param seed          The seed to populate RNG with
     */
    public Board(int row, int col, int numMines, Pane pane, int multiplier, String seed) {
        numClickedTiles = 0;
        scaleMultiplier = multiplier;
        numFlags = numMines;
        createBoard(col, row);
        rows = row;
        columns = col;
        initMines(numMines, seed);
        setNeighbors();
        for (List<Tile> l: tiles) {
            for (Tile t: l) {
                pane.getChildren().add(t);
            }
        }

    }

    /**
     * Updates the tile underneath the mouse
     * @param mouseEvent    The event sent by clicking the mouse
     * @return              True if the tile is not a mine, false if it is
     */
    public boolean onClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            int y = (int) mouseEvent.getX() / (Tile.SCALE * scaleMultiplier);
            int x = (int) mouseEvent.getY() / (Tile.SCALE * scaleMultiplier);
            if (!tiles.get(x).get(y).isSelected()) {
                if (log) {
                    writer.write("Primary click on tile: " + y + "," + x + "\r\n");
                }
            }
            return parseBoard(x, y);
        }
        return true;
    }

    /**
     * Flags the specified tile or un-flags it if already flagged
     * @param mouseEvent    The event sent by clicking the mouse
     * @return              The total number of flags currently placed
     */
    public int setFlagged(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX() / (Tile.SCALE * scaleMultiplier);
        int y = (int) mouseEvent.getY() / (Tile.SCALE * scaleMultiplier);
        Tile temp = tiles.get(y).get(x);
        if (!temp.isSelected()) {
            temp.flag();
            if (temp.isFlagged()) {
                if (log) {
                    writer.write("Flagged tile: " + y + "," + x + "\r\n");
                }
                numFlags--;
            } else {
                if (log) {
                    writer.write("Un-flagged tile: " + y + "," + x + "\r\n");
                }
                numFlags++;
            }
        }
        return numFlags;
    }

    public int getNumClickedTiles() {
        return numClickedTiles;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    /**
     * Clears the board
     * @param pane  The pane the mines are drawn to
     */
    public void clear(Pane pane) {
        for (List<Tile> l : tiles) {
            pane.getChildren().removeAll(l);
        }
    }

    /**
     * Reveals all mines on the board when the game ends
     */
    public void revealMines() {
        for (List<Tile> l : tiles) {
            for (Tile t : l) {
                if (t.isMine()) {
                    t.updateImage();
                }
            }
        }
    }

    /**
     * Tells the application whether or not to generate a log file
     * @param bool  True or false for generating the file
     */
    public void generateLog(boolean bool) {
        log = bool;
    }

    /**
     * Tells the application to generate a log file
     * @param bool          T/F on generating a file
     * @param hash          The hash for the loading information
     * @param width         The width of the board
     * @param height        The height of the board
     * @param numMines      The number of mines on the board
     * @param difficulty    The difficulty of this board
     */
    public void generateLog(boolean bool, String hash, int width, int height, int numMines, String difficulty) {
        generateLog(bool, hash, width, height, numMines, false, difficulty);
    }

    /**
     * Tells the application to generate a log file
     * @param bool          T/F on generating a file
     * @param hash          The hash for the loading information
     * @param width         The width of the board
     * @param height        The height of the board
     * @param numMines      The number of mines on the board
     * @param difficulty    The difficulty of this board
     * @param keepLogs      Whether or not to keep all data logs when the user specifies a seed
     */
    public int generateLog(boolean bool, String hash, int width, int height, int numMines,
                            boolean keepLogs, String difficulty) {
        log = bool;
        if (log) {
            try {
                if (keepLogs != saveLogs) {
                    saveLogs = keepLogs;
                    logCount = 0;
                }
                if (keepLogs){
                    writer = new PrintWriter(System.getProperty("user.dir") + "\\Logs\\" +
                            seed + "_" + hash + "_attempt_" + ++logCount + ".txt");
                    writer.write("File seed and hash: " + seed + hash + "\r\n");
                    writer.write("Difficulty: " + difficulty.substring(5) + "\r\n");
                    writer.write("Board width: " + width + "\r\n");
                    writer.write("Board height: " + height + "\r\n");
                    writer.write("Total number of mines: " + numMines + "\r\n");
                } else {
                    writer = new PrintWriter( System.getProperty("user.dir") + "\\Logs\\" +
                            + seed + "_" + hash + ".txt");
                    writer.write("File seed and hash: " + seed + hash + "\r\n");
                    writer.write("Difficulty: " + difficulty.substring(5) + "\r\n");
                    writer.write("Board width: " + width + "\r\n");
                    writer.write("Board height: " + height + "\r\n");
                    writer.write("Total number of mines: " + numMines + "\r\n");
                }
            } catch (IOException e) {
                File dir = new File(System.getProperty("user.dir") + "\\Logs");
                if (dir.mkdirs()) {
                    generateLog(bool, hash, width, height, numMines, keepLogs, difficulty);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Critical Error!");
                    alert.setHeaderText("Error!");
                    alert.setContentText("Error! The log files could not be created\n" +
                            "due to an I/O error!");
                    alert.showAndWait();
                }
            }
        }
        return logCount;
    }

    /**
     * Tells the application to generate a log file
     * @param bool          T/F on generating a file
     * @param hash          The hash for the loading information
     * @param width         The width of the board
     * @param height        The height of the board
     * @param numMines      The number of mines on the board
     * @param keepLogs      Whether or not to keep all data logs when the user specifies a seed
     * @param difficulty    The difficulty of this board
     * @param logs          The total number of logs already created for that seed
     */
    public int generateLog(boolean bool, String hash, int width, int height, int numMines,
                           boolean keepLogs, int logs, String difficulty) {
        logCount = logs;
        saveLogs = keepLogs;
        return generateLog(bool, hash, width, height, numMines, keepLogs, difficulty);
    }

    /**
     * Closes the log writer file
     * @param result    The result of the match, true for win false for loss
     */
    public void closeOutput(boolean result) {
        if (result) {
            writer.write("Final result: Won!");
        } else {
            writer.write("Final result: Loss!");
        }
        writer.close();
    }

    /**
     * Closes the log writer file when ended before a game has ended
     * @param result    What action was done to end the game
     */
    public void closeOutput(String result) {
        writer.write("Game ended early due to a " + result + " requested by the user.");
        writer.close();
    }

    public String getSeed() {
        return Long.toString(seed);
    }

    /**
     * Determines if a writer is currently writing a log file
     * @return  True if the writer exists, false otherwise
     */
    public boolean outputLog() {
        try {
            writer.write("");
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean parseBoard(int x, int y) {
        Tile temp = tiles.get(x).get(y);
        if (temp.isSelected() || temp.isFlagged()) {
            return true;
        }
        numClickedTiles++;
        int res = temp.onClick();
        if (res == -1) {
            return false;
        } else if (res == 0) {
            if (x + 1 < columns) {
                if (!tiles.get(x + 1).get(y).isMine()) {
                    parseBoard(x + 1, y);
                }
            }
            if (x - 1 >= 0) {
                if (!tiles.get(x - 1).get(y).isMine()) {
                    parseBoard(x - 1, y);
                }
            }
            if (y + 1 < rows) {
                if (!tiles.get(x).get(y + 1).isMine()) {
                    parseBoard(x, y + 1);
                }
            }
            if (y + 1 < rows && x + 1 < columns) {
                if (!tiles.get(x + 1).get(y + 1).isMine()) {
                    parseBoard(x + 1, y + 1);
                }
            }
            if (y + 1 < rows && x - 1 >= 0) {
                if (!tiles.get(x - 1).get(y + 1).isMine()) {
                    parseBoard(x - 1, y + 1);
                }
            }
            if (y - 1 >= 0 && x + 1 < columns) {
                if (!tiles.get(x + 1).get(y - 1).isMine()) {
                    parseBoard(x + 1, y - 1);
                }
            }
            if (y - 1 >= 0 && x - 1 >= 0) {
                if (!tiles.get(x - 1).get(y - 1).isMine()) {
                    parseBoard(x - 1, y - 1);
                }
            }
            if (y - 1 >= 0) {
                if (!tiles.get(x).get(y - 1).isMine()) {
                    parseBoard(x, y - 1);
                }
            }
            return true;
        } else {
            return true;
        }
    }

    private void createBoard(int col, int row) {
        for (int i = 0; i < col; i++) {
            List<Tile> temp = new ArrayList<>();
            for (int j = 0; j < row; j++) {
                temp.add(new Tile(i, j, this, scaleMultiplier));
            }
            tiles.add(temp);
        }
    }

    private void initMines(int mines, String seed) {
        int i = 0;
        if (Long.parseLong(seed) == 0) {
            this.seed = System.nanoTime();
        } else {
            this.seed = Long.parseLong(seed);
        }
        Random rand = new Random(this.seed);
        int rowSize = rows;
        int colSize = columns;
        while (i < mines) {
            int x = rand.nextInt(colSize);
            int y = rand.nextInt(rowSize);
            Tile tile = tiles.get(x).get(y);
            if (!tile.isMine()) {
                tile.setMine();
                i++;
            }
        }
    }

    private void setNeighbors() {
        for (int i = 0; i < tiles.size(); i++) {
            List<Tile> temp = tiles.get(i);
            for (int j = 0; j < temp.size(); j++) {
                Tile tile = temp.get(j);
                if (!tile.isMine()) {
                    tile.determineNeighbors(tiles);
                }
            }
        }
    }
}