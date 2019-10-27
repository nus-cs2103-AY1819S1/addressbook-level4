package seedu.address.storage.entry;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entry.EntryInfo;

/** A JAXB friendly version of EntryInfo */
public class XmlAdaptedEntryInfo {

    private static final String MESSAGE_MISSING_ENTRY_INFO = "An entry is missing the %s field";

    @XmlAttribute
    private String title;
    @XmlAttribute
    private String subheader;
    @XmlAttribute
    private String duration;

    /**
     * Default constructor required by JAXB.
     */
    public XmlAdaptedEntryInfo() {}

    public XmlAdaptedEntryInfo(EntryInfo source) {
        title = source.getTitle();
        subheader = source.getSubHeader();
        duration = source.getDuration();
    }

    /**
     * Constructs a {@code XmlAdaptedEntryInfo} with the given details.
     */
    public XmlAdaptedEntryInfo(String title, String subheader, String duration) {
        this.title = title;
        this.subheader = subheader;
        this.duration = duration;
    }

    /**
     * Converts this jaxb-friendly adapted entry info object into the model's EntryInfo object
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted entry info
     */
    public EntryInfo toModelType() throws IllegalValueException {

        /* If entryInfo has been specified in XML, all 3 fields must be provided */
        if (title == null) {
            throw new IllegalValueException(String.format(MESSAGE_MISSING_ENTRY_INFO, "title"));
        }

        if (subheader == null) {
            throw new IllegalValueException(String.format(MESSAGE_MISSING_ENTRY_INFO, "subheader"));
        }

        if (duration == null) {
            throw new IllegalValueException(String.format(MESSAGE_MISSING_ENTRY_INFO, "duration"));
        }

        List<String> toBeValidated = Arrays.asList(title, subheader, duration);

        if (!EntryInfo.isValidEntryInfo(toBeValidated)) {
            throw new IllegalValueException(EntryInfo.MESSAGE_ENTRYINFO_CONSTRAINTS);
        }

        return new EntryInfo(title, subheader, duration);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEntryInfo)) {
            return false;
        }

        XmlAdaptedEntryInfo otherAdaptedEntryInfo = (XmlAdaptedEntryInfo) other;
        return Objects.equals(title, otherAdaptedEntryInfo.title)
                 && Objects.equals(subheader, otherAdaptedEntryInfo.subheader)
                 && Objects.equals(duration, otherAdaptedEntryInfo.duration);
    }
}
