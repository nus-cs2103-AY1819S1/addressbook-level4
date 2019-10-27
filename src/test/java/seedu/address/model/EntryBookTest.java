package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEntrys.AWARD_WITH_NO_ENTRYINFO_NO_DESC;
import static seedu.address.testutil.TypicalEntrys.NUS_EDUCATION;
import static seedu.address.testutil.TypicalEntrys.NUS_EDUCATION_WITH_SPACED_TAG;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK_DIFF_ENTRYINFO_SAME_CAT_TAG;
import static seedu.address.testutil.TypicalEntrys.getTypicalEntryBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.entry.exceptions.DuplicateEntryException;
import seedu.address.model.util.EntryBuilder;
import seedu.address.testutil.EntryBookBuilder;

public class EntryBookTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EntryBook entryBook = new EntryBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), entryBook.getEntryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        entryBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyEntryBook_replacesData() {
        EntryBook newData = getTypicalEntryBook();
        entryBook.resetData(newData);
        assertEquals(newData, entryBook);
    }

    @Test
    public void resetData_withDuplicateEntries_throwsDuplicateEntryException() {
        ResumeEntry editedNusEducation = new EntryBuilder().withCategory("education")
                .withTitle("National University of Singapore").withDuration("2010 - 2014")
                .withSubHeader("Bachelor of computing")
                .withTags("machinelearning").build();
        List<ResumeEntry> newEntries = Arrays.asList(NUS_EDUCATION, editedNusEducation);
        EntryBookTest.EntryBookStub newData = new EntryBookTest.EntryBookStub(newEntries);

        thrown.expect(DuplicateEntryException.class);
        entryBook.resetData(newData);
    }

    @Test
    public void hasEntry_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        entryBook.hasEntry(null);
    }

    @Test
    public void hasEntry_entryNotInAddressBook_returnsFalse() {
        assertFalse(entryBook.hasEntry(NUS_EDUCATION));
    }

    @Test
    public void hasEntry_entryInAddressBook_returnsTrue() {
        entryBook.addEntry(NUS_EDUCATION);
        assertTrue(entryBook.hasEntry(NUS_EDUCATION));
    }

    @Test
    public void hasEntry_entryWithSameIdentityFieldsInEntryBook_returnsTrue() {
        entryBook.addEntry(NUS_EDUCATION);
        ResumeEntry editedNusEducation = new EntryBuilder().withCategory("education")
                .withTitle("National University of Singapore").withDuration("2010 - 2014")
                .withSubHeader("Bachelor of computing")
                .withTags("machinelearning").build();
        assertTrue(entryBook.hasEntry(editedNusEducation));
    }

    @Test
    public void getEntryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        entryBook.getEntryList().remove(0);
    }

    @Test
    public void toString_hashCode_constructEntryBookFromAnotherEntryBook() {
        EntryBook newData = getTypicalEntryBook();
        assertEquals(getTypicalEntryBook(), new EntryBook(newData));
        // test toString method
        assertEquals("5 entries", newData.toString());
        //test hashcode
        assertEquals(getTypicalEntryBook().hashCode(), newData.hashCode());
    }

    @Test
    public void removeAndAddEntryTest() {
        EntryBook typicalEntryBook = getTypicalEntryBook();
        typicalEntryBook.removeEntry(WORK_FACEBOOK);
        typicalEntryBook.updateEntry(NUS_EDUCATION_WITH_SPACED_TAG, WORK_FACEBOOK);

        EntryBook editedEntryBook = new EntryBookBuilder()
                .withEntry(WORK_FACEBOOK)
                .withEntry(AWARD_WITH_NO_ENTRYINFO_NO_DESC)
                .withEntry(NUS_EDUCATION)
                .withEntry(WORK_FACEBOOK_DIFF_ENTRYINFO_SAME_CAT_TAG)
                .build();
        assertEquals(editedEntryBook, typicalEntryBook);
    }


    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class EntryBookStub implements ReadOnlyEntryBook {
        private final ObservableList<ResumeEntry> entries = FXCollections.observableArrayList();

        EntryBookStub(Collection<ResumeEntry> entries) {
            this.entries.setAll(entries);
        }

        @Override
        public ObservableList<ResumeEntry> getEntryList() {
            return entries;
        }
    }
}
