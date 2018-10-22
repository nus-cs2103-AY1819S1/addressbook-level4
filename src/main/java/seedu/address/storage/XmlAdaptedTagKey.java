package seedu.address.storage;

import seedu.address.model.tag.TagKey;

import javax.xml.bind.annotation.XmlValue;

public class XmlAdaptedTagKey {

    @XmlValue
    private String tagKey;


    public XmlAdaptedTagKey() {}

    public XmlAdaptedTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public TagKey toModelType() {
        return new TagKey(tagKey);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return tagKey.equals((String) other);
    }

    @Override
    public int hashCode() {
        return tagKey.hashCode();
    }
}
