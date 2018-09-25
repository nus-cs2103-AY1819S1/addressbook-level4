package seedu.address.model.record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_H2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_R2;
import static seedu.address.testutil.TypicalRecords.R1;
import static seedu.address.testutil.TypicalRecords.R2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.record.exceptions.DuplicateRecordException;
import seedu.address.model.record.exceptions.RecordNotFoundException;
import seedu.address.testutil.RecordBuilder;

public class UniqueRecordListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRecordList uniqueRecordList = new UniqueRecordList();

    @Test
    public void contains_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.contains(null);
    }

    @Test
    public void contains_recordNotInList_returnsFalse() {
        assertFalse(uniqueRecordList.contains(R1));
    }

    @Test
    public void contains_recordInList_returnsTrue() {
        uniqueRecordList.add(R1);
        assertTrue(uniqueRecordList.contains(R1));
    }

    @Test
    public void contains_recordWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRecordList.add(R1);
        Record editedRecord = new RecordBuilder(R1).withHour(VALID_HOUR_H2).withRemark(VALID_REMARK_R2)
                .build();
        assertTrue(uniqueRecordList.contains(editedRecord));
    }

    @Test
    public void add_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.add(null);
    }

    @Test
    public void add_duplicateRecord_throwsDuplicateRecordException() {
        uniqueRecordList.add(R1);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.add(R1);
    }

    @Test
    public void setRecord_nullTargetRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(null, R1);
    }

    @Test
    public void setRecord_nullEditedRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(R1, null);
    }

    @Test
    public void setRecord_targetRecordNotInList_throwsRecordNotFoundException() {
        thrown.expect(RecordNotFoundException.class);
        uniqueRecordList.setRecord(R1, R1);
    }

    @Test
    public void setRecord_editedRecordIsSameRecord_success() {
        uniqueRecordList.add(R1);
        uniqueRecordList.setRecord(R1, R1);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(R1);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecord_editedRecordHasSameIdentity_success() {
        uniqueRecordList.add(R1);
        Record editedRecord = new RecordBuilder(R1).withHour(VALID_HOUR_H2).withRemark(VALID_REMARK_R2)
                .build();
        uniqueRecordList.setRecord(R1, editedRecord);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(editedRecord);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecord_editedRecordHasDifferentIdentity_success() {
        uniqueRecordList.add(R1);
        uniqueRecordList.setRecord(R1, R2);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(R2);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecord_editedRecordHasNonUniqueIdentity_throwsDuplicateRecordException() {
        uniqueRecordList.add(R1);
        uniqueRecordList.add(R2);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.setRecord(R1, R2);
    }

    @Test
    public void remove_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.remove(null);
    }

    @Test
    public void remove_recordDoesNotExist_throwsRecordNotFoundException() {
        thrown.expect(RecordNotFoundException.class);
        uniqueRecordList.remove(R1);
    }

    @Test
    public void remove_existingRecord_removesRecord() {
        uniqueRecordList.add(R1);
        uniqueRecordList.remove(R1);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_nullUniqueRecordList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((UniqueRecordList) null);
    }

    @Test
    public void setRecords_uniqueRecordList_replacesOwnListWithProvidedUniqueRecordList() {
        uniqueRecordList.add(R1);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(R2);
        uniqueRecordList.setRecords(expectedUniqueRecordList);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((List<Record>) null);
    }

    @Test
    public void setRecords_list_replacesOwnListWithProvidedList() {
        uniqueRecordList.add(R1);
        List<Record> recordList = Collections.singletonList(R2);
        uniqueRecordList.setRecords(recordList);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(R2);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_listWithDuplicateRecords_throwsDuplicateRecordException() {
        List<Record> listWithDuplicateRecords = Arrays.asList(R1, R1);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.setRecords(listWithDuplicateRecords);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRecordList.asUnmodifiableObservableList().remove(0);
    }
}
