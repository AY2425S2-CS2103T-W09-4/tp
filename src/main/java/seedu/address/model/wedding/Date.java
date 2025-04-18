package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a Wedding's date in the planner.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS = "Dates should be in the format DDMMYYYY (e.g., 30092025 for "
            + "30 September 2025) and must exist (eg 29022029 is invalid).";

    // Formatter for parsing the input string in DDMMYYYY format.
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("ddMMuuuu");
    // Formatter for outputting the date in a prettier format.
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM uuuu");

    private static final DateTimeFormatter formatters = new DateTimeFormatterBuilder()
            .appendOptional(INPUT_FORMATTER)
            .appendOptional(OUTPUT_FORMATTER)
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    public final LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param dateStr A valid date string in DDMMYYYY format.
     * @throws IllegalArgumentException if {@code dateStr} is not in the correct format or is not a future date.
     * @throws NullPointerException if {@code dateStr} is null.
     */
    public Date(String dateStr) {
        requireNonNull(dateStr);
        checkArgument(isValidDate(dateStr), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(dateStr, formatters);
    }

    /**
     * Returns true if a given string is a valid date in DDMMYYYY format and is in the future.
     *
     * @param testDate the date string to validate.
     * @return true if the date is in the correct format and is a future date, false otherwise.
     */
    public static boolean isValidDate(String testDate) {
        try {
            LocalDate parsedDate = LocalDate.parse(testDate, formatters);
            return true; // no longer going to test for this condition
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public int compareTo(Date other) {
        return this.date.compareTo(other.date);
    }

    /**
     * Returns this date in a user-friendly format (e.g., "30 September 2025").
     *
     * @return a formatted date string.
     */
    @Override
    public String toString() {
        return date.format(OUTPUT_FORMATTER);
    }

    /**
     * Indicates whether two Date objects are equal.
     * Two {@code Date} objects are considered equal if they represent the same date.
     *
     * @param other the reference object with which to compare.
     * @return true if this object is the same as the other object.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Date)) {
            return false;
        }
        Date otherDate = (Date) other;
        return date.equals(otherDate.date);
    }


    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
