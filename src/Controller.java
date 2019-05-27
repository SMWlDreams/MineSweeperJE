import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Controller {
    @FXML
    GridPane grid;
    @FXML
    FlowPane pane;
    @FXML
    MenuItem pause;
    @FXML
    MenuItem restart;
    @FXML
    MenuItem newGame;
    @FXML
    MenuItem about;
    @FXML
    MenuItem helpMenu;
    @FXML
    Text flags;
    @FXML
    Text mines;
    @FXML
    Text time;
    @FXML
    Text difficulty;
    @FXML
    MenuItem highScore;
    @FXML
    CheckMenuItem log;
    @FXML
    Menu file;
    @FXML
    Menu game;
    @FXML
    Menu help;

    private Stage newWindowStage;
    private Board board;
    private int height;
    private int width;
    private int numMines;
    private boolean start = false;
    private boolean run;
    private long startTime;
    private int numFlags;
    private final Timeline timeline = new Timeline();
    private GUI gui;
    private int scaleMultiplier = 1;
    private boolean paused = false;
    private boolean setSeed = false;
    private String hash;
    private boolean play = false;
    private long score;
    private boolean outputLog = false;
    private boolean keepLogs = false;
    private boolean showMines = false;
    private int logCount = 0;
    private Hotkeys hotkeys = new Hotkeys();

    /**
     * Starts the main application on clicking new
     * @param actionEvent   Event sent by clicking new
     */
    public void start(ActionEvent actionEvent) throws IOException {
        run = false;
        setSeed = false;
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("NewGame.fxml").openStream());
        NewGameController controller = loader.getController();
        controller.setController(this);
        Scene scene = new Scene(root);
        newWindowStage = new Stage();
        newWindowStage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "\\Images\\mine.png").toURI().toURL().toString()));
        newWindowStage.setOnCloseRequest(e -> closeNewWindow());
        newWindowStage.setResizable(false);
        newWindowStage.setScene(scene);
        newWindowStage.setTitle("Start New Game");
        lockout(true);
        newWindowStage.showAndWait();
    }

    /**
     * Opens the window to save a specified seed
     * @param actionEvent   Event sent by javafx
     * @throws IOException  If the FXML file is missing
     */
    public void setSeed(ActionEvent actionEvent) throws IOException {
        run = false;
        timeline.stop();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("SetSeed.fxml").openStream());
        Scene scene = new Scene(root);
        SetSeed seed = loader.getController();
        String hashSeed = getSeed();
        newWindowStage = new Stage();
        newWindowStage.setScene(scene);
        newWindowStage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "\\Images\\mine.png").toURI().toURL().toString()));
        newWindowStage.setTitle("Set Custom Seed");
        newWindowStage.setOnShown(e -> seed.setCurrentSeed(hashSeed));
        newWindowStage.setOnCloseRequest(e -> closeNewWindow());
        newWindowStage.setResizable(false);
        seed.setController(this);
        lockout(true);
        newWindowStage.showAndWait();
    }

    /**
     * Shows the high scores for the current difficulty
     * @param actionEvent   Event sent by javafx
     * @throws IOException  If the FXML file is missing
     */
    public void showHighScores(ActionEvent actionEvent) throws IOException {
        run = false;
        timeline.stop();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("HighScores.fxml").openStream());
        Scene scene = new Scene(root);
        HighScores scores = loader.getController();
        newWindowStage = new Stage();
        newWindowStage.setScene(scene);
        newWindowStage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "\\Images\\mine.png").toURI().toURL().toString()));
        newWindowStage.setTitle("High Scores");
        newWindowStage.setOnShown(e -> scores.getHighScores(difficulty.getText().substring(5)));
        newWindowStage.setOnCloseRequest(e -> closeNewWindow());
        newWindowStage.setResizable(false);
        scores.setController(this);
        lockout(true);
        newWindowStage.show();
    }

    /**
     * Shows the hotkeys
     * @param actionEvent   Event sent by javafx
     * @throws IOException  If the FXML file is missing
     */
    public void showHotkeyWindow(ActionEvent actionEvent) throws IOException {
        run = false;
        timeline.stop();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("Hotkeys.fxml").openStream());
        Scene scene = new Scene(root);
        Hotkeys hotkeys = loader.getController();
        newWindowStage = new Stage();
        newWindowStage.setScene(scene);
        newWindowStage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "\\Images\\mine.png").toURI().toURL().toString()));
        newWindowStage.setTitle("Hotkeys");
        newWindowStage.setOnShown(e -> hotkeys.generateHotKeys(true));
        newWindowStage.setOnCloseRequest(e -> closeNewWindow());
        newWindowStage.setResizable(false);
        hotkeys.setController(this);
        lockout(true);
        newWindowStage.show();
    }

    /**
     * Displays the about menu
     * @param actionEvent   Event sent by javafx
     * @throws IOException  If the FXML file is missing
     */
    public void showAboutMenu(ActionEvent actionEvent) throws IOException {
        run = false;
        timeline.stop();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("About.fxml").openStream());
        Scene scene = new Scene(root);
        About about = loader.getController();
        newWindowStage = new Stage();
        newWindowStage.setScene(scene);
        newWindowStage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "\\Images\\mine.png").toURI().toURL().toString()));
        newWindowStage.setTitle("About");
        newWindowStage.setOnCloseRequest(e -> closeNewWindow());
        newWindowStage.setResizable(false);
        about.setController(this);
        lockout(true);
        newWindowStage.showAndWait();
    }

    /**
     * Displays the help window
     * @param actionEvent   Event sent by javafx
     * @throws IOException  If the FXML file is missing
     */
    public void showHelpWindow(ActionEvent actionEvent) throws IOException {
        run = false;
        timeline.stop();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("Help.fxml").openStream());
        Scene scene = new Scene(root);
        Help help = loader.getController();
        newWindowStage = new Stage();
        newWindowStage.setScene(scene);
        newWindowStage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "\\Images\\mine.png").toURI().toURL().toString()));
        newWindowStage.setTitle("Help");
        newWindowStage.setOnShown(e -> help.setSeeds(getSeed()));
        newWindowStage.setOnCloseRequest(e -> closeNewWindow());
        newWindowStage.setResizable(false);
        help.setController(this);
        lockout(true);
        newWindowStage.showAndWait();
    }

    /**
     * Tells the board whether or not to generate a log file
     * @param actionEvent   The event sent by javafx
     */
    public void generateLog(ActionEvent actionEvent) throws IOException {
        log.setSelected(false);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Restart");
        alert.setHeaderText("Restart?");
        alert.setContentText("Generating a log file will require a restart. Continue?");
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.OK)) {
            if (!outputLog) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Output Location");
                alert.setTitle("Log Output");
                alert.setContentText("Your log files can be found at \n" +
                        System.getProperty("user.dir") + "\\Logs\\\"Seed_Hash.txt\".\n" +
                        "Note: if you are using a set seed log files will be erased unless\n" +
                        "you selected the \"keep separate log files\" option.");
                alert.showAndWait();
                log.setSelected(true);
                outputLog = true;
                run = false;
                restart(actionEvent);
            } else {
                log.setSelected(false);
                outputLog = false;
                run = false;
                restart(actionEvent);
            }
        }
    }

    /**
     * Restarts the game with a new board
     * @param actionEvent   The event sent by clicking restart
     */
    public void restart(ActionEvent actionEvent) throws IOException {
        if (setSeed) {
            setHashSeed(board.getSeed(), hash);
        } else if (start && !run && !paused) {
            clear();
            if (board != null) {
                if (board.outputLog()) {
                    board.closeOutput("start new game");
                }
            }
            board = new Board(width, height, numMines, pane, scaleMultiplier);
            if (outputLog) {
                board.generateLog(true, hash, width, height, numMines, difficulty.getText());
            } else {
                board.generateLog(false);
            }
            mines.setText("     " + Integer.toString(numMines));
            flags.setText("     " + Integer.toString(numMines));
            numFlags = numMines;
            pause.setDisable(false);
            startTime = 0;
            timeline.stop();
            timeline.play();
            start = true;
            run = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Restart");
            alert.setHeaderText("Restart?");
            alert.setContentText("Are you sure you want to restart? All progress will be lost!");
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.OK)) {
                if (paused) {
                    pause(actionEvent);
                }
                clear();
                if (board != null) {
                    if (board.outputLog()) {
                        board.closeOutput("start new game");
                    }
                }
                board = new Board(width, height, numMines, pane, scaleMultiplier);
                if (outputLog) {
                    board.generateLog(true, hash, width, height, numMines, difficulty.getText());
                } else {
                    board.generateLog(false);
                }
                mines.setText("     " + Integer.toString(numMines));
                flags.setText("     " + Integer.toString(numMines));
                numFlags = numMines;
                pause.setDisable(false);
                startTime = 0;
                timeline.stop();
                timeline.play();
                start = true;
                run = true;
            }
        }
    }

    /**
     * Pauses the game
     * @param actionEvent   Event sent by clicking pause
     */
    public void pause(ActionEvent actionEvent) {
        if (start && run) {
            run = false;
            paused = true;
            timeline.stop();
            pause.setText("Resume");
            partialLockout(true);
        } else if (start) {
            run = true;
            paused = false;
            timeline.play();
            pause.setText("Pause");
            partialLockout(false);
        }
    }

    /**
     * Changes the tile underneath the cursor and the coordinates
     * @param mouseEvent    Event sent by clicking the mouse
     */
    public void onClick(MouseEvent mouseEvent) {
        if (start && run) {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                numFlags = board.setFlagged(mouseEvent);
                flags.setText("     " + Integer.toString(numFlags));
            } else if (!board.onClick(mouseEvent)) {
                loss();
            } else if (board.getNumClickedTiles() == width * height - numMines) {
                win();
            }
        }
    }

    /**
     * Sets the difficulty of the game from one of the presets
     * @param difficulty    The preset difficulty identification number
     */
    public void setDifficulty(int difficulty) {
        highScore.setDisable(false);
        showMines = false;
        setSeed = false;
        switch (difficulty) {
            case 1:
                height = 9;
                width = 9;
                numMines = 10;
                scaleMultiplier = 3;
                generateHash();
                this.difficulty.setText("     Easy");
                timeline.stop();
                timeline.play();
                break;
            case 2:
                height = 16;
                width = 16;
                numMines = 40;
                scaleMultiplier = 2;
                generateHash();
                this.difficulty.setText("     Intermediate");
                timeline.stop();
                timeline.play();
                break;
            case 3:
                height = 24;
                width = 24;
                numMines = 99;
                scaleMultiplier = 1;
                generateHash();
                this.difficulty.setText("     Expert");
                timeline.stop();
                timeline.play();
                break;
        }
    }

    /**
     * Creates a board with dimensions specified by the user and saves them for restarts
     * @param height    The desired board height
     * @param width     The desired board width
     * @param numMines  The desired number of mines on the board
     */
    public void customDimensions(int height, int width, int numMines) {
        highScore.setDisable(true);
        this.width = width;
        this.height = height;
        this.numMines = numMines;
        if (height <= 10 && width <= 13) {
            scaleMultiplier = 3;
        } else if (height <= 16 && width <= 22) {
            scaleMultiplier = 2;
        } else {
            scaleMultiplier = 1;
        }
        generateHash();
        difficulty.setText("     Custom");
        timeline.stop();
        timeline.play();
    }

    /**
     * Closes the new game prompt window and starts the game
     */
    public void closeNewWindow() {
        newWindowStage.close();
        lockout(false);
        if (play) {
            run = true;
            timeline.play();
        }
    }

    /**
     * Changes the size of the window to fit the game and starts it
     */
    public void begin() throws IOException {
        if (board != null) {
            if (board.outputLog()) {
                board.closeOutput("new game");
            }
        }
        board = new Board(width, height, numMines, pane, scaleMultiplier);
        if (outputLog) {
            board.generateLog(true, hash, width, height, numMines, difficulty.getText());
        } else {
            board.generateLog(false);
        }
        adjustBoard();
        restart.setDisable(false);
        pause.setDisable(false);
        startTime = 0;
        start = true;
        run = true;
    }

    public void clear() {
        board.clear(pane);
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public boolean began() {
        return start;
    }

    /**
     * Creates the game on startup of the application
     */
    public void initialGame() throws IOException {
        setDifficulty(1);
        setHotkeyTexts();
        begin();
        startTimeUpdate();
    }

    public void close(ActionEvent actionEvent) {
        gui.close();
    }

    /**
     * Sets the hash seed for a new game
     * @param seed  The seed to populate RNG
     * @param hash  The hash to build the board
     */
    public void setHashSeed(String seed, String hash) throws IOException {
        this.hash = hash;
        int height = Integer.parseInt(hash.substring(2, 4));
        int width = Integer.parseInt(hash.substring(4, 6));
        int mine = Integer.parseInt(hash.substring(6));
        customDimensions(height, width, mine);
        board.clear(pane);
        if (outputLog && board != null) {
            if (board.outputLog()) {
                board.closeOutput("start new game");
            }
        } else if (board != null) {
            board.generateLog(false);
        }
        board = new Board(width, height, numMines, pane, scaleMultiplier, seed);
        if (outputLog) {
            if (keepLogs) {
                logCount = board.generateLog(true, hash, width, height, numMines, true, logCount,
                        difficulty.getText());
            } else {
                logCount = 0;
                logCount = board.generateLog(true, hash, width, height, numMines, true, logCount,
                        difficulty.getText());
            }
        } else {
            board.generateLog(false);
        }
        adjustBoard();
        startTime = 0;
        timeline.stop();
        timeline.play();
        pause.setDisable(false);
        start = true;
        run = true;
        setSeed = true;
    }

    /**
     * Tells the board whether or not to erase logs when using a set seed
     * @param selected  If the checkbox is selected
     */
    public void setKeepLogs(boolean selected) {
        keepLogs = selected;
        logCount = 0;
    }

    /**
     * Tells the board whether or not to show mines when using a set seed
     * @param selected  If the checkbox is selected
     */
    public void setShowMines(boolean selected) {
        showMines = selected;
    }

    /**
     * Stops the timeline on close to prevent a crash
     */
    public void stopTimeline() {
        timeline.getKeyFrames().remove(0);
        timeline.stop();
        Platform.exit();
    }

    public void parseInput(String character) {
        try {
            if (character.equalsIgnoreCase(hotkeys.getPauseKey()) && !pause.isDisable() && !game.isDisable()) {
                pause(new ActionEvent());
            } else if (character.equalsIgnoreCase(hotkeys.getRestartKey()) && !restart.isDisable() && !game.isDisable()) {
                restart(new ActionEvent());
            } else if (character.equalsIgnoreCase(hotkeys.getNewGameKey()) && !file.isDisable()) {
                start(new ActionEvent());
            } else if (character.equalsIgnoreCase(hotkeys.getHelpKey()) && !help.isDisable()) {
                showHelpWindow(new ActionEvent());
            } else if (character.equalsIgnoreCase(hotkeys.getHighScoreKey()) && !highScore.isDisable() && !game.isDisable()) {
                showHighScores(new ActionEvent());
            } else if (character.equalsIgnoreCase(hotkeys.getAboutKey()) && !help.isDisable()) {
                showAboutMenu(new ActionEvent());
            }
        } catch (IOException e) {
            System.out.println("F");
        }
    }

    private String getSeed() {
        String temp = board.getSeed();
        temp += "t4";
        if (height < 10) {
            temp += "0" + Integer.toString(height);
        } else {
            temp += Integer.toString(height);
        }
        if (width < 10) {
            temp += "0" + Integer.toString(width);
        } else {
            temp += Integer.toString(width);
        }
        if (numMines < 10) {
            temp += "00" + Integer.toString(numMines);
        } else if (numMines < 100) {
            temp += "0" + Integer.toString(numMines);
        } else {
            temp += Integer.toString(numMines);
        }
        return temp;
    }

    private void adjustBoard() {
        score = 1000;
        play = true;
        grid.setPrefHeight((height * Tile.SCALE * scaleMultiplier) + 50);
        grid.setPrefWidth((width * Tile.SCALE * scaleMultiplier + 14) > 460 ?
                (width * Tile.SCALE * scaleMultiplier + 14) : 460);
        pane.setPrefHeight(height * Tile.SCALE * scaleMultiplier);
        pane.setPrefWidth(width * Tile.SCALE * scaleMultiplier + 14);
        gui.setDimensions((width * Tile.SCALE * scaleMultiplier + 14) > 460 ?
                        (width * Tile.SCALE * scaleMultiplier + 14) : 460,
                (height * Tile.SCALE * scaleMultiplier) + 100);
        mines.setText("     " + Integer.toString(numMines));
        flags.setText("     " + Integer.toString(numMines));
        numFlags = numMines;
    }

    private void startTimeUpdate() {
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), e -> updateTimeText());
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void updateTimeText() {
        time.setText("     " + Long.toString(++startTime) + " seconds");
    }

    private void win() {
        timeline.stop();
        play = false;
        gameEnd(true);
    }

    private void loss() {
        play = false;
        timeline.stop();
        gameEnd(false);
    }

    private void gameEnd(boolean result) {
        if (!setSeed) {
            board.revealMines();
        } else if (!showMines) {
            board.revealMines();
        }
        if (log.isSelected()) {
            board.closeOutput(result);
        }
        run = false;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (!result) {
            alert.setTitle("You Lose");
            alert.setHeaderText("You Lost");
            alert.setContentText("You lost. Better luck next time!");
            alert.showAndWait();
            pause.setDisable(true);
        } else {
            alert.setTitle("You Win");
            alert.setHeaderText("Your Winner");
            alert.setContentText("You won! Now go get yourself a cookie.");
            alert.showAndWait();
            pause.setDisable(true);
            if (!setSeed) {
                checkHighScore();
            }
        }
    }

    private void checkHighScore() {
        HighScores highScores = new HighScores();
        try {
            long newScore;
            String difficulty = this.difficulty.getText().substring(5);
            if (difficulty.equalsIgnoreCase("easy")) {
                newScore = score - (startTime * 10);
            } else if (difficulty.equalsIgnoreCase("intermediate")) {
                newScore = score - (startTime * 4);
            } else {
                newScore = score - (startTime / 2);
            }
            List<Scores<String>> scores = highScores.readScores(difficulty);
            int i = 1;
            for (Scores<String> s : scores) {
                if (newScore > Long.parseLong(s.getValues().get(1))) {
                    registerHighScore(Integer.toString(i), Long.toString(newScore), difficulty);
                    break;
                }
                i++;
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Critical Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Error! The high score file cannot be found!");
            alert.showAndWait();
        }
    }

    private void registerHighScore(String rank, String score, String difficulty) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("Register.fxml").openStream());
        Scene scene = new Scene(root);
        RegisterHighScores highScores = loader.getController();
        highScores.setController(this);
        newWindowStage = new Stage();
        newWindowStage.setScene(scene);
        newWindowStage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "\\Images\\mine.png").toURI().toURL().toString()));
        newWindowStage.setTitle("High Score");
        newWindowStage.setOnShown(e -> highScores.setInfo(score, rank, difficulty));
        newWindowStage.setOnCloseRequest(e -> closeRHSWindow());
        newWindowStage.setResizable(false);
        lockout(true);
        newWindowStage.showAndWait();
    }

    public void closeRHSWindow() {
        try {
            newWindowStage.close();
            showHighScores(new ActionEvent());
        } catch (IOException e) {
            System.out.println("oops");
        }
    }

    private void generateHash() {
        hash = "t4";
        if (height < 10) {
            hash += "0" + height;
        } else {
            hash += height;
        }
        if (width < 10) {
            hash += "0" + width;
        } else {
            hash += width;
        }
        if (numMines < 10) {
            hash += "00" + numMines;
        } else if (numMines < 100) {
            hash += "0" + numMines;
        } else {
            hash += numMines;
        }
    }

    private void lockout(boolean lock) {
        file.setDisable(lock);
        game.setDisable(lock);
        help.setDisable(lock);
    }

    private void partialLockout(boolean lock) {
        file.setDisable(lock);
        restart.setDisable(lock);
        help.setDisable(lock);
        highScore.setDisable(lock);
    }

    private void setHotkeyTexts() {
        newGame.setText("New Game  (" + hotkeys.getNewGameKey() + ")");
        about.setText("About      (" + hotkeys.getAboutKey() + ")");
        helpMenu.setText("Help         (" + hotkeys.getHelpKey() + ")");
        highScore.setText("High Scores   (" + hotkeys.getHighScoreKey() + ")");
        restart.setText("Restart          (" + hotkeys.getRestartKey() + ")");
        pause.setText("Pause            (" + hotkeys.getPauseKey() + ")");
    }
}