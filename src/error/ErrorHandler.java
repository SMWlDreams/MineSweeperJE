package error;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ErrorHandler {
    private ErrorHandler() {}

    public static void newUnexpectedExceptionAlert(Exception e, boolean log) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(e.getClass().getName());
        alert.setHeaderText("An Unexpected Exception Occurred!");
        alert.setContentText("Unexpected exception " + e.getClass().getName() + "\nthrew the " +
                "following:\n" + e.getMessage());
        alert.showAndWait();
        if (log) {
            Logger.getInstance().log(e);
        }
    }

    public static void newExpectedExceptionAlert(Exception e, String title, boolean log) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("An exception was caught!");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        if (log) {
            Logger.getInstance().log(e);
        }
    }

    public static void forceExit() {
        Platform.exit();
    }
}
