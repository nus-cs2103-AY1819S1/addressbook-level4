package seedu.modsuni.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.modsuni.commons.exceptions.IllegalValueException;
import seedu.modsuni.model.module.Code;

/**
 * JAXB-friendly version of the Locked Module Codes.
 */
public class XmlAdaptedLockedModules {

    @XmlValue
    private String lockedModuleCode;

    public XmlAdaptedLockedModules() { }

    public XmlAdaptedLockedModules(String lockedModuleCode) {
        this.lockedModuleCode = lockedModuleCode;
    }

    public XmlAdaptedLockedModules(Code source) {
        lockedModuleCode = source.code;
    }

    /**
     * Converts this jaxb-friendly adapted locked module object into the model's Code object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted locked
     * module code.
     */
    public Code toModelType() throws IllegalValueException {
        if (!Code.isValidCode(lockedModuleCode)) {
            throw new IllegalValueException(Code.MESSAGE_CODE_CONSTRAINTS);
        }
        return new Code(lockedModuleCode);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedLockedModules)) {
            return false;
        }

        return lockedModuleCode.equals(((XmlAdaptedLockedModules) other).lockedModuleCode);
    }
}
