package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        TaskCard personCard = new TaskCard(personWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        personCard = new TaskCard(personWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        TaskCard personCard = new TaskCard(person, 0);

        // same person, same index -> returns true
        TaskCard copy = new TaskCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new TaskCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new TaskCard(person, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TaskCard personCard, Person expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}
