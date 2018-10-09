package seedu.souschef.storage;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.AppContent;

/**
 * Generic parent for all the serializable classes
 */
public abstract class XmlSerializableGeneric {


    public abstract AppContent toModelType() throws IllegalValueException;
}
