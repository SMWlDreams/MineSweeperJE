import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;

public class Controller {
    @FXML
    private GridPane grid;
    @FXML
    private FlowPane pane;
    @FXML
    private MenuItem pause;
    @FXML
    private MenuItem restart;
    @FXML
    private MenuItem newGame;
    @FXML
    private MenuItem about;
    @FXML
    private MenuItem helpMenu;
    @FXML
    private Text flags;
    @FXML
    private Text mines;
    @FXML
    private Text time;
    @FXML
    private Text difficulty;
    @FXML
    private MenuItem highScore;
    @FXML
    private CheckMenuItem log;
    @FXML
    private Menu file;
    @FXML
    private Menu game;
    @FXML
    private Menu help;
    @FXML
    private HBox gameStats1;
    @FXML
    private HBox gameStats2;
    @FXML
    private HBox playback1;
    @FXML
    private HBox playback2;
    @FXML
    private VBox box;
    @FXML
    private Text currentStep;
    @FXML
    private Text move;
    @FXML
    private Menu playbackMenu;
    @FXML
    private Button auto;
    @FXML
    private CheckMenuItem displayFlags;

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
    private Timeline autoplay;
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
    private int cycleCount = 0;
    private boolean isPlayback = false;
    private Playback playback;
    private boolean playing = false;
    private double autoplayMoveDelay = 1.0;
    private int currentMove = 0;

    /**
     * Starts the main application on clicking new
     * @param actionEvent   Event sent by clicking new
     */
    public void start(ActionEvent actionEvent) {
        try {
            launch(NewGame.PROPERTIES);
        } catch (IOException e) {
            System.out.println("APPLICATION IS CORRUPT! PLEASE RE-DOWNLOAD!");
        }
    }

    /**
     * Opens the window to save a specified seed
     * @param actionEvent   Event sent by javafx
     */
    public void setSeed(ActionEvent actionEvent) {
        try {
            launch(SetSeed.PROPERTIES);
        } catch (IOException e) {
            System.out.println("APPLICATION IS CORRUPT! PLEASE RE-DOWNLOAD!");
        }
    }

    /**
     * Shows the high scores for the current difficulty
     * @param actionEvent   Event sent by javafx
     */
    public void showHighScores(ActionEvent actionEvent) {
        try {
            launch(HighScores.PROPERTIES);
        } catch (IOException e) {
            System.out.println("APPLICATION IS CORRUPT! PLEASE RE-DOWNLOAD!");
        }
    }

    /**
     * Shows the hotkeys
     * @param actionEvent   Event sent by javafx
     */
    public void showHotkeyWindow(ActionEvent actionEvent) {
        try {
            launch(Hotkeys.PROPERTIES);
        } catch (IOException e) {
            System.out.println("APPLICATION IS CORRUPT! PLEASE RE-DOWNLOAD!");
        }
    }

    /**
     * Shows the launch settings menu
     * @param actionEvent   Event sent by javafx
     */
    public void showSettingsMenu(ActionEvent actionEvent) {
        try {
            launch(LaunchSettings.PROPERTIES);
        } catch (IOException e) {
            System.out.println("APPLICATION IS CORRUPT! PLEASE RE-DOWNLOAD!");
        }
    }

    /**
     * Shows the settings menu
     * @param actionEvent   Event sent by javafx
     */
    public void showOtherSettingsMenu(ActionEvent actionEvent) {
        try {
            launch(Settings.PROPERTIES);
        } catch (IOException e) {
            System.out.println("APPLICATION IS CORRUPT! PLEASE RE-DOWNLOAD!");
        }
    }

    /**
     * Displays the about menu
     * @param actionEvent   Event sent by javafx
     */
    public void showAboutMenu(ActionEvent actionEvent) {
        try {
            launch(About.PROPERTIES);
        } catch (IOException e) {
            System.out.println("APPLICATION IS CORRUPT! PLEASE RE-DOWNLOAD!");
        }
    }

    /**
     * Displays the help window
     * @param actionEvent   Event sent by javafx
     */
    public void showHelpWindow(ActionEvent actionEvent) {
        try {
            launch(Help.PROPERTIES);
        } catch (IOException e) {
            System.out.println("APPLICATION IS CORRUPT! PLEASE RE-DOWNLOAD!");
        }
    }

    /**
     * Enters playback mode if it not currently active
     * @param actionEvent   Event sent by javafx
     */
    public void enterPlaybackMode(ActionEvent actionEvent) {
        if (!isPlayback) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm?");
            alert.setHeaderText("Enter Playback?");
            alert.setContentText("Undergoing this action will enter playback mode.\n" +
                    "Any current game progress will be lost!\n" +
                    "Are you sure you wish to proceed?");
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.OK)) {
                FileChooser chooser = new FileChooser();
                chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                chooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("Minesweeper Logs", "*.msl"));
                File file = chooser.showOpenDialog(new Stage());
                if (file != null) {
                    try {
                        playback = new Playback(file);
                        timeline.stop();
                        clear();
                        playback.setController(this);
                        playback.generate(pane);
                        toStart(actionEvent);
                        playback.setDisplayFlags(displayFlags.isSelected());
                        box.getChildren().removeAll(gameStats1, gameStats2);
                        box.getChildren().addAll(playback1, playback2);
                        restart.setDisable(true);
                        pause.setDisable(true);
                        playback1.setPrefWidth(pane.getPrefWidth());
                        playback2.setPrefWidth(pane.getPrefWidth());
                        for (Node n : playback1.getChildren()) {
                            Text t = (Text) n;
                            t.setWrappingWidth(playback1.getPrefWidth() / 4.0);
                        }
                        for (Node n : playback2.getChildren()) {
                            Button b = (Button) n;
                            b.setPrefWidth(playback2.getPrefWidth() / 5.0);
                        }
                        isPlayback = true;
                    } catch (NullPointerException | FileNotFoundException e) {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid File");
                        alert.setHeaderText("Invalid File");
                        alert.setContentText("The specified file could not be found or does not exist" +
                                ".\nPlease ensure the file is in the specified directory.");
                        alert.showAndWait();
                    } catch (IOException | InvalidLogException e) {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid File");
                        alert.setHeaderText("Invalid File");
                        alert.setContentText("The specified file contains invalid file formatting.\n" +
                                "Please only select unmodified minesweeper log files.");
                        alert.showAndWait();
                    } catch (InvalidCoordinateException e) {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Coordinate");
                        alert.setHeaderText("Invalid Coordinate");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    } catch (InvalidCountException | InvalidDimensionException e) {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Board Dimension");
                        alert.setHeaderText("Invalid Board Dimension");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            }
        }
    }

    /**
     * Selects a new log file
     * @param actionEvent   Event sent by javafx
     */
    public void selectLog(ActionEvent actionEvent) {
        if (isPlayback) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Minesweeper Logs", "*.msl"));
            File file = chooser.showOpenDialog(new Stage());
            if (file != null) {
                try {
                    playback.clear(pane);
                    playback = new Playback(file);
                    playback.setController(this);
                    playback.generate(pane);
                    toStart(actionEvent);
                    playback.setDisplayFlags(displayFlags.isSelected());
                    playback1.setPrefWidth(pane.getPrefWidth());
                    playback2.setPrefWidth(pane.getPrefWidth());
                    for (Node n : playback1.getChildren()) {
                        Text t = (Text) n;
                        t.setWrappingWidth(playback1.getPrefWidth() / 4.0);
                    }
                    for (Node n : playback2.getChildren()) {
                        Button b = (Button) n;
                        b.setPrefWidth(playback2.getPrefWidth() / 5.0);
                    }
                } catch (NullPointerException | FileNotFoundException e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid File");
                    alert.setHeaderText("Invalid File");
                    alert.setContentText("The specified file could not be found or does not exist" +
                            ".\nPlease ensure the file is in the specified directory.");
                    alert.showAndWait();
                } catch (IOException | InvalidLogException e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid File");
                    alert.setHeaderText("Invalid File");
                    alert.setContentText("The specified file contains invalid file formatting.\n" +
                            "Please only select unmodified minesweeper log files.");
                    alert.showAndWait();
                } catch (InvalidCoordinateException e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Coordinate");
                    alert.setHeaderText("Invalid Coordinate");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                } catch (InvalidCountException | InvalidDimensionException e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Board Dimension");
                    alert.setHeaderText("Invalid Board Dimension");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Sets the width, height, and scale multiplier values for a playback board to autoscale the UI
     * @param width         The width of the board
     * @param height        The height of the board
     * @param multiplier    The scale multiplier for the tile sizes
     */
    public void setVars(int width, int height, int multiplier) {
        this.width = width;
        this.height = height;
        scaleMultiplier = multiplier;
        adjustBoard();
    }

    /**
     * Exits log file playback mode and starts a new game
     * @param actionEvent   Event sent by javafx
     */
    public void exitPlaybackMode(ActionEvent actionEvent) {
        exitPlaybackMode(true);
    }

    /**
     * Jumps to the start of the playback and kills the autoplay if it is running
     * @param actionEvent   Event sent by javafx
     */
    public void toStart(ActionEvent actionEvent) {
        if (playing) {
            autoplay(actionEvent);
        }
        playback.toStart();
        currentMove = 0;
        move.setText("None");
        currentStep.setText("0");
    }

    /**
     * Steps back one move in the playback and kills the autoplay if it is running
     * @param actionEvent   Event sent by javafx
     */
    public void stepBack(ActionEvent actionEvent) {
        if (playing) {
            autoplay(actionEvent);
        }
        String s = playback.stepBack();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                move.setText("Undo " + s.substring(0, i));
                currentStep.setText(s.substring(i + 1));
                currentMove--;
                break;
            }
        }
    }

    /**
     * Steps forward one move in the playback and kills the autoplay if it is currently running
     * @param actionEvent   Event sent by javafx
     */
    public void stepForward(ActionEvent actionEvent) {
        String s = playback.stepForward();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                i++;
                move.setText(s.substring(0, i));
                currentStep.setText(s.substring(i + 1));
                currentMove++;
                break;
            }
        }
    }

    /**
     * Jumps to the end of the playback and kills the autoplay if it is currently running
     * @param actionEvent   Event sent by javafx
     */
    public void toEnd(ActionEvent actionEvent) {
        if (playing) {
            autoplay(actionEvent);
        }
        String s = playback.toEnd();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                i++;
                move.setText(s.substring(0, i));
                currentStep.setText(s.substring(i + 1));
                currentMove = playback.getNumberOfMoves() - 1;
                break;
            }
        }
    }

    /**
     * Runs an autoplay on the current playback or kills the current autoplay
     * @param actionEvent   Event sent by javafx
     */
    public void autoplay(ActionEvent actionEvent) {
        if (!playing) {
            autoplay = new Timeline();
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(autoplayMoveDelay), e -> stepForward(actionEvent));
            autoplay.setCycleCount(playback.getNumberOfMoves() - currentMove);
            autoplay.setOnFinished(this::autoplay);
            autoplay.getKeyFrames().add(keyFrame);
            auto.setText("Stop");
            playing = true;
            autoplay.play();
        } else {
            auto.setText("Autoplay");
            autoplay.stop();
            playing = false;
        }
    }

    /**
     * Tells the current playback whether or not to display flag objects during playback
     * @param actionEvent   Event sent by javafx
     */
    public void displayFlags(ActionEvent actionEvent) {
        if (playback != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Restart");
            alert.setHeaderText("Restart?");
            alert.setContentText("Changing this setting will require you to reload\n" +
                    "the current playback file.\n" +
                    "Are you sure you wish to restart?");
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.OK)) {
                toStart(actionEvent);
                playback.setDisplayFlags(displayFlags.isSelected());
            } else {
                displayFlags.setSelected(!displayFlags.isSelected());
            }
        }
    }

    /**
     * Changes the audoplay frequency to the desired value
     * @param actionEvent   Event sent by javafx
     */
    public void setAutoplayFrequency(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog(autoplayMoveDelay + "");
        dialog.setTitle("Set Autoplay Frequency");
        dialog.setHeaderText("Set Autoplay Frequency");
        dialog.setContentText("Please enter a value greater than 0 to wait between\n" +
                "moves while in autoplay.");
        dialog.showAndWait();
        String s = dialog.getResult();
        try {
            double d = Double.parseDouble(s);
            if (d <= 0) {
                throw new InputMismatchException("Invalid value");
            }
            autoplayMoveDelay = d;
        } catch (InputMismatchException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Input");
            alert.setTitle("Invalid Input");
            alert.setContentText("User input " + s + "\n" +
                    "is not a valid value for the autoplay delay.");
            alert.showAndWait();
        }
    }

    /**
     * Tells the board whether or not to generate a log file
     * @param actionEvent   The event sent by javafx
     */
    public void generateLog(ActionEvent actionEvent) {
        log.setSelected(false);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Restart");
        alert.setHeaderText("Restart?");
        alert.setContentText("Changing log generation settings will require a restart.\n" +
                "Continue?");
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
    public void restart(ActionEvent actionEvent) {
        if (setSeed) {
            setHashSeed(board.getSeed(), hash);
        } else if (start && !run && !paused) {
            restartGame();
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
                restartGame();
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
            pause.setText("Resume         (" + LoadedSettings.getHotkeys()[1] + ")");
            partialLockout(true);
        } else if (start) {
            run = true;
            paused = false;
            timeline.play();
            pause.setText("Pause            (" + LoadedSettings.getHotkeys()[1] + ")");
            partialLockout(false);
        }
    }

    /**
     * Changes the tile underneath the cursor and the coordinates
     * @param mouseEvent    Event sent by clicking the mouse
     */
    public void onClick(MouseEvent mouseEvent) {
        if (start && run && !isPlayback) {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                numFlags = board.setFlagged(mouseEvent);
                flags.setText("     " + numFlags);
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
        setHotkeyTexts();
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
    public void begin() {
        if (!isPlayback || exitPlaybackMode(false)) {
            if (board != null) {
                if (board.outputLog()) {
                    if (GUI.promptSaveLog()) {
                        board.closeOutput();
                    } else {
                        board.deleteLog();
                    }
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
            cycleCount = 0;
            start = true;
            run = true;
        }
    }

    /**
     * Clears the board
     */
    public void clear() {
        board.clear(pane);
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Determines if the game has began or not
     * @return  True if started, false otherwise
     */
    public boolean began() {
        return start;
    }

    /**
     * Creates the initial game at launch based off of data found in the LaunchSettings.cfg file and
     * sets any relevant settings
     */
    public void initialGame() {
        if (board != null) {
            clear();
        }
        box.getChildren().removeAll(playback1, playback2);
        try {
            LoadedSettings.load();
            String[] settings = LoadedSettings.getLaunchSettings();
            if (settings[0].equalsIgnoreCase("Custom")) {
                if (settings[1].equalsIgnoreCase("False")) {
                    if (settings[4].equalsIgnoreCase("True")) {
                        outputLog = true;
                        keepLogs = true;
                        log.setSelected(true);
                    } else {
                        outputLog = false;
                        keepLogs = false;
                        log.setSelected(false);
                    }
                    SetSeed seed = new SetSeed();
                    seed.setController(this);
                    if (settings[3].equalsIgnoreCase("Null")) {
                        seed.generateSeed();
                        seed.init();
                    } else {
                        seed.setSeed(settings[3]);
                    }
                    setHotkeyTexts();
                    startTimeUpdate();
                } else {
                    String seed = settings[2];
                    String hash = settings[3];
                    if (settings[4].equalsIgnoreCase("True")) {
                        outputLog = true;
                        keepLogs = true;
                        log.setSelected(true);
                    } else {
                        outputLog = false;
                        keepLogs = false;
                        log.setSelected(false);
                    }
                    SetSeed seeds = new SetSeed();
                    seeds.setController(this);
                    seeds.setSeed(seed, hash);
                    setHotkeyTexts();
                    startTimeUpdate();
                }
            } else {
                switch (settings[0]) {
                    case "Easy":
                        setDifficulty(1);
                        break;
                    case "Intermediate":
                        setDifficulty(2);
                        break;
                    case "Expert":
                        setDifficulty(3);
                        break;
                }
                if (settings[4].equalsIgnoreCase("True")) {
                    outputLog = true;
                    keepLogs = true;
                    log.setSelected(true);
                } else {
                    outputLog = false;
                    keepLogs = false;
                    log.setSelected(false);
                }
                setHotkeyTexts();
                begin();
                startTimeUpdate();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Critical Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Error! The settings file cannot be found!\n" +
                    "Generating a new file...");
            alert.showAndWait();
            GenerateSettings.xmlGeneration();
            setDifficulty(1);
            outputLog = false;
            keepLogs = false;
            setHotkeyTexts();
            begin();
            startTimeUpdate();
        } catch (InvalidSettingsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Critical Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Error! The settings file is corrupted!\n" +
                    "Generating a new file...");
            alert.showAndWait();
            LoadedSettings.generateNewFile();
            switch (LoadedSettings.getErrorLevel()) {
                case 1:
                    GenerateSettings.updateSettings(GenerateSettings.DEFAULT_SETTINGS,
                            LoadedSettings.getHotkeys(), LoadedSettings.getLogSettings());
            }
//            int errorLevel = GenerateSettings.getErrorLevel();
//            if (errorLevel == 1) {
//                GenerateSettings.updateSettings(GenerateSettings.DEFAULT_SETTINGS, LoadedSettings.getHotkeys());
//                setDifficulty(1);
//                outputLog = false;
//                keepLogs = false;
//                setHotkeyTexts();
//                begin();
//                startTimeUpdate();
//            } else if (errorLevel == 2) {
//                GenerateSettings.updateSettings(LoadedSettings.getLaunchSettings(), Hotkeys.DEFAULT_HOTKEYS);
//                initialGame();
//            } else {
//                GenerateSettings.xmlGeneration();
//                setDifficulty(1);
//                outputLog = false;
//                keepLogs = false;
//                setHotkeyTexts();
//                begin();
//                startTimeUpdate();
//            }
        }
    }

    /**
     * Closes the application
     * @param event   Event send by javafx
     */
    public void close(Event event) {
        try {
            if (board.outputLog()) {
                if (GUI.promptSaveLog()) {
                    board.closeOutput();
                } else {
                    board.deleteLog();
                }
            }
        } catch (NullPointerException ignored) {
        } finally {
            Platform.exit();
        }
    }

    /**
     * Sets the hash seed for a new game
     * @param seed  The seed to populate RNG
     * @param hash  The hash to build the board
     */
    public void setHashSeed(String seed, String hash) {
        if (!isPlayback || exitPlaybackMode(false)) {
            this.hash = hash;
            int height = Integer.parseInt(hash.substring(2, 4));
            int width = Integer.parseInt(hash.substring(4, 6));
            int mine = Integer.parseInt(hash.substring(6));
            customDimensions(height, width, mine);
            if (board != null) {
                clear();
            }
            if (outputLog && board != null) {
                if (board.outputLog()) {
                    if (GUI.promptSaveLog()) {
                        board.closeOutput();
                    } else {
                        board.deleteLog();
                    }
                }
            } else if (board != null) {
                board.generateLog(false);
            }
            board = new Board(width, height, numMines, pane, scaleMultiplier, seed);
            if (outputLog) {
                if (keepLogs) {
                    logCount = board.generateLog(true, hash, width, height, numMines,
                            true, logCount, difficulty.getText());
                } else {
                    logCount = 0;
                    logCount = board.generateLog(true, hash, width, height, numMines,
                            false, logCount, difficulty.getText());
                }
            } else {
                board.generateLog(false);
            }
            adjustBoard();
            startTime = 0;
            timeline.stop();
            cycleCount = 0;
            timeline.play();
            pause.setDisable(false);
            restart.setDisable(false);
            start = true;
            run = true;
            play = true;
            setSeed = true;
        }
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

    /**
     * Handles all the code for creating new windows while running the application
     * @param args          The individual PROPERTIES array from whichever class is being used as
     *                     the controller for the new window
     * @throws IOException  If the FXML File specified in the PROPERTIES array is missing
     */
    public void launch(String[] args) throws IOException {
        run = false;
        timeline.stop();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource(args[0]).openStream());
        Scene scene = new Scene(root);
        Controllers controller = loader.getController();
        newWindowStage = new Stage();
        newWindowStage.setScene(scene);
        newWindowStage.getIcons().add(Tile.MINE_IMAGE);
        newWindowStage.setTitle(args[1]);
        String className = controller.getClass().getName();
        if (className.equalsIgnoreCase("Help") || className.equalsIgnoreCase("SetSeed")
                || className.equalsIgnoreCase("LaunchSettings")) {
            newWindowStage.setOnShown(e -> controller.launch(getSeed()));
        } else if (className.equalsIgnoreCase("HighScores")) {
            newWindowStage.setOnShown(e -> controller.launch(difficulty.getText().substring(5)));
        } else if (className.equalsIgnoreCase("Hotkeys")) {
            newWindowStage.setOnShown(e -> controller.launch("true"));
        } else if (className.equalsIgnoreCase("About")) {
            newWindowStage.setOnShown(e -> controller.launch(About.VERSION_ID));
        }
        newWindowStage.setOnCloseRequest(e -> closeNewWindow());
        newWindowStage.setResizable(false);
        controller.setController(this);
        lockout(true);
        if (className.equalsIgnoreCase("HighScores")) {
            newWindowStage.show();
        } else {
            newWindowStage.showAndWait();
        }
    }

    /**
     * Parses user input to determine if they pressed a hotkey or not
     * @param character The character string pressed by the user
     */
    public void parseInput(String character) {
        String[] hotkeys = LoadedSettings.getHotkeys();
        if (character.equalsIgnoreCase(hotkeys[0]) && !restart.isDisable() && !game.isDisable()) {
            restart(new ActionEvent());
        } else if (character.equalsIgnoreCase(hotkeys[1]) && !pause.isDisable() && !game.isDisable()) {
            pause(new ActionEvent());
        } else if (character.equalsIgnoreCase(hotkeys[2]) && !file.isDisable()) {
            start(new ActionEvent());
        } else if (character.equalsIgnoreCase(hotkeys[3]) && !help.isDisable()) {
            showHelpWindow(new ActionEvent());
        } else if (character.equalsIgnoreCase(hotkeys[4]) && !highScore.isDisable() && !game.isDisable()) {
            showHighScores(new ActionEvent());
        } else if (character.equalsIgnoreCase(hotkeys[5]) && !help.isDisable()) {
            showAboutMenu(new ActionEvent());
        }
    }

    /**
     * Initial parsing of the input character in order to determine if the hard settings reset
     * has been entered
     * @param keyEvent  The key event sent from the main javafx window
     */
    public void parseInput(KeyEvent keyEvent) {
        if (!(keyEvent.getCode().equals(KeyCode.CONTROL))) {
            if (keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.R)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Reset Settings");
                alert.setHeaderText("Reset Settings?");
                alert.setContentText("Are you sure you want to hard-reset the launch settings?");
                alert.showAndWait();
//                if (alert.getResult().equals(ButtonType.OK)) {
//                    GenerateSettings.updateSettings(GenerateSettings.DEFAULT_SETTINGS,
//                            LoadedSettings.getHotkeys());
                    try {
                        LoadedSettings.load();
                    } catch (IOException e) {
                        System.out.println("F");
                    }
                    run = false;
                    restart(new ActionEvent());
//                }
            } else {
                parseInput(keyEvent.getText());
            }
        }
    }

    /**
     * Closes the register high scores window (note: this is different than every other window)
     */
    public void closeRHSWindow() {
        newWindowStage.close();
        showHighScores(new ActionEvent());
    }

    /**
     * Changes the window to be manually resizable by the user
     * @param actionEvent   Event sent by javafx
     */
    public void setResizable(ActionEvent actionEvent) {
        gui.setResizable();
    }

    private String getSeed() {
        String temp = board.getSeed();
        temp += "t4";
        if (height < 10) {
            temp += "0" + height;
        } else {
            temp += Integer.toString(height);
        }
        if (width < 10) {
            temp += "0" + width;
        } else {
            temp += Integer.toString(width);
        }
        if (numMines < 10) {
            temp += "00" + numMines;
        } else if (numMines < 100) {
            temp += "0" + numMines;
        } else {
            temp += Integer.toString(numMines);
        }
        return temp;
    }

    private void adjustBoard() {
        score = 1000;
        play = true;
        grid.setPrefHeight((height * Tile.SCALE * scaleMultiplier) + 50);
        grid.setPrefWidth((width * Tile.SCALE * scaleMultiplier + 16) > 460 ?
                (width * Tile.SCALE * scaleMultiplier + 16) : 460);
        pane.setPrefHeight(height * Tile.SCALE * scaleMultiplier);
        pane.setPrefWidth(width * Tile.SCALE * scaleMultiplier);
        gui.setDimensions((width * Tile.SCALE * scaleMultiplier + 16) > 460 ?
                        (width * Tile.SCALE * scaleMultiplier + 16) : 460,
                (height * Tile.SCALE * scaleMultiplier) + 100);
        mines.setText("     " + numMines);
        flags.setText("     " + numMines);
        numFlags = numMines;
    }

    private void startTimeUpdate() {
        if (timeline.getKeyFrames().size() == 0) {
            KeyFrame keyFrame = new KeyFrame(Duration.millis(1), e -> updateTimeText());
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
        }
    }

    private synchronized void updateTimeText() {
        cycleCount++;
        if (cycleCount == 1000) {
            time.setText("     " + ++startTime + " seconds");
            cycleCount = 0;
        }
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
                newScore = score - (startTime * 3);
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
        newWindowStage.getIcons().add(Tile.MINE_IMAGE);
        newWindowStage.setTitle("High Score");
        newWindowStage.setOnShown(e -> highScores.setInfo(score, rank, difficulty));
        newWindowStage.setOnCloseRequest(e -> closeRHSWindow());
        newWindowStage.setResizable(false);
        lockout(true);
        newWindowStage.showAndWait();
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
        playbackMenu.setDisable(lock);
    }

    private void partialLockout(boolean lock) {
        file.setDisable(lock);
        restart.setDisable(lock);
        help.setDisable(lock);
        highScore.setDisable(lock);
        playbackMenu.setDisable(lock);
    }

    private void setHotkeyTexts() {
        String[] hotkeys = LoadedSettings.getHotkeys();
        restart.setText("Restart          (" + hotkeys[0] + ")");
        pause.setText("Pause            (" + hotkeys[1] + ")");
        newGame.setText("New Game  (" + hotkeys[2] + ")");
        helpMenu.setText("Help         (" + hotkeys[3] + ")");
        highScore.setText("High Scores   (" + hotkeys[4] + ")");
        about.setText("About      (" + hotkeys[5] + ")");
    }

    private void restartGame() {
        clear();
        if (board != null) {
            if (board.outputLog()) {
                if (GUI.promptSaveLog()) {
                    board.closeOutput();
                } else {
                    board.deleteLog();
                }
            }
        }
        board = new Board(width, height, numMines, pane, scaleMultiplier);
        if (outputLog) {
            board.generateLog(true, hash, width, height, numMines, difficulty.getText());
        } else {
            board.generateLog(false);
        }
        mines.setText("     " + numMines);
        flags.setText("     " + numMines);
        numFlags = numMines;
        pause.setDisable(false);
        startTime = 0;
        timeline.stop();
        timeline.play();
        start = true;
        run = true;
        play = true;
    }

    private boolean exitPlaybackMode(boolean buttonExit) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm?");
        alert.setHeaderText("Exit Playback?");
        alert.setContentText("Undergoing this action will exit playback mode.\n" +
                "Are you sure you wish to proceed?");
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.OK)) {
            box.getChildren().addAll(gameStats1, gameStats2);
            box.getChildren().removeAll(playback1, playback2);
            restart.setDisable(false);
            pause.setDisable(false);
            playback.clear(pane);
            playback = null;
            isPlayback = false;
            if (buttonExit) {
                initialGame();
            }
            return true;
        }
        return false;
    }
}