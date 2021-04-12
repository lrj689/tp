package seedu.ta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ta.commons.core.Messages.MESSAGE_OVERLAPPING_ENTRY;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.ta.logic.commands.exceptions.CommandException;
import seedu.ta.model.Model;
import seedu.ta.model.entry.Entry;

/**
 * Adds an Entry to the list
 */
public class AddEntryCommand extends Command {

    public static final String COMMAND_WORD = "eadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entry to the list. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_END_DATE + "END DATE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "consultation 1 "
            + PREFIX_START_DATE + "2021-04-01 17:00 "
            + PREFIX_END_DATE + "2021-04-01 19:00 "
            + PREFIX_TAG + "CS2100";

    public static final String MESSAGE_SUCCESS = "New entry added: %1$s";

    private final Entry toAdd;

    /**
     * Creates an AddEntryCommand to add the specified {@code Entry}
     * @param entry
     */
    public AddEntryCommand(Entry entry) {
        requireNonNull(entry);
        toAdd = entry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isOverlappingEntry(toAdd)) {
            throw new CommandException(MESSAGE_OVERLAPPING_ENTRY);
        }

        model.addEntry(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddEntryCommand
                && toAdd.equals(((AddEntryCommand) other).toAdd));
    }
}
