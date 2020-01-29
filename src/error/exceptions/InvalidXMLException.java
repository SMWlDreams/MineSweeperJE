package error.exceptions;

public class InvalidXMLException extends RuntimeException {
    public InvalidXMLException(String message) {
        super(message);
    }

    public InvalidXMLException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
