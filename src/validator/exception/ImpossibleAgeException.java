package validator.exception;

/**
 * Exception for impossible age
 */
public class ImpossibleAgeException extends ValidationException {
    /**
     * Empty constructor
     */
    public ImpossibleAgeException() {
    }

    /**
     * Constructor with message
     * @param message error message
     */
    public ImpossibleAgeException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message error message
     * @param cause error cause
     */
    public ImpossibleAgeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause
     * @param cause error cause
     */
    public ImpossibleAgeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with message cause and additional options
     * @param message error message
     * @param cause error cause
     * @param enableSuppression suppression modifier
     * @param writableStackTrace stack trace modifier
     */
    public ImpossibleAgeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
