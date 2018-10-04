package seedu.address.storage;

import java.util.LinkedList;

import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * This Xml adapted class acts as a wrapper for a collection of {@code XmlAdaptedWish}es.
 */
public class XmlAdaptedWishWrapper {
    @XmlElementWrapper
    private LinkedList<XmlAdaptedWish> xmlAdaptedWishes;

    public XmlAdaptedWishWrapper() {
        this.xmlAdaptedWishes = new LinkedList<>();
    }

    public XmlAdaptedWishWrapper(LinkedList<XmlAdaptedWish> xmlAdaptedWishes) {
        this.xmlAdaptedWishes = xmlAdaptedWishes;
    }

    public LinkedList<XmlAdaptedWish> getXmlAdaptedWishes() {
        return xmlAdaptedWishes;
    }
}
