package seedu.address.storage;

import static seedu.address.commons.util.StringUtil.isEmptyString;
import static seedu.address.commons.util.StringUtil.isOnlyWhiteSpace;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.storage.entry.XmlAdaptedResumeEntry;

/** This class contains an event name and its associated resume entry.
 *  It is meant to be JAXB friendly - i.e. meant to be used for de-serialisation from XML into a Java object
 *  representation.
 */
public class XmlAdaptedContextEntry {

    public static final String MESSAGE_EVENTNAME_REQUIREMENT = "Every contextual entry must have an event name.";
    public static final String MESSAGE_EVENTNAME_CONSTRAINT = "An event name cannot be an empty string or only "
                                                               + "whitespace.";
    public static final String MESSAGE_ENTRY_REQUIREMENT = "Each contextual entry must have an entry element.";

    @XmlAttribute
    private String eventName;

    @XmlElement
    private XmlAdaptedResumeEntry entry;

    public String getEventName() throws IllegalValueException {
        if (eventName == null) {
            throw new IllegalValueException(MESSAGE_EVENTNAME_REQUIREMENT);
        }

        if (isEmptyString(eventName) || isOnlyWhiteSpace(eventName)) {
            throw new IllegalValueException(MESSAGE_EVENTNAME_CONSTRAINT);
        }

        return eventName;
    }

    public ResumeEntry getEntry() throws IllegalValueException {
        if (entry == null) {
            throw new IllegalValueException(MESSAGE_ENTRY_REQUIREMENT);
        }

        return entry.toModelType();
    }

}
