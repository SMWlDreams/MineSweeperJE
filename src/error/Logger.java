package error;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Logger {
    private static Logger LOGGER = new Logger();
    private PrintWriter writer;

    private Logger() {
        initWriter();
    }

    private void initWriter() {
        try {
            writer = new PrintWriter(System.getProperty("user.home") + "\\AppData\\Roaming" +
                    "\\Minesweeper\\ErrorLog.txt");

        } catch (IOException e) {
            if (new File(System.getProperty("user.home") + "\\AppData\\Roaming" +
                    "\\Minesweeper\\").mkdirs()) {
                initWriter();
            } else {
                ErrorHandler.newExpectedExceptionAlert(new FileNotFoundException("Cannot write " +
                        "to error logger!\nPlease make sure you have write permissions!"), "File " +
                        "Write Error!", false);
            }
        }
    }

    public static Logger getInstance() {
        return LOGGER;
    }

    public void log(Exception e) {
        try {
            writer.append(
                    new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss")
                            .parse(new GregorianCalendar()
                                    .getTime()
                                    .toString())
                            + ": " + e.getMessage() + "\r\n");
        } catch (ParseException e1) {
            ErrorHandler.newUnexpectedExceptionAlert(e1, false);
        }
    }

    private void closeWriter() {
        writer.close();
    }

    public static void close() {
        LOGGER.closeWriter();
    }
}
