package seedu.superta.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.superta.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.superta.model.student.Student;
import seedu.superta.testutil.StudentBuilder;

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

        PersonCardHandle personCardHandle = new PersonCardHandle(studentCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify student details are displayed correctly
        assertCardDisplaysPerson(expectedStudent, personCardHandle);
    }
}
