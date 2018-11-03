package seedu.scheduler.commons.util;

import static org.junit.Assert.assertEquals;
import static seedu.scheduler.model.util.SampleSchedulerDataUtil.getReminderDurationList;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.storage.XmlAdaptedEvent;
import seedu.scheduler.storage.XmlAdaptedTag;
import seedu.scheduler.storage.XmlSerializableScheduler;
import seedu.scheduler.testutil.EventBuilder;
import seedu.scheduler.testutil.SchedulerBuilder;
import seedu.scheduler.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validScheduler.xml");
    private static final Path MISSING_EVENT_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingEventField.xml");
    private static final Path INVALID_EVENT_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidEventField.xml");
    private static final Path VALID_EVENT_FILE = TEST_DATA_FOLDER.resolve("validEvent.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempScheduler.xml");

    private static final String INVALID_EVENT_NAME = " start blank space";

    private static final UUID VALID_EVENTUID = UUID.fromString("aef6b77e-20a7-4de4-8402-343fbd475fd4");
    private static final UUID VALID_EVENTSETUID = UUID.fromString("aef6b77e-20a7-4de4-8402-343fbd475fd3");
    private static final String VALID_EVENT_NAME = "CS2103 Tutorial";
    private static final DateTime VALID_START_DATETIME = new DateTime(LocalDateTime.of(2018, 9,
            21, 11, 0));
    private static final DateTime VALID_END_DATETIME = new DateTime(LocalDateTime.of(2018, 9,
            21, 12, 0));
    private static final String VALID_DESCRIPTION = "F11 Tutorial slot";
    private static final String VALID_VENUE = "School of Computing";
    private static final RepeatType VALID_REPEAT_TYPE = RepeatType.NONE;
    private static final DateTime VALID_REPEAT_UNTIL_DATETIME = new DateTime(LocalDateTime.of(2018, 9,
            21, 12, 0));
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("play"));
    private static final ReminderDurationList VALID_REMINDER_DURATION_LIST = getReminderDurationList(0);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, Scheduler.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, Scheduler.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, Scheduler.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        Scheduler dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableScheduler.class).toModelType();
        assertEquals(3, dataFromFile.getEventList().size());
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithMissingEventField_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                MISSING_EVENT_FIELD_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(VALID_EVENTUID, VALID_EVENTSETUID, null, VALID_START_DATETIME,
                VALID_END_DATETIME, VALID_DESCRIPTION, VALID_VENUE,
                VALID_REPEAT_TYPE, VALID_REPEAT_UNTIL_DATETIME, VALID_TAGS, VALID_REMINDER_DURATION_LIST);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithInvalidEventField_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                INVALID_EVENT_FIELD_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(VALID_EVENTUID, VALID_EVENTSETUID, INVALID_EVENT_NAME,
                VALID_START_DATETIME, VALID_END_DATETIME, VALID_DESCRIPTION, VALID_VENUE, VALID_REPEAT_TYPE,
                VALID_REPEAT_UNTIL_DATETIME, VALID_TAGS, VALID_REMINDER_DURATION_LIST);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithValidEvent_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                VALID_EVENT_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(VALID_EVENTUID, VALID_EVENTSETUID, VALID_EVENT_NAME,
                VALID_START_DATETIME, VALID_END_DATETIME, VALID_DESCRIPTION, VALID_VENUE, VALID_REPEAT_TYPE,
                VALID_REPEAT_UNTIL_DATETIME, VALID_TAGS, VALID_REMINDER_DURATION_LIST);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new Scheduler());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new Scheduler());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableScheduler dataToWrite = new XmlSerializableScheduler(new Scheduler());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableScheduler dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableScheduler.class);
        assertEquals(dataToWrite, dataFromFile);

        SchedulerBuilder builder = new SchedulerBuilder(new Scheduler());
        dataToWrite = new XmlSerializableScheduler(
                builder.withEvent(new EventBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableScheduler.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedEvent}
     * objects.
     */
    @XmlRootElement(name = "event")
    private static class XmlAdaptedEventWithRootElement extends XmlAdaptedEvent {}
}
