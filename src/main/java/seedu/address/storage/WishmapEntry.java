package seedu.address.storage;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class WishmapEntry {
    @XmlJavaTypeAdapter(XmlAdaptedID.class)
    @XmlAttribute
    public UUID key;

    @XmlValue
    public XmlAdaptedWishWrapper value;
}
