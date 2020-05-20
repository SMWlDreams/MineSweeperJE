package error.exceptions;

public class IllegalHotkeyValueException extends RuntimeException {
    public IllegalHotkeyValueException(String message) {
        super(message);
    }

    public IllegalHotkeyValueException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
