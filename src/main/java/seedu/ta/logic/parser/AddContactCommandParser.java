package seedu.ta.logic.parser;

import static seedu.ta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.ta.logic.commands.AddContactCommand;
import seedu.ta.logic.parser.exceptions.ParseException;
import seedu.ta.model.contact.Contact;
import seedu.ta.model.contact.ContactEmail;
import seedu.ta.model.contact.ContactName;
import seedu.ta.model.contact.ContactPhone;
import seedu.ta.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddContactCommand object.
 */
public class AddContactCommandParser implements Parser<AddContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddContactCommand
     * and returns an AddContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        ContactName name = ParserUtil.parseContactName(argMultimap.getValue(PREFIX_NAME).get());
        ContactPhone phone = ParserUtil.parseContactPhone(argMultimap.getValue(PREFIX_PHONE).get());
        ContactEmail email = ParserUtil.parseContactEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Contact contact = new Contact(name, phone, email, tagList);
        return new AddContactCommand(contact);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

