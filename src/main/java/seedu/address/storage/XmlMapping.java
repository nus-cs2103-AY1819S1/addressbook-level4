package seedu.address.storage;

import static seedu.address.commons.util.StringUtil.isEmptyString;
import static seedu.address.commons.util.StringUtil.isOnlyWhiteSpace;

import java.util.HashSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;


/**
 *  This class holds a full phrase and, optionally, a set of slang corresponding to this full phrase.
 *  For example, a full phrase could be "computer science" and the set of slang would be {"cs", "compsci"}.
 *  Note that it is ok for a full phrase to be associated with no slang.
 *
 *  It is meant to be JAXB friendly - i.e. meant to be used to des-serialize mappings held in XML, into a Java object
 *  representation.
 */

@XmlRootElement
public class XmlMapping {

    public static final String MESSAGE_FULLPHRASE_REQUIREMENT = "Each mapping must have a full phrase.";
    public static final String MESSAGE_FULLPHRASE_CONSTRAINT = "Fullphrase cannot be empty string, or only whitespace.";

    @XmlElement
    private String fullPhrase;

    @XmlElement
    private HashSet<String> slang;

    /* JAXB requires a default constructor */
    public XmlMapping() {}

    public String getFullPhrase() throws IllegalValueException {

        if (fullPhrase == null) {
            throw new IllegalValueException(MESSAGE_FULLPHRASE_REQUIREMENT);
        }

        if (isEmptyString(fullPhrase) || isOnlyWhiteSpace(fullPhrase)) {
            throw new IllegalValueException(MESSAGE_FULLPHRASE_CONSTRAINT);
        }

        return fullPhrase;
    }

    public HashSet<String> getSlang() {

        /* it is ok for slang to be null --> simply means there is no slang associated with the full phrase in this
           mapping */

        return slang;
    }

    @Override
    public String toString() {
        return fullPhrase + "[" + slang + "]";
    }
}
