package seedu.address.storage.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EntryBook;
import seedu.address.model.ReadOnlyEntryBook;
import seedu.address.model.entry.ResumeEntry;

/**
 * An Immutable EntryBook that is serializable to XML format
 */
@XmlRootElement(name = "entrybook")
public class XmlSerializableEntryBook {

    public static final String MESSAGE_DUPLICATE_ENTRY = "Entry list contains duplicate entries.";

    @XmlElement(name = "entry")
    private List<XmlAdaptedResumeEntry> resumeEntries;

    /**
     * Creates an empty XmlSerializableEntryBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableEntryBook() {
        resumeEntries = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableEntryBook(ReadOnlyEntryBook src) {
        this();
        resumeEntries.addAll(src.getEntryList().stream().map(XmlAdaptedResumeEntry::new).collect(Collectors.toList()));
    }

    /**
     * Converts this entrybook into the model's {@code EntryBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedResumeEntryBook}.
     */
    public EntryBook toModelType() throws IllegalValueException {
        EntryBook entryBook = new EntryBook();
        for (XmlAdaptedResumeEntry e : resumeEntries) {
            ResumeEntry entry = e.toModelType();
            if (entryBook.hasEntry(entry)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ENTRY);
            }
            entryBook.addEntry(entry);
        }
        return entryBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableEntryBook)) {
            return false;
        }
        return resumeEntries.equals(((XmlSerializableEntryBook) other).resumeEntries);
    }
}
