package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AppContent;

/**
 * Generic parent for all the serializable classes
 */
public abstract class XmlSerializableGeneric {


    public abstract AppContent toModelType() throws IllegalValueException;
}
