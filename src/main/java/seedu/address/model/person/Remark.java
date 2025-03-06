package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid.
 */
public class Remark {
    public final String value;

    /**
     * Constructs a {@code Remark} with the specified remark value.
     *
     * @param remark A valid remark string.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    /**
     * Returns the string representation of this Remark.
     *
     * @return The remark value as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if this Remark is equal to another object.
     *
     * @param other The object to compare with.
     * @return {@code true} if the other object is a {@code Remark} with the same value, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    /**
     * Returns the hash code for this Remark.
     *
     * @return The hash code of the remark value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
