package seedu.meeting.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.meeting.commons.exceptions.DataConversionException;
import seedu.meeting.commons.util.XmlUtil;

/**
 * Stores MeetingBook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given MeetingBook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableMeetingBook meetingBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, meetingBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns MeetingBook in the file or an empty MeetingBook
     */
    public static XmlSerializableMeetingBook loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableMeetingBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
