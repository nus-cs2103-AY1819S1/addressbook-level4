package seedu.address.testutil;

import seedu.address.model.EntryBook;
import seedu.address.model.entry.ResumeEntry;

/**
 * A utility class to help with building EntryBook objects.
 * Example usage: <br>
 *     {@code EntryBook eb = new EntryBookBuilder().withEntry(entry1, entry2).build();}
 */
public class EntryBookBuilder {

    private EntryBook entryBook;

    public EntryBookBuilder() {
        entryBook = new EntryBook();
    }

    public EntryBookBuilder(EntryBook entryBook) {
        this.entryBook = entryBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public EntryBookBuilder withEntry(ResumeEntry entry) {
        entryBook.addEntry(entry);
        return this;
    }

    public EntryBook build() {
        return entryBook;
    }
}
