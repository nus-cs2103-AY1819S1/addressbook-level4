package seedu.address.ui;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
//
//import org.junit.Test;
//
//import guitests.guihandles.PersonCardHandle;
//import seedu.address.model.person.Person;
//import seedu.address.testutil.PersonBuilder;

//public class CarparkCardTest extends GuiUnitTest {

public class CarparkCardTest {
//    @Test
//    public void display() {
//        // no tags
//        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
//        CarparkCard carparkCard = new CarparkCard(personWithNoTags, 1);
//        uiPartRule.setUiPart(carparkCard);
//        assertCardDisplay(carparkCard, personWithNoTags, 1);
//
//        // with tags
//        Person personWithTags = new PersonBuilder().build();
//        carparkCard = new CarparkCard(personWithTags, 2);
//        uiPartRule.setUiPart(carparkCard);
//        assertCardDisplay(carparkCard, personWithTags, 2);
//    }
//
//    @Test
//    public void equals() {
//        Person person = new PersonBuilder().build();
//        CarparkCard carparkCard = new CarparkCard(person, 0);
//
//        // same carpark, same index -> returns true
//        CarparkCard copy = new CarparkCard(person, 0);
//        assertTrue(carparkCard.equals(copy));
//
//        // same object -> returns true
//        assertTrue(carparkCard.equals(carparkCard));
//
//        // null -> returns false
//        assertFalse(carparkCard.equals(null));
//
//        // different types -> returns false
//        assertFalse(carparkCard.equals(0));
//
//        // different carpark, same index -> returns false
//        Person differentPerson = new PersonBuilder().withName("differentName").build();
//        assertFalse(carparkCard.equals(new CarparkCard(differentPerson, 0)));
//
//        // same carpark, different index -> returns false
//        assertFalse(carparkCard.equals(new CarparkCard(person, 1)));
//    }
//
//    /**
//     * Asserts that {@code carparkCard} displays the details of {@code expectedPerson} correctly and matches
//     * {@code expectedId}.
//     */
//    private void assertCardDisplay(CarparkCard carparkCard, Person expectedPerson, int expectedId) {
//        guiRobot.pauseForHuman();
//
//        PersonCardHandle personCardHandle = new PersonCardHandle(carparkCard.getRoot());
//
//        // verify id is displayed correctly
//        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());
//
//        // verify carpark details are displayed correctly
//        assertCardDisplaysPerson(expectedPerson, personCardHandle);
//    }
}
