package tutorhelper.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tutorhelper.ui.testutil.GuiTestAssert.assertCardDisplaysStudent;

import org.junit.Test;

import guitests.guihandles.StudentCardHandle;
import tutorhelper.model.student.Student;
import tutorhelper.testutil.StudentBuilder;

public class StudentCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Student studentWithNoTags = new StudentBuilder().withTags(new String[0]).build();
        StudentCard studentCard = new StudentCard(studentWithNoTags, 1);
        uiPartRule.setUiPart(studentCard);
        assertCardDisplay(studentCard, studentWithNoTags, 1);

        // with tags
        Student studentWithTags = new StudentBuilder().build();
        studentCard = new StudentCard(studentWithTags, 2);
        uiPartRule.setUiPart(studentCard);
        assertCardDisplay(studentCard, studentWithTags, 2);
    }

    @Test
    public void equals() {
        Student student = new StudentBuilder().build();
        StudentCard studentCard = new StudentCard(student, 0);

        // same student, same index -> returns true
        StudentCard copy = new StudentCard(student, 0);
        assertTrue(studentCard.equals(copy));

        // same object -> returns true
        assertTrue(studentCard.equals(studentCard));

        // null -> returns false
        assertFalse(studentCard.equals(null));

        // different types -> returns false
        assertFalse(studentCard.equals(0));

        // different student, same index -> returns false
        Student differentStudent = new StudentBuilder().withName("differentName").build();
        assertFalse(studentCard.equals(new StudentCard(differentStudent, 0)));

        // same student, different index -> returns false
        assertFalse(studentCard.equals(new StudentCard(student, 1)));
    }

    /**
     * Asserts that {@code studentCard} displays the details of {@code expectedStudent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(StudentCard studentCard, Student expectedStudent, int expectedId) {
        guiRobot.pauseForHuman();

        StudentCardHandle studentCardHandle = new StudentCardHandle(studentCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", studentCardHandle.getId());

        // verify student details are displayed correctly
        assertCardDisplaysStudent(expectedStudent, studentCardHandle);
    }
}
