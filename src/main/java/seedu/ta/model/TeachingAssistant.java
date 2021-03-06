package seedu.ta.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.ta.model.contact.Contact;
import seedu.ta.model.contact.UniqueContactList;
import seedu.ta.model.entry.Entry;
import seedu.ta.model.entry.NonOverlappingEntryList;


/**
 * Wraps all data at the teaching-assistant level.
 * Duplicates are not allowed (by .isSameContact comparison).
 */
public class TeachingAssistant implements ReadOnlyTeachingAssistant {

    private final UniqueContactList contacts;
    private final NonOverlappingEntryList entries;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        entries = new NonOverlappingEntryList();
        contacts = new UniqueContactList();
    }

    public TeachingAssistant() {}

    /**
     * Creates an TeachingAssistant using the data in the {@code toBeCopied}.
     */
    public TeachingAssistant(ReadOnlyTeachingAssistant toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// ===== List Operations =====

    /**
     * Replaces the contents of the contact list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts.setContacts(contacts);
    }

    /**
     * Replaces the contents of the entry list with {@code entries}.
     * {@code entries} must not contain overlapping entries.
     */
    public void setEntries(List<Entry> entries) {
        this.entries.setEntries(entries);
    }

    /**
     * Resets the existing data of this {@code TeachingAssistant} with {@code newData}.
     */
    public void resetData(ReadOnlyTeachingAssistant newData) {
        requireNonNull(newData);
        setContacts(newData.getContactList());
        setEntries(newData.getEntryList());
    }

    //// ===== Contact Operations =====

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in Teaching Assistant.
     */
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return contacts.contains(contact);
    }

    /**
     * Adds a contact to Teaching Assistant.
     * The contact must not already exist in Teaching Assistant.
     */
    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    /**
     * Replaces the given contact {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in Teaching Assistant.
     * The contact identity of {@code editedContact} must not be the same as another existing
     * contact in Teaching Assistant.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireNonNull(editedContact);
        contacts.setContact(target, editedContact);
    }

    /**
     * Removes {@code contact} from this {@code Teaching Assistant}.
     * {@code contact} must exist in Teaching Assistant.
     */
    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    //// ===== Entry Operations =====

    /**
     * Returns true if an entry with the same identity as {@code entry} exists in Teaching Assistant.
     */
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return entries.contains(entry);
    }

    /**
     * Adds an entry to the list.
     * The entry must not overlap with existing entries in the list.
     */
    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    /**
     * Removes an entry {@code entry} from the list.
     * {@code entry} must exist in the list.
     */
    public void removeEntry(Entry entry) {
        requireNonNull(entry);
        entries.remove(entry);
    }

    /**
     * Replaces the given entry {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the list.
     * {@code editedEntry} must not overlap with existing entries in the list.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireNonNull(editedEntry);
        entries.setEntry(target, editedEntry);
    }

    /**
     * Returns true if the give entry overlaps with existing entries in the list.
     */
    public boolean isOverlappingEntry(Entry entry) {
        requireNonNull(entry);
        return entries.overlapsWith(entry);
    }

    /**
     * Clears all overdue entries in Teaching Assistant.
     */
    public void clearOverdueEntries() {
        entries.clearOverdueEntries();
    }

    // ===== Util =====

    @Override
    public String toString() {
        return contacts.asUnmodifiableObservableList().size() + " contacts";
        // TODO: refine later
    }

    @Override
    public ObservableList<Contact> getContactList() {
        return contacts.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Entry> getEntryList() {
        return entries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TeachingAssistant // instanceof handles nulls
                && contacts.equals(((TeachingAssistant) other).contacts));
    }

    @Override
    public int hashCode() {
        return contacts.hashCode();
    }
}
