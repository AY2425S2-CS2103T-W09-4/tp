package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        // Invalid format
        assertThrows(IllegalArgumentException.class, () -> new Date("2025-12-25"));
        assertThrows(IllegalArgumentException.class, () -> new Date("25/12/2025"));

        // Invalid day/month
        assertThrows(IllegalArgumentException.class, () -> new Date("32122025")); // Invalid day
        assertThrows(IllegalArgumentException.class, () -> new Date("25132025")); // Invalid month
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("25/12/2025")); // wrong format
        assertFalse(Date.isValidDate("2025-12-25")); // wrong format
        assertFalse(Date.isValidDate("32122025")); // invalid day
        assertFalse(Date.isValidDate("25132025")); // invalid month

        // invalid dates within 1-31 (such as invalid leap years)
        assertFalse(Date.isValidDate("29022029")); // wrong leap year
        assertFalse(Date.isValidDate("31062025")); // day does not exist
        assertFalse(Date.isValidDate("31042027")); // day does not exist

        // valid dates (future dates in DDMMYYYY format)
        assertTrue(Date.isValidDate("25122025")); // Christmas 2025
        assertTrue(Date.isValidDate("01012030")); // New Year's Day 2030
        assertTrue(Date.isValidDate("31012025")); // month with 31 days
        assertTrue(Date.isValidDate("29022028")); // leap year

        // Edge cases - tomorrow should be valid
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String tomorrowStr = tomorrow.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        assertTrue(Date.isValidDate(tomorrowStr));
    }

    @Test
    public void equals() {
        Date date = new Date("25122025");

        // same values -> returns true
        assertTrue(date.equals(new Date("25122025")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new Date("01012030")));
    }

    @Test
    public void toString_validDate_returnsFormattedDate() {
        Date date = new Date("25122025");
        assertEquals("25 December 2025", date.toString());
    }
}
