package seedu.address.model.entry.exceptions;

/**
 * Signals that the operation will result in overlapping entries.
 * (Entries are considered overlapping if their start dates end dates clash with one another)
 */
public class OverlappingEntryException extends RuntimeException {
    public OverlappingEntryException() {
        super("Operation would result in overlapping entries");
    }
}
