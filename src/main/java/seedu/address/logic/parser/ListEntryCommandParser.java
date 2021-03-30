package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entry.ListEntryFormatPredicate;

/**
 * Parses input arguments and creates a new ListEntryCommand object
 */
public class ListEntryCommandParser {

    /**
     * Parses the give {@code String} of arguments in the context of the ListEntryCommand
     * and returns a ListEntryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListEntryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        ListEntryCommand listEntryCommand = new ListEntryCommand(
                new ListEntryFormatPredicate(trimmedArgs));
        if (trimmedArgs.equals("day") || trimmedArgs.equals("week") || trimmedArgs.isEmpty()) {
            return listEntryCommand;
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListEntryCommand.MESSAGE_USAGE));
        }
    }
}
