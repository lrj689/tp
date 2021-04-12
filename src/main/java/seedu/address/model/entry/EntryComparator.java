package seedu.address.model.entry;

import java.util.Comparator;

/**
 * A comparator used to sort an Entry List according to {@code Entry}'s start date from earliest to latest.
 */
public class EntryComparator implements Comparator<Entry> {

    @Override
    public int compare(Entry a, Entry b) {
        if (a.getStartDate().isBefore(b.getStartDate())) {
            return -1;
        } else {
            return 1;
        }
    }

}
