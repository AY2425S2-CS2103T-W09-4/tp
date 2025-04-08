package seedu.address.logic.parser.wedding;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.wedding.FilterByTagCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterByTagCommand object
 */
public class FilterByTagCommandParser implements Parser<FilterByTagCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the FilterByTagCommand
     * and returns a FilterByTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterByTagCommand parse(String args) throws ParseException {
        String trimArgs = args.trim();


        if (trimArgs.isEmpty()) {
            return new FilterByTagCommand();
        } else {
            List<String> tagKeywords = Arrays.asList(trimArgs.split("\\s+"));
            // Check validity of tag
            for (String tag : tagKeywords) {
                if (!Tag.isValidTagName(tag)) {
                    throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
                }
            }
            return new FilterByTagCommand(new PersonContainsTagPredicate(tagKeywords));
        }
    }
}
