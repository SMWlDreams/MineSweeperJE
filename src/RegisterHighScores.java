import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Handles registering high scores
 */
public class RegisterHighScores {
    @FXML
    TextField field;
    @FXML
    Text score;
    @FXML
    Text rank;

    private String dif;
    private Controller controller;
    private String realRank;
    private String playerScore;

    /**
     * Registers the score with the entered name
     * @param actionEvent   Event from javafx
     */
    public void register(ActionEvent actionEvent) {
        try {
            String name = field.getText();
            if (name == null) {
                throw new IOException();
            }
            Scores<String> score = new Scores<>(name, playerScore, dif);
            HighScores scores = new HighScores();
            scores.writeScores(score, realRank);
            close(actionEvent);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Name Error");
            alert.setHeaderText("Invalid Name");
            alert.setContentText("Please enter a value for your name.");
            alert.showAndWait();
        }
    }

    /**
     * Sets the info on the GUI so the user knows their stats
     * @param score         The players score
     * @param rank          The players rank
     * @param difficulty    The difficulty of that board
     */
    public void setInfo(String score, String rank, String difficulty) {
        this.score.setText("Your score: " + score);
        this.rank.setText("Your rank for " + difficulty + " is: " + rank);
        playerScore = score;
        realRank = rank;
        dif = difficulty;
    }

    public void close(ActionEvent actionEvent) {
        controller.closeRHSWindow();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
