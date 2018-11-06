package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCca;

import org.junit.Test;

import guitests.guihandles.CcaCardHandle;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;
import seedu.address.testutil.CcaBuilder;
import seedu.address.testutil.PersonBuilder;

//@@author ericyjw
public class CcaCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Cca cca = new CcaBuilder().build();
        CcaCard ccaCard = new CcaCard(cca, 1);
        uiPartRule.setUiPart(ccaCard);
        assertCardDisplay(ccaCard, cca, 1);

    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        // same person, same index -> returns true
        PersonCard copy = new PersonCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(person, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CcaCard ccaCard, Cca expectedCca, int expectedId) {
        guiRobot.pauseForHuman();

        CcaCardHandle ccaCardHandle = new CcaCardHandle(ccaCard.getRoot());

        // verify person details are displayed correctly
        assertCardDisplaysCca(expectedCca, ccaCardHandle);
    }
}
