package error;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ErrorHandler {
    private ErrorHandler() {}

    public static void newUnexpectedExceptionAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(e.getClass().getName());
        alert.setHeaderText("An Unexpected Exception Occurred!");
        alert.setContentText("Unexpected exception " + e.getClass().getName() + " threw the " +
                "following:\n" + e.getMessage());
        alert.showAndWait();
    }

    public static void newExpectedExceptionAlert(Exception e, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("An exception was caught!");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public static void forceExit() {
        Platform.exit();
    }
}
