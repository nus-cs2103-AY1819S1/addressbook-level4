package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedRecord.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalRecords.R1;
import static seedu.address.testutil.TypicalRecords.R2;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.EventId;
import seedu.address.model.record.Hour;
import seedu.address.model.record.Remark;
import seedu.address.model.volunteer.VolunteerId;
import seedu.address.testutil.Assert;

public class XmlAdaptedRecordTest {
    private static final int INVALID_EVENTID = -1;
    private static final int INVALID_VOLUNTEERID = -1;
    private static final String INVALID_HOUR = "1abc";
    private static final String INVALID_REMARK = " ";

    private static final int VALID_EVENTID = R2.getEventId().id;
    private static final int VALID_VOLUNTEERID = R2.getVolunteerId().id;
    private static final String VALID_HOUR = R2.getHour().toString();
    private static final String VALID_REMARK = R2.getRemark().toString();

    @Test
    public void toModelType_validRecordDetails_returnsRecord() throws Exception {
        XmlAdaptedRecord record = new XmlAdaptedRecord(R1);
        assertEquals(R1, record.toModelType());
    }

    @Test
    public void toModelType_invalidEventId_throwsIllegalValueException() {
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(INVALID_EVENTID, VALID_VOLUNTEERID, VALID_HOUR, VALID_REMARK);
        String expectedMessage = EventId.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidVolunteerId_throwsIllegalValueException() {
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(VALID_EVENTID, INVALID_VOLUNTEERID, VALID_HOUR, VALID_REMARK);
        String expectedMessage = VolunteerId.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidHour_throwsIllegalValueException() {
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(VALID_EVENTID, VALID_VOLUNTEERID, INVALID_HOUR, VALID_REMARK);
        String expectedMessage = Hour.MESSAGE_HOUR_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullHour_throwsIllegalValueException() {
        XmlAdaptedRecord record = new XmlAdaptedRecord(VALID_EVENTID, VALID_VOLUNTEERID, null, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Hour.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidRemark_throwsIllegalValueException() {
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(VALID_EVENTID, VALID_VOLUNTEERID, VALID_HOUR, INVALID_REMARK);
        String expectedMessage = Remark.MESSAGE_REMARK_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        XmlAdaptedRecord record = new XmlAdaptedRecord(VALID_EVENTID, VALID_VOLUNTEERID, VALID_HOUR, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }
}
