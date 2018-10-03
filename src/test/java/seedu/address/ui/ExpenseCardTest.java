package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.ExpenseCardHandle;
import seedu.address.model.expense.Person;
import seedu.address.testutil.PersonBuilder;

public class ExpenseCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        ExpenseCard expenseCard = new ExpenseCard(personWithNoTags, 1);
        uiPartRule.setUiPart(expenseCard);
        assertCardDisplay(expenseCard, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        expenseCard = new ExpenseCard(personWithTags, 2);
        uiPartRule.setUiPart(expenseCard);
        assertCardDisplay(expenseCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        ExpenseCard expenseCard = new ExpenseCard(person, 0);

        // same expense, same index -> returns true
        ExpenseCard copy = new ExpenseCard(person, 0);
        assertTrue(expenseCard.equals(copy));

        // same object -> returns true
        assertTrue(expenseCard.equals(expenseCard));

        // null -> returns false
        assertFalse(expenseCard.equals(null));

        // different types -> returns false
        assertFalse(expenseCard.equals(0));

        // different expense, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(expenseCard.equals(new ExpenseCard(differentPerson, 0)));

        // same expense, different index -> returns false
        assertFalse(expenseCard.equals(new ExpenseCard(person, 1)));
    }

    /**
     * Asserts that {@code expenseCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ExpenseCard expenseCard, Person expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        ExpenseCardHandle expenseCardHandle = new ExpenseCardHandle(expenseCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", expenseCardHandle.getId());

        // verify expense details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, expenseCardHandle);
    }
}
