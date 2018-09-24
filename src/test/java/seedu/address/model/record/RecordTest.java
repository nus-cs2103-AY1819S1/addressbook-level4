package seedu.address.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENTID_E1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENTID_E2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_H2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_R2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEERID_V1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEERID_V2;
import static seedu.address.testutil.TypicalRecords.R1;
import static seedu.address.testutil.TypicalRecords.R2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.RecordBuilder;

public class RecordTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameRecord() {
        // same object -> returns true
        assertTrue(R1.isSameRecord(R1));

        // null -> returns false
        assertFalse(R1.isSameRecord(null));

        // different eventId and volunteerId -> returns false
        Record editedRecord = new RecordBuilder(R1).withEventId(VALID_EVENTID_E2)
                .withVolunteerId(VALID_VOLUNTEERID_V2).build();
        assertFalse(R1.isSameRecord(editedRecord));

        // same eventId, same volunteerId -> returns true
        editedRecord = new RecordBuilder(R1).withEventId(VALID_EVENTID_E1)
                .withVolunteerId(VALID_VOLUNTEERID_V1).build();
        assertTrue(R1.isSameRecord(editedRecord));

        // same eventId, same volunteerId, different attributes -> returns true
        editedRecord = new RecordBuilder(R1).withEventId(VALID_EVENTID_E1).withVolunteerId(VALID_VOLUNTEERID_V1)
                .withHour(VALID_HOUR_H2).withRemark(VALID_REMARK_R2).build();
        assertTrue(R1.isSameRecord(editedRecord));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Record r1Copy = new RecordBuilder(R1).build();
        assertTrue(R1.equals(r1Copy));

        // same object -> returns true
        assertTrue(R1.equals(R1));

        // null -> returns false
        assertFalse(R1.equals(null));

        // different type -> returns false
        assertFalse(R1.equals(5));

        // different Record -> returns false
        assertFalse(R1.equals(R2));

        // different eventId -> returns false
        Record editedRecord = new RecordBuilder(R1).withEventId(VALID_EVENTID_E2).build();
        assertFalse(R1.equals(editedRecord));

        // different volunteerId -> returns false
        editedRecord = new RecordBuilder(R1).withVolunteerId(VALID_VOLUNTEERID_V2).build();
        assertFalse(R1.equals(editedRecord));

        // different hour -> returns false
        editedRecord = new RecordBuilder(R1).withHour(VALID_HOUR_H2).build();
        assertFalse(R1.equals(editedRecord));

        // different remark -> returns false
        editedRecord = new RecordBuilder(R1).withRemark(VALID_REMARK_R2).build();
        assertFalse(R1.equals(editedRecord));
    }
}
