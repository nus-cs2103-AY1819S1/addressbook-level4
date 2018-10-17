package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Issue;
import seedu.address.model.ReadOnlySaveIt;
import seedu.address.model.SaveIt;

/**
 * An Immutable SaveIt that is serializable to XML format
 */
@XmlRootElement(name = "saveit")
public class XmlSerializableSaveIt {

    public static final String MESSAGE_DUPLICATE_PERSON = "Issues list contains duplicate issue(s).";

    @XmlElement
    private List<XmlAdaptedIssue> issues;

    /**
     * Creates an empty XmlSerializableSaveIt.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableSaveIt() {
        issues = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableSaveIt(ReadOnlySaveIt src) {
        this();
        issues.addAll(src.getIssueList().stream().map(XmlAdaptedIssue::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address into the model's {@code SaveIt} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedIssue}.
     */
    public SaveIt toModelType() throws IllegalValueException {
        SaveIt saveIt = new SaveIt();
        for (XmlAdaptedIssue i : issues) {
            Issue issue = i.toModelType();
            if (saveIt.hasIssue(issue)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            saveIt.addIssue(issue);
        }
        return saveIt;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableSaveIt)) {
            return false;
        }
        return issues.equals(((XmlSerializableSaveIt) other).issues);
    }
}
