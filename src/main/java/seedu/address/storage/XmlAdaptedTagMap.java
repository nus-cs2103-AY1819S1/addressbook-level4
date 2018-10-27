package seedu.address.storage;

import java.util.Map;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.TagKey;
import seedu.address.model.tag.TagMap;
import seedu.address.model.tag.TagValue;

/**
 * An xml storage representation for a TagMap within this address book.
 */
public class XmlAdaptedTagMap {

    @XmlValue
    private Map<TagKey, TagValue> tagMap;

    public XmlAdaptedTagMap() {}

    public XmlAdaptedTagMap(TagMap tagMap) {
        this.tagMap = tagMap.getTagMap();
    }

    public XmlAdaptedTagMap(Map<TagKey, TagValue> otherMap) {
        tagMap = otherMap;
    }

    public TagMap toModelType() throws IllegalValueException {
        return new TagMap(tagMap);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTag)) {
            return false;
        }

        return tagMap.equals((TagMap) other);
    }



}
