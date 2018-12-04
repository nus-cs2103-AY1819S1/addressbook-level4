package seedu.thanepark.storage;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB-friendly list of XmlAdaptedCommandEntry.
 */
@XmlRootElement
public class XmlListOfCommandEntry {
    @XmlElement(name = "xmlAdaptedCommandEntry", required = true)
    private List<XmlAdaptedCommandEntry> value;

    /**
     * Constructs an XmlListOfCommandEntry.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlListOfCommandEntry() {}

    public List<XmlAdaptedCommandEntry> getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlListOfCommandEntry)) {
            return false;
        }

        XmlListOfCommandEntry otherListOfCommandEntry = (XmlListOfCommandEntry) other;
        return Objects.equals(value, otherListOfCommandEntry.getValue());
    }

}
