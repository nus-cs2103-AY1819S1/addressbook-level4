package seedu.superta.storage;

import seedu.superta.commons.exceptions.IllegalValueException;

/**
 * XMLAdapted class means it should be able to convert to its model type.
 * @param <T> Its model type.
 */
public interface XmlAdapted<T> {
    public T toModelType() throws IllegalValueException;
}
