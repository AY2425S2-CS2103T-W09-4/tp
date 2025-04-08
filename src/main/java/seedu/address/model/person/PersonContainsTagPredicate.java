package seedu.address.model.person;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag} matches the tag given.
 */
public class PersonContainsTagPredicate implements Predicate<Person> {
    private final List<Tag> tags;

    public PersonContainsTagPredicate(List<String> tags) {
        this.tags = tags.stream().map(Tag::new).collect(Collectors.toList());
    }

    @Override
    public boolean test(Person person) {
        return tags.stream().anyMatch(tag -> person.getTags().contains(tag));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsTagPredicate)) {
            return false;
        }

        PersonContainsTagPredicate otherPersonContainsTagPredicate = (PersonContainsTagPredicate) other;
        return tags.equals(otherPersonContainsTagPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tags", tags).toString();
    }
}
