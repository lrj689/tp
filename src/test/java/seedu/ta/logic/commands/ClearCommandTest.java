package seedu.ta.logic.commands;

import static seedu.ta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ta.testutil.TypicalContacts.getTypicalContactsTeachingAssistant;

import org.junit.jupiter.api.Test;

import seedu.ta.model.Model;
import seedu.ta.model.ModelManager;
import seedu.ta.model.TeachingAssistant;
import seedu.ta.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyTeachingAssistant_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTeachingAssistant_success() {
        Model model = new ModelManager(getTypicalContactsTeachingAssistant(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalContactsTeachingAssistant(), new UserPrefs());
        expectedModel.setTeachingAssistant(new TeachingAssistant());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
