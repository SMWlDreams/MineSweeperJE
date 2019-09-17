import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads and writes scores to .mhs files
 */
public class HighScores implements Controllers {

    /**
     * Class properties to allow loading of the window
     */
    public static final String[] PROPERTIES = {"HighScores.fxml", "High Scores"};

    @FXML
    private Text difficulty;
    @FXML
    private Text score1;
    @FXML
    private Text score2;
    @FXML
    private Text score3;
    @FXML
    private Text score4;
    @FXML
    private Text score5;
    @FXML
    private Text name1;
    @FXML
    private Text name2;
    @FXML
    private Text name3;
    @FXML
    private Text name4;
    @FXML
    private Text name5;
    @FXML
    private ComboBox<String> box;

    private Controller controller;
    private final Path path;

    /**
     * Initializes the path to the high score files
     */
    public HighScores() {
        path = Paths.get(System.getProperty("user.dir") + "\\Records\\");
    }

    /**
     * Changes the high scores to the selected difficulty
     * @param actionEvent   Event from javafx
     */
    public void changeDifficulty(ActionEvent actionEvent) {
        getHighScores(box.getSelectionModel().getSelectedItem());
        box.setValue(box.getSelectionModel().getSelectedItem());
    }

    /**
     * Writes a new high score to the file for that difficulty
     * @param scores        The score to be written
     * @param rank          Their rank in the leaderboard
     * @return              True if added
     * @throws IOException  If the file is missing
     */
    public boolean writeScores(Scores<String> scores, String rank) throws IOException {
        int insertPoint = Integer.parseInt(rank);
        List<String> list = scores.getValues();
        String difficulty = list.get(2);
        List<Scores<String>> score = readScores(difficulty);
        score.add(insertPoint - 1, scores);
        DataOutputStream outputStream = new
                DataOutputStream(new FileOutputStream(path.toString() + "\\" + difficulty + ".mhs"));
        for (int i = 0; i < 5; i++) {
            outputStream.writeUTF(score.get(i).toString());
        }
        outputStream.close();
        return true;
    }

    /**
     * Reads the scores from the high score file for the specified difficulty
     * @param difficulty    Difficulty to get info from
     * @return              The top 5 scores for that difficulty
     */
    public List<Scores<String>> readScores(String difficulty) {
        try {
            DataInputStream inputStream = new
                    DataInputStream(new FileInputStream(path.toString() + "\\" + difficulty + ".mhs"));
            List<Scores<String>> score = new ArrayList<>();
            int i = 0;
            while (i < 5) {
                String in = inputStream.readUTF();
                int i1;
                int i2;
                int i3;
                for (i1 = 1; i1 < in.length(); i1++) {
                    if (in.charAt(i1) == '\n') {
                        break;
                    }
                }
                for (i2 = i1 + 1; i2 < in.length(); i2++) {
                    if (in.charAt(i2) == '\n') {
                        break;
                    }
                }
                for (i3 = i2 + 1; i3 < in.length(); i3++) {
                    if (in.charAt(i3) == '\n') {
                        break;
                    }
                }
                score.add(new Scores<>(in.substring(1, i1), in.substring(i1 + 1, i2),
                        in.substring(i2 + 1, i3)));
                i++;
            }
            inputStream.close();
            return score;
        } catch (IOException e) {
            ResetHighScores.verify();
            return readScores(difficulty);
        }
    }

    public Path getPath() {
        return path;
    }

    /**
     * Sets the text in the GUI to the retrieved high scores
     * @param difficulty    The difficulty to pull data from
     */
    public void getHighScores(String difficulty) {
            List<Scores<String>> scores = readScores(difficulty);
            this.difficulty.setText(difficulty);
            name1.setText(scores.get(0).getValues().get(0));
            score1.setText(scores.get(0).getValues().get(1));
            name2.setText(scores.get(1).getValues().get(0));
            score2.setText(scores.get(1).getValues().get(1));
            name3.setText(scores.get(2).getValues().get(0));
            score3.setText(scores.get(2).getValues().get(1));
            name4.setText(scores.get(3).getValues().get(0));
            score4.setText(scores.get(3).getValues().get(1));
            name5.setText(scores.get(4).getValues().get(0));
            score5.setText(scores.get(4).getValues().get(1));
            setChoiceBox();
    }

    public void close(ActionEvent actionEvent) {
        controller.closeNewWindow();
    }

    /**
     * Method that handles the launch settings for each new window
     * @param string Any argument string that must be passed to the implemented method
     */
    @Override
    public void launch(String string) {
        getHighScores(string);
    }

    /**
     * Sets the controller
     * @param controller    The copy of the main GUI controller
     */
    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Restores the score to their original value
     * @param actionEvent   Event from javafx
     */
    public void clearScores(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Scores");
        alert.setHeaderText("Clear Scores?");
        alert.setContentText("Are you sure you wish to reset the scores for all\n" +
                "difficulties? All scores will be erased!");
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.OK)) {
            ResetHighScores.ResetScores();
            controller.closeNewWindow();
        }
    }

    private void setChoiceBox() {
        List<String> choice = new ArrayList<>();
        choice.add("Easy");
        choice.add("Intermediate");
        choice.add("Expert");
        box.setValue(difficulty.getText());
        box.setItems(FXCollections.observableArrayList(choice));
        box.setOnAction(this::changeDifficulty);
    }
}