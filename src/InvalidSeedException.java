public class InvalidSeedException extends RuntimeException {
    public InvalidSeedException(String message) {
        super(message);
    }

    public InvalidSeedException(String message, Throwable cause) {
        super(message, cause);
    }
}
