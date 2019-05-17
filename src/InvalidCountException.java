public class InvalidCountException extends RuntimeException {
    public InvalidCountException(String message) {
        super(message);
    }

    public InvalidCountException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
