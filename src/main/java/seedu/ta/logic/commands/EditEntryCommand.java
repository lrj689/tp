package seedu.ta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ta.commons.core.Messages.MESSAGE_EDIT_ENTRY_SUCCESS;
import static seedu.ta.commons.core.Messages.MESSAGE_INVALID_DATE_RANGE;
import static seedu.ta.commons.core.Messages.MESSAGE_INVALID_ENTRY_INDEX;
import static seedu.ta.commons.core.Messages.MESSAGE_OVERLAPPING_ENTRY;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ta.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import java.util.List;
import java.util.Set;

import seedu.ta.commons.core.index.Index;
import seedu.ta.logic.commands.exceptions.CommandException;
import seedu.ta.model.Model;
import seedu.ta.model.entry.Entry;
import seedu.ta.model.entry.EntryDate;
import seedu.ta.model.entry.EntryName;
import seedu.ta.model.entry.TemporaryEntry;
import seedu.ta.model.tag.Tag;

/**
 * Edits the details of an existing entry in the address book.
 */
public class EditEntryCommand extends Command {

    public static final String COMMAND_WORD = "eedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the entry identified "
            + "by the entry index used in the displayed entry list. Index must be a positive integer."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + "[" + PREFIX_END_DATE + "END_DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Meeting with group ";

    private final Index targetIndex;
    private final TemporaryEntry tempEntry;

    /**
     * @param targetIndex to identify which entry by its index
     * @param tempEntry containing the details to edit the entry with
     */
    public EditEntryCommand(Index targetIndex, TemporaryEntry tempEntry) {
        requireNonNull(targetIndex);
        requireNonNull(tempEntry);

        this.targetIndex = targetIndex;
        this.tempEntry = tempEntry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Entry> lastShownList = model.getFilteredEntryList();

        Integer index = targetIndex.getOneBased();
        if (index < 1 || index > lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_ENTRY_INDEX);
        }

        Entry targetEntry = lastShownList.get(index - 1);

        EntryName updatedEntryName = tempEntry.getEntryName().orElse(targetEntry.getEntryName());
        EntryDate updatedEntryStartDate = tempEntry.getStartDate().orElse(targetEntry.getOriginalStartDate());
        EntryDate updatedEntryEndDate = tempEntry.getEndDate().orElse(targetEntry.getOriginalEndDate());
        Set<Tag> updatedTags = tempEntry.getTags().orElse(targetEntry.getTags());
        Entry updatedEntry = new Entry(updatedEntryName, updatedEntryStartDate, updatedEntryEndDate, updatedTags);

        if (!updatedEntryStartDate.isBefore(updatedEntryEndDate)) {
            throw new CommandException(MESSAGE_INVALID_DATE_RANGE);
        }

        model.deleteEntry(targetEntry);
        if (model.isOverlappingEntry(updatedEntry)) {
            model.addEntry(targetEntry);
            throw new CommandException(MESSAGE_OVERLAPPING_ENTRY);
        }

        model.addEntry(updatedEntry);
        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, updatedEntry));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEntryCommand)) {
            return false;
        }

        // state check
        EditEntryCommand e = (EditEntryCommand) other;
        return targetIndex.equals(e.targetIndex)
                && tempEntry.equals(e.tempEntry);
    }
}
