package seedu.ta.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ta.logic.commands.CommandTestUtil.showEntryAtIndex;
import static seedu.ta.testutil.TypicalIndices.INDEX_FIRST;
import static seedu.ta.testutil.TypicalIndices.INDEX_SECOND;
import static seedu.ta.testutil.TypicalTeachingAssistant.getTypicalTeachingAssistant;

import org.junit.jupiter.api.Test;

import seedu.ta.commons.core.Messages;
import seedu.ta.commons.core.index.Index;
import seedu.ta.model.Model;
import seedu.ta.model.ModelManager;
import seedu.ta.model.UserPrefs;
import seedu.ta.model.entry.Entry;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteEntryCommand}.
 */
public class DeleteEntryCommandTest {

    private Model model = new ModelManager(getTypicalTeachingAssistant(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Entry entryToDelete = model.getFilteredEntryList().get(INDEX_FIRST.getZeroBased());
        DeleteEntryCommand deleteEntryCommand = new DeleteEntryCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteEntryCommand.MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete);

        ModelManager expectedModel = new ModelManager(model.getTeachingAssistant(), new UserPrefs());
        expectedModel.deleteEntry(entryToDelete);

        assertCommandSuccess(deleteEntryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredEntryList().size() + 1);
        DeleteEntryCommand deleteEntryCommand = new DeleteEntryCommand(outOfBoundsIndex);

        assertCommandFailure(deleteEntryCommand, model, Messages.MESSAGE_INVALID_ENTRY_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEntryAtIndex(model, INDEX_FIRST);

        Entry entryToDelete = model.getFilteredEntryList().get(INDEX_FIRST.getZeroBased());
        DeleteEntryCommand deleteEntryCommand = new DeleteEntryCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteEntryCommand.MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete);

        Model expectedModel = new ModelManager(model.getTeachingAssistant(), new UserPrefs());
        expectedModel.deleteEntry(entryToDelete);
        showNoEntry(expectedModel);

        assertCommandSuccess(deleteEntryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEntryAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of the entry list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTeachingAssistant().getEntryList().size());

        DeleteEntryCommand deleteEntryCommand = new DeleteEntryCommand(outOfBoundIndex);

        assertCommandFailure(deleteEntryCommand, model, Messages.MESSAGE_INVALID_ENTRY_INDEX);
    }

    @Test
    public void equals() {
        DeleteEntryCommand deleteFirstEntryCommand = new DeleteEntryCommand(INDEX_FIRST);
        DeleteEntryCommand deleteSecondEntryCommand = new DeleteEntryCommand(INDEX_SECOND);

        //Same object -> true
        assertTrue(deleteFirstEntryCommand.equals(deleteFirstEntryCommand));

        //Same values -> true
        DeleteEntryCommand deleteFirstEntryCommandCopy = new DeleteEntryCommand(INDEX_FIRST);
        assertTrue(deleteFirstEntryCommand.equals(deleteFirstEntryCommandCopy));

        //Different types -> false
        assertFalse(deleteSecondEntryCommand.equals("2"));

        //null -> false
        assertFalse(deleteSecondEntryCommand.equals(null));

        //different entry -> false
        assertFalse(deleteFirstEntryCommand.equals(deleteSecondEntryCommand));
    }

    /**
     * Update the {@code model}'s filtered list to show no entry.
     * @param model
     */
    private void showNoEntry(Model model) {
        model.updateFilteredEntryList(p -> false);

        assertTrue(model.getFilteredEntryList().isEmpty());
    }
}
