import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Information for displaying and entering new user specified hotkeys
 */
public class Hotkeys implements Controllers {

    /**
     * Class properties to allow loading of the window
     */
    public static final String[] PROPERTIES = {"Hotkeys.fxml", "Hotkeys"};

    /**
     * The default hotkeys in the event of a corrupt file or a user requested hotkey reset
     */
    public static final String[] DEFAULT_HOTKEYS = {"R", "P", "N", "H", "S", "A"};

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

    private Controller controller;
    private boolean edit = false;

    /**
     * Closes the window and re-enables gameplay
     * @param actionEvent   JavaFX event
     */
    public void close(ActionEvent actionEvent) {
        controller.closeNewWindow();
    }

    /**
     * Reads the hotkeys currently loaded in the LoadedSettings class
     */
    public void generateHotKeys() {
        String[] hotkeys = LoadedSettings.getHotkeys();
        restart.setText(hotkeys[0]);
        pause.setText(hotkeys[1]);
        newGame.setText(hotkeys[2]);
        help.setText(hotkeys[3]);
        highScore.setText(hotkeys[4]);
        about.setText(hotkeys[5]);
    }

    /**
     * Changes the gui layout to ENTER_HOTKEY_MODE from DISPLAY_HOTKEY_MODE
     * @param actionEvent   JavaFX Event
     */
    public void changeHotKeys(ActionEvent actionEvent) {
        setGuiLayout();
    }

    /**
     * Changes the gui layout from ENTER_HOTKEY_MODE to DISPLAY_HOTKEY_MODE without saving the
     * user-entered hotkeys
     */
    public void cancel() {
        setGuiLayout();
    }

    /**
     * Resets the hotkeys after checking with tthe user
     * @param actionEvent   Event sent from javafx
     */
    public void reset(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reset Hotkeys");
        alert.setHeaderText("Reset Hotkeys?");
        alert.setContentText("Are you sure you want to set the hotkeys\nto their default values?");
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.OK)) {
            reset();
            generateHotKeys();
        }
    }

    /**
     * Method that handles the launch settings for each new window
     *
     * @param string Any argument string that must be passed to the implemented method
     */
    @Override
    public void launch(String string) {
        generateHotKeys();
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Saves the new hotkey strings and updates their contents in the LoadedSettings
     */
    public void saveNewHotkeys() {
        try {
            String[] hotkeys = new String[DEFAULT_HOTKEYS.length];
            String[] oldKeys = LoadedSettings.getHotkeys();
            if (!(setRestart.getText().length() == 0)) {
                hotkeys[0] = setRestart.getText().substring(0, 1);
            } else {
                hotkeys[0] = oldKeys[0];
            }
            if (!(setPause.getText().length() == 0)) {
                hotkeys[1] = setPause.getText().substring(0, 1);
            } else {
                hotkeys[1] = oldKeys[1];
            }
            if (!(setNewGame.getText().length() == 0)) {
                hotkeys[2] = setNewGame.getText().substring(0, 1);
            } else {
                hotkeys[2] = oldKeys[2];
            }
            if (!(setHelp.getText().length() == 0)) {
                hotkeys[3] = setHelp.getText().substring(0, 1);
            } else {
                hotkeys[3] = oldKeys[3];
            }
            if (!(setHighScores.getText().length() == 0)) {
                hotkeys[4] = setHighScores.getText().substring(0, 1);
            } else {
                hotkeys[4] = oldKeys[4];
            }
            if (!(setAbout.getText().length() == 0)) {
                hotkeys[5] = setAbout.getText().substring(0, 1);
            } else {
                hotkeys[5] = oldKeys[5];
            }
            verify(hotkeys);
            setGuiLayout();
        } catch (StringIndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Hotkey Input");
            alert.setHeaderText("Invalid Hotkey Input");
            alert.setContentText("Duplicate or invalid hotkey detected.\n" +
                    "Please ensure you have no duplicate hotkeys and\n" +
                    "that no hotkeys have a space assigned to them.");
            alert.showAndWait();
        }
    }

    /**
     * Resets to the default hotkey settings keeping the user specified launch settings
     */
    private void reset() {
        GenerateSettings.updateSettings(LoadedSettings.getLaunchSettings(), DEFAULT_HOTKEYS,
                Settings.DEFAULT_SETTINGS);
    }

    /**
     * Updates the GUI layout based on whether we are in ENTER_HOTKEY_MODE or DISPLAY_HOTKEY_MODE
     */
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
            setAbout.clear();
            setHelp.setVisible(false);
            setHelp.clear();
            setHighScores.setVisible(false);
            setHighScores.clear();
            setNewGame.setVisible(false);
            setNewGame.clear();
            setPause.setVisible(false);
            setPause.clear();
            setRestart.setVisible(false);
            setRestart.clear();
            hotkeyChange.setText("Click here to change hotkey keybinds");
            hotkeyChange.setOnAction(this::changeHotKeys);
            infoText.setText("The following hotkeys can be used");
            okSave.setText("OK");
            okSave.setOnAction(this::close);
            resetClear.setText("Reset");
            resetClear.setOnAction(this::reset);
            edit = false;
            generateHotKeys();
        }
    }

    /**
     * Verifies that the hotkeys entered by the user fit the requirements set forth by the
     * application in the verify(String[], int) method
     * @param hotkeys   The String[] containing the newly entered user hotkeys
     */
    private void verify(String[] hotkeys) {
        if (verify(hotkeys, 0)) {
            GenerateSettings.updateSettings(LoadedSettings.getLaunchSettings(), hotkeys, Settings.DEFAULT_SETTINGS);
        } else {
            throw new StringIndexOutOfBoundsException("Invalid hotkey");
        }
    }

    /**
     * Determines the validity of the user-specified hotkeys before saving them
     * In order for the hotkeys to be valid it must follow the following rules:
     *      There must be NO duplicate hotkeys present
     *      All hotkeys must be assigned a valid value (note: any fields left blank will simply
     *      be updated with whatever was present before)
     *      No hotkey can have the character ' ' assigned to it
     * @param hotkeys       String[] containing the new hotkeys
     * @param searchIndex   0 to point to the start of the array
     * @return              True if and only if ALL the new hotkeys are valid
     */
    private boolean verify(String[] hotkeys, int searchIndex) {
        String s = hotkeys[searchIndex];
        if (s.equalsIgnoreCase(" ")) {
            return false;
        }
        switch (searchIndex) {
            case 0:
                if (s.equalsIgnoreCase(hotkeys[1])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[2])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[3])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[4])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[5])) {
                    return false;
                }
                break;
            case 1:
                if (s.equalsIgnoreCase(hotkeys[2])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[3])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[4])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[5])) {
                    return false;
                }
                break;
            case 2:
                if (s.equalsIgnoreCase(hotkeys[3])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[4])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[5])) {
                    return false;
                }
                break;
            case 3:
                if (s.equalsIgnoreCase(hotkeys[4])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[5])) {
                    return false;
                }
                break;
            case 4:
                if (s.equalsIgnoreCase(hotkeys[5])) {
                    return false;
                }
                break;
            case 5:
                return true;
            default:
                return false;
        }
        return (verify(hotkeys, ++searchIndex));
    }
}