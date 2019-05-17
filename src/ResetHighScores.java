import javafx.scene.control.Alert;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResetHighScores {
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
            alert.setContentText("Error! The high score file cannot be found!");
            alert.showAndWait();
        }
    }
}
