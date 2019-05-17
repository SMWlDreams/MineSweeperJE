import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * The controller for the new game window
 */
public class NewGameController {
    @FXML
    RadioButton easy;
    @FXML
    RadioButton intermediate;
    @FXML
    RadioButton expert;
    @FXML
    RadioButton custom;
    @FXML
    TextField width;
    @FXML
    TextField height;
    @FXML
    TextField numMines;

    private Controller controller;

    /**
     * Begins the game based on the specified values
     * @param actionEvent Event sent from clicking begin button
     */
    public void begin(ActionEvent actionEvent) throws IOException {
        try {
            if (easy.isSelected()) {
                controller.setDifficulty(1);
            } else if (intermediate.isSelected()) {
                controller.setDifficulty(2);
            } else if (expert.isSelected()) {
                controller.setDifficulty(3);
            } else {
                createCustomBoard();
            }
            controller.closeNewWindow();
            if (controller.began()) {
                controller.clear();
            }
            controller.begin();
        } catch(InvalidDimensionException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Dimension");
            alert.setHeaderText("Invalid Dimension");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        } catch(InvalidCountException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Number of Mines");
            alert.setHeaderText("Invalid Number of Mines");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void createCustomBoard() throws InvalidDimensionException, InvalidCountException {
        if (width.getText().equals("") || Integer.parseInt(width.getText()) > 30 ||
                Integer.parseInt(width.getText()) < 5) {
            throw new InvalidDimensionException("The specified width is invalid.");
        } else if (height.getText().equals("") || Integer.parseInt(height.getText()) > 24 ||
                Integer.parseInt(height.getText()) < 5) {
            throw new InvalidDimensionException("The specified height is invalid.");
        }
        int tempWidth = Integer.parseInt(width.getText());
        int tempHeight = Integer.parseInt(height.getText());
        if (numMines.getText().equals("") || Integer.parseInt(numMines.getText()) == 0) {
            double mines = ((tempWidth * tempHeight) / 4.5);
            controller.customDimensions(tempHeight, tempWidth, (int)mines);
        } else {
            int mines = Integer.parseInt(numMines.getText());
            if (mines > (tempHeight * tempWidth) - 1) {
                throw new InvalidCountException("The number of mines specified is too large.");
            } else if (mines == 0) {
                throw new InvalidCountException("The number of mines cannot be 0.");
            }
            controller.customDimensions(tempHeight, tempWidth, mines);
        }
    }
}