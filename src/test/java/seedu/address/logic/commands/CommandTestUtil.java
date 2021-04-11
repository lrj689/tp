package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactNameContainsKeywordsPredicate;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.EntryNameContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.entry.ListEntryFormatPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditContactDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ALICE = "Alice Pauline";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_ENTRY_NAME_EXAMS = "Exams";
    public static final String VALID_ENTRY_NAME_ASSIGNMENTS = "Assignment 3";
    public static final String VALID_ENTRY_NAME_CONSULTATION = "Consultation";
    public static final String VALID_START_DATE_EXAMS = "2021-04-01 17:00";
    public static final String VALID_END_DATE_EXAMS = "2021-04-01 19:00";
    public static final String VALID_START_DATE_ASSIGNMENT = "2021-04-03 19:00";
    public static final String VALID_END_DATE_ASSIGNMENT = "2021-04-03 19:00";
    public static final String VALID_START_DATE_CONSULTATION = "2021-04-05 10:00";
    public static final String VALID_END_DATE_CONSULTATION = "2021-04-05 13:00";
    public static final String VALID_TAG_CS2030T = "CS2030T";
    public static final String VALID_TAG_CS2100 = "CS2100";
    public static final String VALID_TAG_ALEX = "ALEX";
    public static final String VALID_TAG_BEN = "BEN";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_DATE_RANGE = " " + PREFIX_START_DATE + "2021-01-01 13:00"
            + " " + PREFIX_END_DATE + "2021-01-01 12:00";
    public static final String PAST_DATE_INTERVAL = " " + PREFIX_START_DATE + "2020-01-01 12:00"
            + " " + PREFIX_END_DATE + "2020-01-01 13:00";
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    //to be deleted-------------------------
    public static final Name FIRST_PERSON_NAME = new Name("Alice Pauline");
    public static final Name SECOND_PERSON_NAME = new Name("Benson Meier");
    public static final Name LAST_PERSON_NAME = new Name("George Best");
    public static final Name INVALID_PERSON_NAME = new Name("John Doe");

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final ListEntryFormatPredicate ALL_PREDICATE = new ListEntryFormatPredicate("");
    public static final ListEntryFormatPredicate DAY_PREDICATE = new ListEntryFormatPredicate("day");
    public static final ListEntryFormatPredicate WEEK_PREDICATE = new ListEntryFormatPredicate("week");

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }
    //----------------------------------

    public static final EditContactCommand.EditContactDescriptor CONTACT_DESC_AMY;
    public static final EditContactCommand.EditContactDescriptor CONTACT_DESC_BOB;

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
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the contact at the given {@code targetIndex} in the
     * {@code model}'s address book.
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

    /**
     * Updates {@code model}'s filtered list to show only the contact at the given {@code targetIndex} in the
     * {@code model}'s list.
     */
    public static void showContactAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredContactList().size());

        Contact contact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        final String[] splitContactName = contact.getName().fullName.split("\\s+");
        model.updateFilteredContactList(new ContactNameContainsKeywordsPredicate(Arrays.asList(splitContactName[0])));

        assertEquals(1, model.getFilteredContactList().size());
    }
}
