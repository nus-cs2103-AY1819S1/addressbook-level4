package seedu.address.model.attendance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_B;
import static seedu.address.testutil.TypicalAttendance.ATTENDANCE_A;
import static seedu.address.testutil.TypicalAttendance.ATTENDANCE_B;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.DuplicateAttendanceException;
import seedu.address.testutil.AttendanceBuilder;


public class UniqueAttendanceListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueAttendanceList uniqueAttendanceList = new UniqueAttendanceList();

    @Test
    public void contains_nullLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAttendanceList.contains(null);
    }

    @Test
    public void contains_lessonNotInList_returnsFalse() {
        assertFalse(uniqueAttendanceList.contains(ATTENDANCE_A));
    }

    @Test
    public void contains_lessonInList_returnsTrue() {
        uniqueAttendanceList.add(ATTENDANCE_A);
        assertTrue(uniqueAttendanceList.contains(ATTENDANCE_A));
    }

    @Test
    public void contains_lessonsWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAttendanceList.add(ATTENDANCE_A);
        Attendance editedA = new AttendanceBuilder(ATTENDANCE_A).withDate(VALID_DATE_B)
                .build();
        assertTrue(uniqueAttendanceList.contains(editedA));
    }

    @Test
    public void add_nullLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAttendanceList.add(null);
    }

    @Test
    public void add_duplicateLesson_throwsDuplicateLessonException() {
        uniqueAttendanceList.add(ATTENDANCE_A);
        thrown.expect(DuplicateAttendanceException.class);
        uniqueAttendanceList.add(ATTENDANCE_A);
    }

    @Test
    public void setLesson_nullTargetLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAttendanceList.setAttendance(null, ATTENDANCE_A);
    }

    @Test
    public void setLesson_nullEditedLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAttendanceList.setAttendance(ATTENDANCE_A, null);
    }

    @Test
    public void setLesson_targetLessonNotInList_throwsLessonNotFoundException() {
        thrown.expect(AttendanceNotFoundException.class);
        uniqueAttendanceList.setAttendance(ATTENDANCE_A, ATTENDANCE_A);
    }

    @Test
    public void setLesson_editedLessonIsSameLesson_success() {
        uniqueAttendanceList.add(ATTENDANCE_A);
        uniqueAttendanceList.setAttendance(ATTENDANCE_A, ATTENDANCE_A);
        UniqueAttendanceList expectedUniqueAttendanceList = new UniqueAttendanceList();
        expectedUniqueAttendanceList.add(ATTENDANCE_A);
        assertEquals(expectedUniqueAttendanceList, uniqueAttendanceList);
    }

    @Test
    public void setLesson_editedLessonHasSameIdentity_success() {
        uniqueAttendanceList.add(ATTENDANCE_A);
        Attendance editedA = new AttendanceBuilder(ATTENDANCE_A).withDate(VALID_DATE_B)
                .build();
        uniqueAttendanceList.setAttendance(ATTENDANCE_A, editedA);
        UniqueAttendanceList expectedUniqueAttendanceList = new UniqueAttendanceList();
        expectedUniqueAttendanceList.add(editedA);
        assertEquals(expectedUniqueAttendanceList, uniqueAttendanceList);
    }

    @Test
    public void setLesson_editedLessonHasDifferentIdentity_success() {
        uniqueAttendanceList.add(ATTENDANCE_A);
        uniqueAttendanceList.setAttendance(ATTENDANCE_A, ATTENDANCE_B);
        UniqueAttendanceList expectedUniqueAttendanceList = new UniqueAttendanceList();
        expectedUniqueAttendanceList.add(ATTENDANCE_B);
        assertEquals(expectedUniqueAttendanceList, uniqueAttendanceList);
    }

    @Test
    public void setLesson_editedLessonHasNonUniqueIdentity_throwsDuplicateLessonException() {
        uniqueAttendanceList.add(ATTENDANCE_A);
        uniqueAttendanceList.add(ATTENDANCE_B);
        thrown.expect(DuplicateAttendanceException.class);
        uniqueAttendanceList.setAttendance(ATTENDANCE_A, ATTENDANCE_B);
    }

    @Test
    public void remove_nullLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAttendanceList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsAssignmentNotFoundException() {
        thrown.expect(AttendanceNotFoundException.class);
        uniqueAttendanceList.remove(ATTENDANCE_A);
    }

    @Test
    public void remove_existingLesson_removesLesson() {
        uniqueAttendanceList.add(ATTENDANCE_A);
        uniqueAttendanceList.remove(ATTENDANCE_A);
        UniqueAttendanceList expectedUniqueAttendanceList = new UniqueAttendanceList();
        assertEquals(expectedUniqueAttendanceList, uniqueAttendanceList);
    }

    @Test
    public void setLessons_nullUniqueAttendanceList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAttendanceList.setAttendances((UniqueAttendanceList) null);
    }

    @Test
    public void setLessons_uniqueAttendanceList_replacesOwnListWithProvidedUniqueAttendanceList() {
        uniqueAttendanceList.add(ATTENDANCE_A);
        UniqueAttendanceList expectedUniqueAttendanceList = new UniqueAttendanceList();
        expectedUniqueAttendanceList.add(ATTENDANCE_B);
        uniqueAttendanceList.setAttendances(expectedUniqueAttendanceList);
        assertEquals(expectedUniqueAttendanceList, uniqueAttendanceList);
    }

    @Test
    public void setLessons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAttendanceList.setAttendances((List<Attendance>) null);
    }

    @Test
    public void setLessons_list_replacesOwnListWithProvidedList() {
        uniqueAttendanceList.add(ATTENDANCE_A);
        List<Attendance> personList = Collections.singletonList(ATTENDANCE_B);
        uniqueAttendanceList.setAttendances(personList);
        UniqueAttendanceList expectedUniqueAttendanceList = new UniqueAttendanceList();
        expectedUniqueAttendanceList.add(ATTENDANCE_B);
        assertEquals(expectedUniqueAttendanceList, uniqueAttendanceList);
    }

    @Test
    public void setLessons_listWithDuplicateLessons_throwsDuplicateAttendanceException() {
        List<Attendance> listWithDuplicateLessons = Arrays.asList(ATTENDANCE_A, ATTENDANCE_A);
        thrown.expect(DuplicateAttendanceException.class);
        uniqueAttendanceList.setAttendances(listWithDuplicateLessons);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueAttendanceList.asUnmodifiableObservableList().remove(0);
    }
}

