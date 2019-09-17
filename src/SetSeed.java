import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Random;

/**
 * GUI for setting the RNG seed and board hash
 */
public class SetSeed implements Controllers {

    /**
     * Class properties to allow loading of the window
     */
    public static final String[] PROPERTIES = {"SetSeed.fxml", "Set Custom Seed"};

    @FXML
    private TextField entry;
    @FXML
    private TextField currentSeed;
    @FXML
    private CheckBox logs;
    @FXML
    private CheckBox showMines;

    private Controller controller;
    private String seed;
    private String hash;

    /**
     * Saves the entered seed and two check items
     * @param actionEvent   Event from javafx
     */
    public void saveSeed(ActionEvent actionEvent) {
        try {
            if (getInputSeed() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Seed");
                alert.setHeaderText("Invalid Seed");
                alert.setContentText("Seeds must be at least 10 characters long but\nless than " +
                        "28 characters long.");
                alert.showAndWait();
            }
            verifyHash();
            controller.setKeepLogs(logs.isSelected());
            controller.setShowMines(showMines.isSelected());
            controller.setHashSeed(seed, hash);
            controller.closeNewWindow();
        } catch (InvalidSeedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Seed");
            alert.setHeaderText("Invalid Seed");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }
    }

    public void init() {
        controller.setHashSeed(seed, hash);
    }

    public void cancel(ActionEvent actionEvent) {
        controller.closeNewWindow();
    }

    /**
     * Sets the seed for a new board to be created from
     * @param hash          The hash to generate a seed from
     * @throws IOException  If the file format is invalid
     */
    public void setSeed(String hash) throws IOException {
        try {
            this.hash = hash;
            this.seed = Long.toString(System.nanoTime());
            verifyHash();
            controller.setHashSeed(this.seed, this.hash);
        } catch (InvalidSeedException e) {
            throw new IOException("Invalid file format");
        }

    }

    /**
     * Sets the seed for a new board to be created from
     * @param seed          The seed to be used to generate a board
     * @param hash          The hash to be used to generate a board
     * @throws IOException  If the file format is invalid
     */
    public void setSeed(String seed, String hash) throws IOException {
        try {
            this.seed = seed;
            this.hash = hash;
            if (seed.length() < 1) {
                throw new IOException("Invalid seed");
            }
            verifyHash();
            controller.setHashSeed(this.seed, this.hash);
        } catch (InvalidSeedException e) {
            throw new IOException("Invalid file format");
        }
    }

    /**
     * Gets the seed the user enters and validates it
     * @return  The validated seed
     */
    public String getInputSeed() {
        if (entry.getText().length() == 0) {
            return null;
        } else if (entry.getText().length() > 9 && entry.getText().length() <= 28) {
            String entry = this.entry.getText();
            String code = "";
            String hash = "";
            int i;
            for (i = 0; i < entry.length(); i++) {
                if (!(entry.charAt(i) == 't')) {
                    code += entry.charAt(i);
                } else {
                    break;
                }
            }
            if (code.length() > 19 || code.length() == 0) {
                throw new InvalidSeedException("The entered seed " + code + " is invalid.");
            }
            if (Long.parseLong(code) < 0 || Long.parseLong(code) > Long.MAX_VALUE) {
                throw new InvalidSeedException("The entered seed " + code + " is invalid.");
            }
            for (i = i; i < entry.length(); i++) {
                hash += entry.charAt(i);
            }
            seed = code;
            this.hash = hash;
            return (code + hash);
        } else {
            return null;
        }
    }

    public void setCurrentSeed(String hashSeed) {
        currentSeed.setText(hashSeed);
    }

    /**
     * Generates a random seed
     * @param actionEvent   Event from javafx
     */
    public void generateSeed(ActionEvent actionEvent) {
        long seed = System.nanoTime();
        this.seed = Long.toString(seed);
        String hash = Long.toString(seed) + "t4";
        Random rand = new Random();
        int h = rand.nextInt(25);
        int w = rand.nextInt(31);
        while (h < 5) {
            h = rand.nextInt(25);
        }
        while (w < 5) {
            w = rand.nextInt(31);
        }
        if (h < 10) {
            hash += "0" + Integer.toString(h);
        } else {
            hash += Integer.toString(h);
        }
        if (w < 10) {
            hash += "0" + Integer.toString(w);
        } else {
            hash += Integer.toString(w);
        }
        int m = rand.nextInt((h * w) - 1);
        if (m < 10) {
            m = 10;
            hash += "0" + Integer.toString(m);
        } else if (m < 100) {
            hash += "0" + Integer.toString(m);
        } else {
            hash += Integer.toString(m);
        }
        this.hash = hash.substring(this.seed.length() + 1);
        entry.setText(hash);
    }

    /**
     * Generates a new seed and verifies the integrity of it
     */
    public void generateSeed() {
        long seed = System.nanoTime();
        this.seed = Long.toString(seed);
        String hash = Long.toString(seed) + "t4";
        Random rand = new Random();
        int h = rand.nextInt(25);
        int w = rand.nextInt(31);
        while (h < 5) {
            h = rand.nextInt(25);
        }
        while (w < 5) {
            w = rand.nextInt(31);
        }
        if (h < 10) {
            hash += "0" + Integer.toString(h);
        } else {
            hash += Integer.toString(h);
        }
        if (w < 10) {
            hash += "0" + Integer.toString(w);
        } else {
            hash += Integer.toString(w);
        }
        int m = rand.nextInt((h * w) - 1);
        if (m < 10) {
            m = 10;
            hash += "0" + Integer.toString(m);
        } else if (m < 100) {
            hash += "0" + Integer.toString(m);
        } else {
            hash += Integer.toString(m);
        }
        this.hash = hash.substring(this.seed.length());
    }

    /**
     * Method that handles the launch settings for each new window
     * @param string Any argument string that must be passed to the implemented method
     */
    @Override
    public void launch(String string) {
        setCurrentSeed(string);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void verifyHash() {
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