package validator.exception;

/**
 * Exception to be thrown if one of the attribute fields is empty
 */
public class EmptyFieldException extends ValidationException {
    /**
     * Empty constructor
     */
    public EmptyFieldException() {
    }

    /**
     * Constructor with message
     * @param message error message
     */
    public EmptyFieldException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message error message
     * @param cause error cause
     */
    public EmptyFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause
     * @param cause error cause
     */
    public EmptyFieldException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with message cause and additional options
     * @param message error message
     * @param cause error cause
     * @param enableSuppression suppression modifier
     * @param writableStackTrace stack trace modifier
     */
    public EmptyFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
