package error.exceptions;

public class InvalidLogException extends RuntimeException {
    public InvalidLogException(String message) {
        super(message);
    }

    public InvalidLogException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
