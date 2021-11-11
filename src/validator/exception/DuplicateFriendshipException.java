package validator.exception;

/**
 * Exception to be thrown if the friendship already exists
 */
public class DuplicateFriendshipException extends RuntimeException {
    /**
     * Empty constructor
     */
    public DuplicateFriendshipException() {
    }

    /**
     * Constructor with message
     * @param message error message
     */
    public DuplicateFriendshipException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message error message
     * @param cause error cause
     */
    public DuplicateFriendshipException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause
     * @param cause error cause
     */
    public DuplicateFriendshipException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with message cause and additional options
     * @param message error message
     * @param cause error cause
     * @param enableSuppression suppression modifier
     * @param writableStackTrace stack trace modifier
     */
    public DuplicateFriendshipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
