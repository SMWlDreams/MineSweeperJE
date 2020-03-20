package error;

import java.io.*;
import java.time.LocalDateTime;

public class Logger {
    private static Logger LOGGER = new Logger();
    private FileWriter writer;

    private Logger() {
        initWriter();
    }

    private void initWriter() {
        try {
            writer = new FileWriter(System.getProperty("user.home") + "\\AppData\\Roaming" +
                    "\\Minesweeper\\ErrorLog.txt", true);
        } catch (IOException e) {
            if (new File(System.getProperty("user.home") + "\\AppData\\Roaming" +
                    "\\Minesweeper\\").mkdirs()) {
                initWriter();
            } else {
                ErrorHandler.newExpectedExceptionAlert(new FileNotFoundException("Cannot write " +
                        "to error logger!\nPlease make sure you have write permissions!"), "File " +
                        "Write Error!", false);
            }
        } catch (Exception e) {
            ErrorHandler.newUnexpectedExceptionAlert(e, true);
        }
    }

    public static Logger getInstance() {
        return LOGGER;
    }

    public void log(Exception e) {
        try {
            LocalDateTime time = LocalDateTime.now();
            writer.append(time.toString().substring(0, 19))
                    .append(": ")
                    .append(e.getMessage())
                    .append("\r\n");
        } catch (Exception e1) {
            ErrorHandler.newUnexpectedExceptionAlert(e1, false);
        }
    }

    private void closeWriter() {
        try {
            writer.close();
        } catch (Exception e) {
            ErrorHandler.newUnexpectedExceptionAlert(e, false);
        }
    }

    public static void close() {
        LOGGER.closeWriter();
    }
}
