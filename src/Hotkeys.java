import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class Hotkeys {
    @FXML
    private Text restart;
    @FXML
    private Text pause;
    @FXML
    private Text newGame;
    @FXML
    private Text help;
    @FXML
    private Text highScore;
    @FXML
    private Text about;
    @FXML
    private Button hotkeyChange;
    @FXML
    private TextField setRestart;
    @FXML
    private TextField setPause;
    @FXML
    private TextField setNewGame;
    @FXML
    private TextField setHelp;
    @FXML
    private TextField setHighScores;
    @FXML
    private TextField setAbout;
    @FXML
    private Text infoText;
    @FXML
    private Button okSave;
    @FXML
    private Button resetClear;

    private String restartKey;
    private String pauseKey;
    private String newGameKey;
    private String highScoreKey;
    private String helpKey;
    private String aboutKey;
    private Controller controller;
    private boolean edit = false;

    public Hotkeys() {
        generateHotKeys(false);
    }

    public void close(ActionEvent actionEvent) {
        controller.closeNewWindow();
    }

    public void generateHotKeys(boolean window) {
        if (window) {
            try (Scanner in = new Scanner(
                    Paths.get(System.getProperty("user.dir") + "\\Hotkeys.ini"))) {
                if (in.nextLine().equals("[input]")) {
                    while (in.hasNextLine()) {
                        String input = in.nextLine();
                        int i;
                        for (i = 1; i < input.length(); i++) {
                            if (input.charAt(i) == ']') {
                                break;
                            }
                        }
                        if (i < input.length()) {
                            String type = input.substring(0, i + 1);
                            int j;
                            if (type.equals("[/input]")) {
                                break;
                            } else if (type.equals("[restart]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                restartKey = Character.toString(input.charAt(j));
                                restart.setText(restartKey);
                            } else if (type.equals("[pause]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                pauseKey = Character.toString(input.charAt(j));
                                pause.setText(pauseKey);
                            } else if (type.equals("[new game]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                newGameKey = Character.toString(input.charAt(j));
                                newGame.setText(newGameKey);
                            } else if (type.equals("[help]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                helpKey = Character.toString(input.charAt(j));
                                help.setText(helpKey);
                            } else if (type.equals("[high scores]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                highScoreKey = Character.toString(input.charAt(j));
                                highScore.setText(highScoreKey);
                            } else if (type.equals("[about]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                aboutKey = Character.toString(input.charAt(j));
                                about.setText(aboutKey);
                            }
                        }
                    }
                } else {
                    restartKey = "R";
                    pauseKey = "P";
                    newGameKey = "N";
                    helpKey = "H";
                    highScoreKey = "S";
                    aboutKey = "A";
                    restart.setText(restartKey);
                    pause.setText(pauseKey);
                    newGame.setText(newGameKey);
                    help.setText(helpKey);
                    highScore.setText(highScoreKey);
                    about.setText(aboutKey);
                    reset();
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Critical Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("Error! The hotkey file could not be found!\nShowing default" +
                        " hotkeys.\nA new copy of the default keys has been created.");
                alert.showAndWait();
                restartKey = "R";
                pauseKey = "P";
                newGameKey = "N";
                helpKey = "H";
                highScoreKey = "S";
                aboutKey = "A";
                restart.setText(restartKey);
                pause.setText(pauseKey);
                newGame.setText(newGameKey);
                help.setText(helpKey);
                highScore.setText(highScoreKey);
                about.setText(aboutKey);
                reset();
            }
        } else {
            try (Scanner in = new Scanner(
                    Paths.get(System.getProperty("user.dir") + "\\Hotkeys.ini"))) {
                if (in.nextLine().equals("[input]")) {
                    while (in.hasNextLine()) {
                        String input = in.nextLine();
                        int i;
                        for (i = 1; i < input.length(); i++) {
                            if (input.charAt(i) == ']') {
                                break;
                            }
                        }
                        if (i < input.length()) {
                            String type = input.substring(0, i + 1);
                            int j;
                            if (type.equals("[/input]")) {
                                break;
                            } else if (type.equals("[restart]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                restartKey = Character.toString(input.charAt(j));
                            } else if (type.equals("[pause]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                pauseKey = Character.toString(input.charAt(j));
                            } else if (type.equals("[new game]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                newGameKey = Character.toString(input.charAt(j));
                            } else if (type.equals("[help]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                helpKey = Character.toString(input.charAt(j));
                            } else if (type.equals("[high scores]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                highScoreKey = Character.toString(input.charAt(j));
                            } else if (type.equals("[about]")) {
                                for (j = i; j < input.length(); j++) {
                                    if (input.charAt(j) == '[') {
                                        j++;
                                        break;
                                    }
                                }
                                aboutKey = Character.toString(input.charAt(j));
                            }
                        }
                    }
                } else {
                    restartKey = "R";
                    pauseKey = "P";
                    newGameKey = "N";
                    helpKey = "H";
                    highScoreKey = "S";
                    aboutKey = "A";
                    reset();
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Critical Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("Error! The hotkey file could not be found!\nCreating a new " +
                        "copy.");
                alert.showAndWait();
                restartKey = "R";
                pauseKey = "P";
                newGameKey = "N";
                helpKey = "H";
                highScoreKey = "S";
                aboutKey = "A";
                reset();
            }
        }
    }

    public void changeHotKeys(ActionEvent actionEvent) {
        setGuiLayout();
    }

    public void saveNewHotkeys() {
        try (
                PrintWriter outputStream =
                        new PrintWriter(new FileOutputStream(System.getProperty("user.dir") +
                                "\\Hotkeys.ini"))) {
            outputStream.write("[input]\r\n");
            String key = setRestart.getText();
            if (key.length() == 1) {
                outputStream.write("[restart] = [" + key.toUpperCase() + "]\r\n");
            } else if (key.length() > 1) {
                outputStream.write("[restart] = [" + key.substring(0, 1).toUpperCase() + "]\r\n");
            } else {
                outputStream.write("[restart] = [" + restartKey + "]\r\n");
            }
            key = setPause.getText();
            if (key.length() == 1) {
                outputStream.write("[pause] = [" + key.toUpperCase() + "]\r\n");
            } else if (key.length() > 1) {
                outputStream.write("[pause] = [" + key.substring(0, 1).toUpperCase() + "]\r\n");
            } else {
                outputStream.write("[pause] = [" + pauseKey + "]\r\n");
            }
            key = setNewGame.getText();
            if (key.length() == 1) {
                outputStream.write("[new game] = [" + key.toUpperCase() + "]\r\n");
            } else if (key.length() > 1) {
                outputStream.write("[new game] = [" + key.substring(0, 1).toUpperCase() + "]\r\n");
            } else {
                outputStream.write("[new game] = [" + newGameKey + "]\r\n");
            }
            key = setHelp.getText();
            if (key.length() == 1) {
                outputStream.write("[help] = [" + key.toUpperCase() + "]\r\n");
            } else if (key.length() > 1) {
                outputStream.write("[help] = [" + key.substring(0, 1).toUpperCase() + "]\r\n");
            } else {
                outputStream.write("[help] = [" + helpKey + "]\r\n");
            }
            key = setHighScores.getText();
            if (key.length() == 1) {
                outputStream.write("[high scores] = [" + key.toUpperCase() + "]\r\n");
            } else if (key.length() > 1) {
                outputStream.write("[high scores] = [" + key.substring(0, 1).toUpperCase() +
                        "]\r\n");
            } else {
                outputStream.write("[high scores] = [" + highScoreKey + "]\r\n");
            }
            key = setAbout.getText();
            if (key.length() == 1) {
                outputStream.write("[about] = [" + key.toUpperCase() + "]\r\n");
            } else if (key.length() > 1) {
                outputStream.write("[about] = [" + key.substring(0, 1).toUpperCase() + "]\r\n");
            } else {
                outputStream.write("[about] = [" + aboutKey + "]\r\n");
            }
            outputStream.write("[/input]");
        } catch (IOException e) {
            System.out.println("Shouldn't get here");
        }
        setGuiLayout();
    }

    public void cancel() {
        setGuiLayout();
    }

    public void reset(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reset Hotkeys");
        alert.setHeaderText("Reset Hotkeys?");
        alert.setContentText("Are you sure you want to set the hotkeys\nto their default values?");
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.OK)) {
            reset();
            controller.closeNewWindow();
        }
    }

    public String getRestartKey() {
        return restartKey;
    }

    public String getPauseKey() {
        return pauseKey;
    }

    public String getNewGameKey() {
        return newGameKey;
    }

    public String getHelpKey() {
        return helpKey;
    }

    public String getHighScoreKey() {
        return highScoreKey;
    }

    public String getAboutKey() {
        return aboutKey;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void reset() {
        try (
        PrintWriter outputStream =
                new PrintWriter(new FileOutputStream(System.getProperty("user.dir") +
                        "\\Hotkeys.ini"))) {
            outputStream.write("[input]\r\n" +
                    "[restart] = [R]\r\n" +
                    "[pause] = [P]\r\n" +
                    "[new game] = [N]\r\n" +
                    "[help] = [H]\r\n" +
                    "[high scores] = [S]\r\n" +
                    "[about] = [A]\r\n" +
                    "[/input]");
        } catch (IOException e) {
            System.out.println("Shouldn't get here");
        }
    }

    private void setGuiLayout() {
        if (!edit) {
            restart.setVisible(false);
            help.setVisible(false);
            newGame.setVisible(false);
            about.setVisible(false);
            pause.setVisible(false);
            highScore.setVisible(false);
            setAbout.setVisible(true);
            setHelp.setVisible(true);
            setHighScores.setVisible(true);
            setNewGame.setVisible(true);
            setPause.setVisible(true);
            setRestart.setVisible(true);
            hotkeyChange.setText("Currently changing hotkeys");
            hotkeyChange.setOnAction(null);
            infoText.setText("All empty fields will be unchanged");
            okSave.setText("Save");
            okSave.setOnAction(e -> saveNewHotkeys());
            resetClear.setText("Cancel");
            resetClear.setOnAction(e -> cancel());
            edit = true;
        } else {
            restart.setVisible(true);
            help.setVisible(true);
            newGame.setVisible(true);
            about.setVisible(true);
            pause.setVisible(true);
            highScore.setVisible(true);
            setAbout.setVisible(false);
            setHelp.setVisible(false);
            setHighScores.setVisible(false);
            setNewGame.setVisible(false);
            setPause.setVisible(false);
            setRestart.setVisible(false);
            hotkeyChange.setText("Click here to change hotkey keybinds");
            hotkeyChange.setOnAction(this::changeHotKeys);
            infoText.setText("The following hotkeys can be used");
            okSave.setText("OK");
            okSave.setOnAction(this::close);
            resetClear.setText("Reset");
            resetClear.setOnAction(this::reset);
            edit = false;
            generateHotKeys(true);
        }
    }
}