package seedu.address.storage;

import seedu.address.model.tag.TagValue;

import javax.xml.bind.annotation.XmlValue;

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
