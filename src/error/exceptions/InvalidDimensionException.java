package error.exceptions;

public class InvalidDimensionException extends RuntimeException {
    public InvalidDimensionException(String message) {
        super(message);
    }

    public InvalidDimensionException(String message, Throwable cause) {
        super(message, cause);
    }
}
