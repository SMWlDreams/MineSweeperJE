import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ResetHighScores {

    /**
     * Resets the high scores files to their defaults
     */
    public static void ResetScores() {
        try {
            DataOutputStream outputStream = new
                    DataOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\Records\\Easy.mhs"));
            outputStream.writeUTF(new Scores<>("Player 1", "500", "Easy").toString());
            outputStream.writeUTF(new Scores<>("Player 2", "400", "Easy").toString());
            outputStream.writeUTF(new Scores<>("Player 3", "300", "Easy").toString());
            outputStream.writeUTF(new Scores<>("Player 4", "200", "Easy").toString());
            outputStream.writeUTF(new Scores<>("Player 5", "100", "Easy").toString());
            outputStream.close();
            outputStream = new DataOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\Records\\Intermediate.mhs"));
            outputStream.writeUTF(new Scores<>("Player 1", "500", "Intermediate").toString());
            outputStream.writeUTF(new Scores<>("Player 2", "400", "Intermediate").toString());
            outputStream.writeUTF(new Scores<>("Player 3", "300", "Intermediate").toString());
            outputStream.writeUTF(new Scores<>("Player 4", "200", "Intermediate").toString());
            outputStream.writeUTF(new Scores<>("Player 5", "100", "Intermediate").toString());
            outputStream.close();
            outputStream = new DataOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\Records\\Expert.mhs"));
            outputStream.writeUTF(new Scores<>("Player 1", "500", "Expert").toString());
            outputStream.writeUTF(new Scores<>("Player 2", "400", "Expert").toString());
            outputStream.writeUTF(new Scores<>("Player 3", "300", "Expert").toString());
            outputStream.writeUTF(new Scores<>("Player 4", "200", "Expert").toString());
            outputStream.writeUTF(new Scores<>("Player 5", "100", "Expert").toString());
            outputStream.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Critical Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Error! The high score file cannot be created\n" +
                    "due to an I/O error!");
            alert.showAndWait();
        }
    }

    /**
     * Resets the high scores for the specified difficulty to their default
     * @param difficulty
     */
    public static void ResetScores(String difficulty) {
        try {
            DataOutputStream outputStream;
            if (difficulty.equalsIgnoreCase("Easy")) {
                outputStream = new DataOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\Records\\Easy.mhs"));
                outputStream.writeUTF(new Scores<>("Player 1", "500", "Easy").toString());
                outputStream.writeUTF(new Scores<>("Player 2", "400", "Easy").toString());
                outputStream.writeUTF(new Scores<>("Player 3", "300", "Easy").toString());
                outputStream.writeUTF(new Scores<>("Player 4", "200", "Easy").toString());
                outputStream.writeUTF(new Scores<>("Player 5", "100", "Easy").toString());
                outputStream.close();
            } else if (difficulty.equalsIgnoreCase("Intermediate")) {
                outputStream = new DataOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\Records\\Intermediate.mhs"));
                outputStream.writeUTF(new Scores<>("Player 1", "500", "Intermediate").toString());
                outputStream.writeUTF(new Scores<>("Player 2", "400", "Intermediate").toString());
                outputStream.writeUTF(new Scores<>("Player 3", "300", "Intermediate").toString());
                outputStream.writeUTF(new Scores<>("Player 4", "200", "Intermediate").toString());
                outputStream.writeUTF(new Scores<>("Player 5", "100", "Intermediate").toString());
                outputStream.close();
            } else if (difficulty.equalsIgnoreCase("Expert")) {
                outputStream = new DataOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\Records\\Expert.mhs"));
                outputStream.writeUTF(new Scores<>("Player 1", "500", "Expert").toString());
                outputStream.writeUTF(new Scores<>("Player 2", "400", "Expert").toString());
                outputStream.writeUTF(new Scores<>("Player 3", "300", "Expert").toString());
                outputStream.writeUTF(new Scores<>("Player 4", "200", "Expert").toString());
                outputStream.writeUTF(new Scores<>("Player 5", "100", "Expert").toString());
                outputStream.close();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Critical Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Error! The high score file cannot be created\n" +
                    "due to an I/O error!");
            alert.showAndWait();
        }
    }

    /**
     * Verifies the integrity of the High Score files and folder
     */
    public static void verify() {
        try {
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(System.getProperty(
                "user.dir") + "\\Records\\Test.mhs"));
            try {
                DataInputStream inputStream =
                        new DataInputStream(new FileInputStream(System.getProperty("user.dir") +
                                "\\Records\\Easy.mhs"));
            } catch (IOException e) {
                ResetScores("Easy");
            }
            try {
                DataInputStream inputStream =
                        new DataInputStream(new FileInputStream(System.getProperty("user.dir") +
                                "\\Records\\Intermediate.mhs"));
            } catch (IOException e) {
                ResetScores("Intermediate");
            }
            try {
                DataInputStream inputStream =
                        new DataInputStream(new FileInputStream(System.getProperty("user.dir") +
                                "\\Records\\Expert.mhs"));
            } catch (IOException e) {
                ResetScores("Expert");
            }
        } catch (IOException e) {
            File dir = new File(System.getProperty("user.dir") + "\\Records");
            if (dir.mkdirs()) {
                ResetScores();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Critical Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("Error! The high score file cannot be created\n" +
                        "due to an I/O error!");
                alert.showAndWait();
            }
        }
    }
}
