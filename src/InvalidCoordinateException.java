public class InvalidCoordinateException extends RuntimeException {
    public InvalidCoordinateException(String message) {
        super(message);
    }

    public InvalidCoordinateException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
