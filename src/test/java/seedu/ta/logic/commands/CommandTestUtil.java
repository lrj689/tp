package seedu.ta.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ta.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ta.commons.core.index.Index;
import seedu.ta.logic.commands.exceptions.CommandException;
import seedu.ta.model.Model;
import seedu.ta.model.TeachingAssistant;
import seedu.ta.model.contact.Contact;
import seedu.ta.model.contact.ContactNameContainsKeywordsPredicate;
import seedu.ta.model.entry.Entry;
import seedu.ta.model.entry.EntryNameContainsKeywordsPredicate;
import seedu.ta.model.entry.ListEntryFormatPredicate;
import seedu.ta.testutil.EditContactDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String VALID_NAME_CONSULTATION = "Consultation";
    public static final String VALID_NAME_EXTRA = "Extra class";
    public static final String VALID_START_DATE_CONSULTATION = "2022-02-01 13:00";
    public static final String VALID_START_DATE_EXTRA = "2022-02-02 17:00";
    public static final String VALID_END_DATE_CONSULTATION = "2022-02-01 14:30";
    public static final String VALID_END_DATE_EXTRA = "2022-02-02 18:30";
    public static final String VALID_TAG_CONSULTATION = "History";
    public static final String VALID_TAG_EXTRA = "Math";

    public static final String NAME_DESC_CONSULTATION = " " + PREFIX_NAME + VALID_NAME_CONSULTATION;
    public static final String NAME_DESC_EXTRA = " " + PREFIX_NAME + VALID_NAME_EXTRA;
    public static final String START_DESC_CONSULTATION = " " + PREFIX_START_DATE + VALID_START_DATE_CONSULTATION;
    public static final String START_DESC_EXTRA = " " + PREFIX_START_DATE + VALID_START_DATE_EXTRA;
    public static final String END_DESC_CONSULTATION = " " + PREFIX_END_DATE + VALID_END_DATE_CONSULTATION;
    public static final String END_DESC_EXTRA = " " + PREFIX_END_DATE + VALID_END_DATE_EXTRA;
    public static final String TAG_DESC_CONSULTATION = " " + PREFIX_TAG + VALID_TAG_CONSULTATION;
    public static final String TAG_DESC_EXTRA = " " + PREFIX_TAG + VALID_TAG_EXTRA;

    public static final String INVALID_START_DATE = " " + PREFIX_START_DATE + "2020-02-30 10:00";
    public static final String INVALID_END_DATE = " " + PREFIX_END_DATE + "2021-10-10 24:30";
    public static final String INVALID_DATE_RANGE = " " + PREFIX_START_DATE + "2021-01-01 13:00"
            + " " + PREFIX_END_DATE + "2021-01-01 12:00";
    public static final String PAST_DATE_INTERVAL = " " + PREFIX_START_DATE + "2020-01-01 12:00"
            + " " + PREFIX_END_DATE + "2020-01-01 13:00";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditContactCommand.EditContactDescriptor CONTACT_DESC_AMY;
    public static final EditContactCommand.EditContactDescriptor CONTACT_DESC_BOB;

    public static final ListEntryFormatPredicate ALL_PREDICATE = new ListEntryFormatPredicate("");
    public static final ListEntryFormatPredicate DAY_PREDICATE = new ListEntryFormatPredicate("day");
    public static final ListEntryFormatPredicate WEEK_PREDICATE = new ListEntryFormatPredicate("week");

    static {
        CONTACT_DESC_AMY = new EditContactDescriptorBuilder().withContactName(VALID_NAME_AMY)
                .withContactPhone(VALID_PHONE_AMY).withContactEmail(VALID_EMAIL_AMY)
                .withContactTags(VALID_TAG_FRIEND).build();
        CONTACT_DESC_BOB = new EditContactDescriptorBuilder().withContactName(VALID_NAME_BOB)
                .withContactPhone(VALID_PHONE_BOB).withContactEmail(VALID_EMAIL_BOB)
                .withContactTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the teaching assistant, filtered contact list and selected contact in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TeachingAssistant expectedTeachingAssistant = new TeachingAssistant(actualModel.getTeachingAssistant());
        List<Contact> expectedFilteredList = new ArrayList<>(actualModel.getFilteredContactList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTeachingAssistant, actualModel.getTeachingAssistant());
        assertEquals(expectedFilteredList, actualModel.getFilteredContactList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the contact at the given {@code targetIndex} in the
     * {@code model}'s teaching assistant.
     */
    public static void showContactAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredContactList().size());

        Contact contact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        final String[] splitName = contact.getName().fullName.split("\\s+");
        model.updateFilteredContactList(new ContactNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredContactList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the entry at the given {@code targetIndex} in the
     * {@code model}'s list.
     */
    public static void showEntryAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEntryList().size());

        Entry entry = model.getFilteredEntryList().get(targetIndex.getZeroBased());
        final String[] splitEntryName = entry.getEntryName().name.split("\\s+");
        model.updateFilteredEntryList(new EntryNameContainsKeywordsPredicate(Arrays.asList(splitEntryName[0])));

        assertEquals(1, model.getFilteredEntryList().size());
    }
}
