package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.awareness.Awareness;
import seedu.address.model.awareness.Dictionary;
import seedu.address.model.entry.ResumeEntry;

/**
 * This class is used to de-serialize XML data into an in-memory Awareness object.
 * It controls the logic used to perform the de-serialization.
 */
@XmlRootElement(name = "awareness")
public class XmlSerializableAwareness {

    public static final String MESSAGE_CONTEXTENTRY_REQUIREMENT = "There must be at least one contextual entry "
                                                                   + "specified. ";

    public static final String MESSAGE_DUPLICATE_EVENTNAME = "The same event name cannot be used for more than one "
                                                              + "contextual entry.";

    @XmlElement (name = "mapping")
    private LinkedList<XmlMapping> mappings;

    @XmlElement (name = "context-entry")
    private List<XmlAdaptedContextEntry> contextEntries;

    /* Default constructor is needed for the JAXB library to work */
    public XmlSerializableAwareness() {

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
     *
     * @return an instance of Awareness, representing the data held by this XmlSerializableAwareness object
     */
    public Awareness toModelType() throws IllegalValueException {

        if (contextEntries == null) {
            throw new IllegalValueException(MESSAGE_CONTEXTENTRY_REQUIREMENT);
        }

        Dictionary dictionary = new Dictionary();
        TreeMap<String, ResumeEntry> nameToEntryMappings = new TreeMap<String, ResumeEntry>();

        processContextEntries(nameToEntryMappings);

        // Mappings are optional, and can be left null --> exception does not need to be thrown
        if (mappings != null) {
            processMappings(dictionary);
        }

        return new Awareness(dictionary, nameToEntryMappings);
    }

    /** Precondition: contextEntries is not null */
    private void processContextEntries(TreeMap<String, ResumeEntry> nameToEntryMappings) throws IllegalValueException {

        for (XmlAdaptedContextEntry contextEntry : contextEntries) {

            String currentEventName = contextEntry.getEventName();
            ResumeEntry currentEntry = contextEntry.getEntry();

            if (nameToEntryMappings.containsKey(currentEventName)) {
                // duplicate event names are not allowed
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENTNAME);
            }

            nameToEntryMappings.put(currentEventName, currentEntry);
        }

    }

    /** Precondition: mappings is not null */
    private void processMappings(Dictionary dictionary) throws IllegalValueException {

        for (XmlMapping map : mappings) {
            String currentFullPhrase = map.getFullPhrase();
            HashSet<String> slangSet = map.getSlang();

            dictionary.registerFullPhrase(currentFullPhrase);

            if (slangSet == null) {
                // current full phrase is not associated with any slang
                continue;
            }

            for (String eachSlang : slangSet) {
                dictionary.registerMapping(eachSlang, currentFullPhrase);
            }
        }

    }
}
