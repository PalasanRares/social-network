package validator.exception;

/**
 * Exception to be thrown if the user is not found inside the repo
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Empty constructor
     */
    public UserNotFoundException() {
    }

    /**
     * Constructor with message
     * @param message error message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message error message
     * @param cause error cause
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause
     * @param cause error cause
     */
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with message cause and additional options
     * @param message error message
     * @param cause error cause
     * @param enableSuppression suppression modifier
     * @param writableStackTrace stack trace modifier
     */
    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
