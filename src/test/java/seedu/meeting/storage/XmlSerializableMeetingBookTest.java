package seedu.meeting.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.meeting.commons.exceptions.IllegalValueException;
import seedu.meeting.commons.util.XmlUtil;
import seedu.meeting.model.MeetingBook;
import seedu.meeting.testutil.TypicalMeetingBook;

public class XmlSerializableMeetingBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableMeetingBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsMeetingBook.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonMeetingBook.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonMeetingBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableMeetingBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableMeetingBook.class);
        MeetingBook meetingBookFromFile = dataFromFile.toModelType();
        MeetingBook typicalPersonsMeetingBook = TypicalMeetingBook.getTypicalMeetingBook();
        assertEquals(meetingBookFromFile, typicalPersonsMeetingBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableMeetingBook dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableMeetingBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableMeetingBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                XmlSerializableMeetingBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableMeetingBook.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
