package seedu.address.model.entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEntrys.NUS_EDUCATION;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.entry.exceptions.DuplicateEntryException;
import seedu.address.model.entry.exceptions.EntryNotFoundException;

public class UniqueEntryListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueEntryList uniqueEntryList = new UniqueEntryList();

    @Test
    public void contains_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.contains(null);
    }

    @Test
    public void contains_entryNotInList_returnsFalse() {
        assertFalse(uniqueEntryList.contains(WORK_FACEBOOK));
    }

    @Test
    public void contains_entryInList_returnsTrue() {
        uniqueEntryList.add(WORK_FACEBOOK);
        assertTrue(uniqueEntryList.contains(WORK_FACEBOOK));
    }

    @Test
    public void add_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.add(null);
    }

    @Test
    public void add_duplicateEntry_throwsDuplicateEntryException() {
        uniqueEntryList.add(WORK_FACEBOOK);
        thrown.expect(DuplicateEntryException.class);
        uniqueEntryList.add(WORK_FACEBOOK);
    }

    @Test
    public void setEntry_nullTargetEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.setEntry(null, WORK_FACEBOOK);
    }

    @Test
    public void setEntry_nullEditedEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.setEntry(WORK_FACEBOOK, null);
    }

    @Test
    public void setEntry_targetEntryNotInList_throwsEntryNotFoundException() {
        thrown.expect(EntryNotFoundException.class);
        uniqueEntryList.setEntry(WORK_FACEBOOK, WORK_FACEBOOK);
    }

    @Test
    public void setEntry_editedEntryIsSameEntry_success() {
        uniqueEntryList.add(WORK_FACEBOOK);
        uniqueEntryList.setEntry(WORK_FACEBOOK, WORK_FACEBOOK);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(WORK_FACEBOOK);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntry_editedEntryHasDifferentIdentity_success() {
        uniqueEntryList.add(WORK_FACEBOOK);
        uniqueEntryList.setEntry(WORK_FACEBOOK, NUS_EDUCATION);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(NUS_EDUCATION);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntry_editedEntryHasNonUniqueIdentity_throwsDuplicateEntryException() {
        uniqueEntryList.add(WORK_FACEBOOK);
        uniqueEntryList.add(NUS_EDUCATION);
        thrown.expect(DuplicateEntryException.class);
        uniqueEntryList.setEntry(WORK_FACEBOOK, NUS_EDUCATION);
    }

    @Test
    public void remove_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.remove(null);
    }

    @Test
    public void remove_entryDoesNotExist_throwsEntryNotFoundException() {
        thrown.expect(EntryNotFoundException.class);
        uniqueEntryList.remove(WORK_FACEBOOK);
    }

    @Test
    public void remove_existingEntry_removesEntry() {
        uniqueEntryList.add(WORK_FACEBOOK);
        uniqueEntryList.remove(WORK_FACEBOOK);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntries_nullUniqueEntryList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.setEntries((UniqueEntryList) null);
    }

    @Test
    public void setEntries_uniqueEntryList_replacesOwnListWithProvidedUniqueEntryList() {
        uniqueEntryList.add(WORK_FACEBOOK);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(NUS_EDUCATION);
        uniqueEntryList.setEntries(expectedUniqueEntryList);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntries_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.setEntries((List<ResumeEntry>) null);
    }

    @Test
    public void setEntries_list_replacesOwnListWithProvidedList() {
        uniqueEntryList.add(WORK_FACEBOOK);
        List<ResumeEntry> entryList = Collections.singletonList(NUS_EDUCATION);
        uniqueEntryList.setEntries(entryList);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(NUS_EDUCATION);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntries_listWithDuplicateEntrys_throwsDuplicateEntryException() {
        List<ResumeEntry> listWithDuplicateEntrys = Arrays.asList(WORK_FACEBOOK, WORK_FACEBOOK);
        thrown.expect(DuplicateEntryException.class);
        uniqueEntryList.setEntries(listWithDuplicateEntrys);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueEntryList.asUnmodifiableObservableList().remove(0);
    }
}
