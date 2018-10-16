package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.Awareness;

/**
 * This class is used to de-serialize XML data into an in-memory Awareness object.
 * It controls the logic used to perform the de-serialization.
 */
@XmlRootElement(name = "awareness")
public class XmlSerializableAwareness {

    @XmlElement (name = "dictionary")
    private LinkedList<XmlMapping> mappings;

    /* Default constructor is needed for the JAXB library to work */
    public XmlSerializableAwareness() {

    }

    public XmlSerializableAwareness(LinkedList<XmlMapping> mappings) {
        this.mappings = mappings;
    }

    /**
     * Convert XML data to an instance of XmlSerializableAwareness object.
     *
     * @param file the path to the XML data
     * @return an instance of XmlSerializableAwareness
     * @throws DataConversionException
     * @throws FileNotFoundException
     */
    public static XmlSerializableAwareness loadDataFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableAwareness.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Uses the data in this XmlSerializableAwareness object to generate an instance of Awareness.
     * TODO: Add validation in this method, to check that the user-defined XML data is valid.
     *
     * @return an instance of Awareness, representing the data held by this XmlSerializableAwareness object
     */
    public Awareness toModelType() {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        TreeSet<String> allEventNames = new TreeSet<String>();

        for (XmlMapping map : mappings) {
            String currentFullPhrase = map.getFullPhrase();
            allEventNames.add(currentFullPhrase);

            for (String eachSlang : map.getSlangSet()) {
                dictionary.put(eachSlang, currentFullPhrase);
            }
        }

        return new Awareness(dictionary, allEventNames);
    }

    @Override
    public String toString() {
        return this.mappings.toString();
    }
}
