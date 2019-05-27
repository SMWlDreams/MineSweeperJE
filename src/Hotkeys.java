import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import java.io.IOException;
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

    private String restartKey;
    private String pauseKey;
    private String newGameKey;
    private String highScoreKey;
    private String helpKey;
    private String aboutKey;
    private Controller controller;

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
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Critical Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("Error! The hotkey file could not be found!\nShowing default" +
                        " hotkeys.");
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
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Critical Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("Error! The hotkey file could not be found!");
                alert.showAndWait();
                restartKey = "R";
                pauseKey = "P";
                newGameKey = "N";
                helpKey = "H";
                highScoreKey = "S";
                aboutKey = "A";
            }
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
}
