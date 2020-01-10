import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class LaunchSettings implements Controllers {

    /**
     * Class properties to allow loading of the window
     */
    public static final String[] PROPERTIES = {"LaunchSettings.fxml", "Launch Settings"};

    @FXML
    private TextField seed;
    @FXML
    private ComboBox<String> box;
    @FXML
    private CheckBox setSeed;
    @FXML
    private CheckBox customDimension;
    @FXML
    private TextField height;
    @FXML
    private TextField width;
    @FXML
    private TextField numMines;
    @FXML
    private CheckBox logs;

    private Controller controller;
    private String currentSeed;

    /**
     * Loads the new information and sets up the menu properly
     */
    public void start() {
        setBox();
        setSeed.setOnAction(e -> updateSettings());
        customDimension.setOnAction(e -> altUpdateSettings());
        setSelectedSettings();
    }

    /**
     * Method that handles the launch settings for each new window
     * @param string Any argument string that must be passed to the implemented method
     */
    @Override
    public void launch(String string) {
        start();
        setCurrentSeed(string);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void cancel(ActionEvent actionEvent) {
        controller.closeNewWindow();
    }

    /**
     * Saves the user selected settings into the LaunchSettings.cfg file
     * @param actionEvent   Event sent from javafx
     */
    public void save(ActionEvent actionEvent) {
        try {
            String[] settings = new String[5];
            settings[0] = box.getValue();
            settings[1] = "False";
            settings[2] = "Null";
            settings[3] = "Null";
            if (settings[0].equals("Custom")) {
                if (setSeed.isSelected()) {
                    settings[1] = "True";
                    settings[2] = this.seed.getText();
                    settings[3] = settings[2].substring(settings[2].length() - 10);
                    verify(settings[3]);
                    settings[2] = settings[2].substring(0, settings[2].length() - 9);
                    settings[3] = settings[3].substring(1);
                } else {
                    if (customDimension.isSelected()) {
                        settings[3] = "t4";
                        int h = Integer.parseInt(height.getText());
                        if (h > 24 || h < 5) {
                            throw new InvalidSeedException("Invalid height");
                        }
                        if (h < 10) {
                            settings[3] += "0" + Integer.toString(h);
                        } else {
                            settings[3] += Integer.toString(h);
                        }
                        int w = Integer.parseInt(width.getText());
                        if (w > 31 || w < 5) {
                            throw new InvalidSeedException("Invalid width");
                        }
                        if (w < 10) {
                            settings[3] += "0" + Integer.toString(w);
                        } else {
                            settings[3] += Integer.toString(w);
                        }
                        int m = Integer.parseInt(numMines.getText());
                        if (m >= h * w) {
                            throw new InvalidSeedException("Invalid number of mines");
                        }
                        if (m < 10) {
                            m = 10;
                            settings[3] += "0" + Integer.toString(m);
                        } else if (m < 100) {
                            settings[3] += "0" + Integer.toString(m);
                        } else {
                            settings[3] += Integer.toString(m);
                        }
                    }
                }
            }
            if (logs.isSelected()) {
                settings[4] = "True";
            } else {
                settings[4] = "False";
            }
            GenerateSettings.updateSettings(settings, LoadedSettings.getHotkeys(), Settings.DEFAULT_SETTINGS);
            controller.closeNewWindow();
        } catch (InvalidSeedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Seed");
            alert.setHeaderText("Invalid Seed");
            alert.setContentText("The entered seed could not be parsed.\nPlease ensure you are " +
                    "using a valid seed.");
            alert.showAndWait();
        }
    }

    public void setCurrentSeed(String seed) {
        currentSeed = seed;
    }

    private void setBox() {
        List<String> choice = new ArrayList<>();
        choice.add("Easy");
        choice.add("Intermediate");
        choice.add("Expert");
        choice.add("Custom");
        box.setItems(FXCollections.observableArrayList(choice));
        box.setOnAction(e -> updateSettings());
    }

    private void setSelectedSettings() {
        String[] settings = LoadedSettings.getLaunchSettings();
        String dif = settings[0];
        String bool = settings[1];
        box.setValue(dif);
        if (dif.equals("Custom")) {
            customDimension.setDisable(false);
            setSeed.setDisable(false);
            if (bool.equals("True")) {
                setSeed.setSelected(true);
                seed.setDisable(false);
                String seedHash = settings[2];
                seedHash += settings[3];
                seed.setText(seedHash);
            } else {
                String hash = settings[3];
                if (!(hash.equals("Null"))) {
                    verify(hash);
                    hash = hash.substring(1);
                    customDimension.setSelected(true);
                    width.setDisable(false);
                    height.setDisable(false);
                    numMines.setDisable(false);
                    width.setText(hash.substring(2, 4));
                    height.setText(hash.substring(4, 6));
                    numMines.setText(hash.substring(6));
                }
            }
        }
        if (settings[4].equalsIgnoreCase("True")) {
            logs.setSelected(true);
        }
    }

    private void updateSettings() {
        if (box.getValue().equals("Custom")) {
            setSeed.setDisable(false);
            customDimension.setDisable(false);
            if (setSeed.isSelected()) {
                customDimension.setSelected(false);
                seed.setDisable(false);
                seed.setText(currentSeed);
                width.setDisable(true);
                height.setDisable(true);
                numMines.setDisable(true);
            } else {
                seed.setDisable(true);
                width.setDisable(true);
                height.setDisable(true);
                numMines.setDisable(true);
            }
        } else {
            seed.setDisable(true);
            setSeed.setDisable(true);
            setSeed.setSelected(false);
            customDimension.setDisable(true);
            customDimension.setSelected(false);
            width.setDisable(true);
            height.setDisable(true);
            numMines.setDisable(true);
        }
    }

    private void altUpdateSettings() {
        if (customDimension.isSelected()) {
            setSeed.setSelected(false);
            seed.setDisable(true);
            width.setDisable(false);
            height.setDisable(false);
            numMines.setDisable(false);
        } else {
            updateSettings();
        }
    }

    private void verify(String seed) {
        String hash = "";
        for (int i = 0; i < seed.length(); i++) {
            if (seed.charAt(i) == 't') {
                if (i == 0) {
                    throw new InvalidSeedException("No RNG seed provided to populate the board");
                }
                hash = seed.substring(i);
                break;
            }
        }
        if (hash.length() != 9) {
            throw new InvalidSeedException("Invalid seed ending hash " + hash + ".");
        }
        String start = hash.substring(0, 2);
        String height = hash.substring(2, 4);
        String width = hash.substring(4, 6);
        String mines = hash.substring(6, 9);
        if (!(start.equalsIgnoreCase("t4"))) {
            throw new InvalidSeedException("Invalid seed ending hash " + hash + ".");
        }
        if (!(Integer.parseInt(height) >= 5) || !(Integer.parseInt(height) <= 24)) {
            throw new InvalidSeedException("Invalid seed ending hash " + hash + ".");
        }
        if (!(Integer.parseInt(width) >= 5) || !(Integer.parseInt(width) <= 30)) {
            throw new InvalidSeedException("Invalid seed ending hash " + hash + ".");
        }
        if (!(Integer.parseInt(mines) >= 0) ||
                !(Integer.parseInt(mines) <= (Integer.parseInt(height) * Integer.parseInt(width) - 1))) {
            throw new InvalidSeedException("Invalid seed ending hash " + hash + ".");
        }
    }
}