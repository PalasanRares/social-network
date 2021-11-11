package constants;

import java.time.format.DateTimeFormatter;

/**
 * Holds date formats
 */
public class DateFormatter {
    /**
     * Formats a date to the dd-MM-yyyy standard
     */
    public static final DateTimeFormatter STANDARD_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
}
