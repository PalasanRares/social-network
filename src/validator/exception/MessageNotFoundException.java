package validator.exception;

public class MessageNotFoundException extends RuntimeException{
    /**
     * Empty constructor
     */
    public MessageNotFoundException() {
    }

    /**
     * Constructor with message
     * @param message error message
     */
    public MessageNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message error message
     * @param cause error cause
     */
    public MessageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause
     * @param cause error cause
     */
    public MessageNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with message cause and additional options
     * @param message error message
     * @param cause error cause
     * @param enableSuppression suppression modifier
     * @param writableStackTrace stack trace modifier
     */
    public MessageNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
