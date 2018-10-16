package seedu.address.model.entry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEntrys.NUS_EDUCATION;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.EntryBuilder;
import seedu.address.testutil.TypicalEntrys;

public class EntryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        ResumeEntry entry = new EntryBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        entry.getTags().remove(0);
    }

    @Test
    public void isSameEntry() {
        // same object -> returns true
        assertTrue(NUS_EDUCATION.isSameEntry(NUS_EDUCATION));
        assertTrue(WORK_FACEBOOK.isSameEntry(WORK_FACEBOOK));

        // different object but same fields
        assertTrue(NUS_EDUCATION.isSameEntry(new EntryBuilder(NUS_EDUCATION).build()));

        // null -> returns false
        assertFalse(WORK_FACEBOOK.isSameEntry(null));
        assertFalse(NUS_EDUCATION.isSameEntry(null));

        // different category -> returns false
        ResumeEntry editedWork = new EntryBuilder(WORK_FACEBOOK).withCategory("education").build();
        assertFalse(WORK_FACEBOOK.isSameEntry(editedWork));
        ResumeEntry editedEducation = new EntryBuilder(NUS_EDUCATION).withCategory("work").build();
        assertFalse(NUS_EDUCATION.isSameEntry(editedEducation));

        // all fields different
        assertFalse(NUS_EDUCATION.isSameEntry(WORK_FACEBOOK));
    }

    @Test
    public void equalsAndToString() {

        // same object -> returns true
        assertTrue(WORK_FACEBOOK.equals(WORK_FACEBOOK));
        assertTrue(NUS_EDUCATION.equals(NUS_EDUCATION));

        // different object but same fields
        assertTrue(NUS_EDUCATION.equals(new EntryBuilder(NUS_EDUCATION).build()));

        // null -> returns false
        assertFalse(WORK_FACEBOOK.equals(null));
        assertFalse(NUS_EDUCATION.equals(null));

        // different category -> returns false
        ResumeEntry editedWork = new EntryBuilder(WORK_FACEBOOK).withCategory("education").build();
        assertFalse(WORK_FACEBOOK.equals(editedWork));
        ResumeEntry editedEducation = new EntryBuilder(NUS_EDUCATION).withCategory("work").build();
        assertFalse(NUS_EDUCATION.equals(editedEducation));

        // all fields different
        assertFalse(NUS_EDUCATION.equals(WORK_FACEBOOK));

        // test toString() of a majorEntry
        assertTrue(WORK_FACEBOOK.toString()
                .equals(" Category: work Title: Facebook SubHeader: software engineering intern"
                        + " Duration: 2010 - 2013 Tags: [java]"));

        // test toString of a minorEntry which does not contain entryInfo
        assertTrue(TypicalEntrys.AWARD_WITH_NO_ENTRYINFO_NO_DESC.toString()
                .equals(" Category: Awards Title:  SubHeader:  Duration:  Tags: "));
    }


}
