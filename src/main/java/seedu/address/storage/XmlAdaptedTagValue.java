package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.model.tag.TagValue;

/**
 * A xml TagValue representation for a TagValue within this addressbook.
 */
public class XmlAdaptedTagValue {

    @XmlValue
    private String tagValue;


    public XmlAdaptedTagValue() {}

    public XmlAdaptedTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public TagValue toModelType() {
        return new TagValue(tagValue);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return tagValue.equals((String) other);
    }

    @Override
    public int hashCode() {
        return tagValue.hashCode();
    }
}
