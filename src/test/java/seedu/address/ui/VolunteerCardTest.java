package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysVolunteer;

import org.junit.Test;

import guitests.guihandles.VolunteerCardHandle;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.VolunteerBuilder;

public class VolunteerCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Volunteer volunteerWithNoTags = new VolunteerBuilder().withTags(new String[0]).build();
        VolunteerCard volunteerCard = new VolunteerCard(volunteerWithNoTags, 1);
        uiPartRule.setUiPart(volunteerCard);
        assertCardDisplay(volunteerCard, volunteerWithNoTags, 1);

        // with tags
        Volunteer volunteerWithTags = new VolunteerBuilder().build();
        volunteerCard = new VolunteerCard(volunteerWithTags, 2);
        uiPartRule.setUiPart(volunteerCard);
        assertCardDisplay(volunteerCard, volunteerWithTags, 2);
    }

    @Test
    public void equals() {
        Volunteer volunteer = new VolunteerBuilder().build();
        VolunteerCard volunteerCard = new VolunteerCard(volunteer, 0);

        // same volunteer, same index -> returns true
        VolunteerCard copy = new VolunteerCard(volunteer, 0);
        assertTrue(volunteerCard.equals(copy));

        // same object -> returns true
        assertTrue(volunteerCard.equals(volunteerCard));

        // null -> returns false
        assertFalse(volunteerCard.equals(null));

        // different types -> returns false
        assertFalse(volunteerCard.equals(0));

        // different volunteer, same index -> returns false
        Volunteer differentVolunteer = new VolunteerBuilder().withName("differentName").build();
        assertFalse(volunteerCard.equals(new VolunteerCard(differentVolunteer, 0)));

        // same volunteer, different index -> returns false
        assertFalse(volunteerCard.equals(new VolunteerCard(volunteer, 1)));
    }

    /**
     * Asserts that {@code volunteerCard} displays the details of {@code expectedVolunteer} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(VolunteerCard volunteerCard, Volunteer expectedVolunteer, int expectedId) {
        guiRobot.pauseForHuman();

        VolunteerCardHandle volunteerCardHandle = new VolunteerCardHandle(volunteerCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", volunteerCardHandle.getId());

        // verify volunteer details are displayed correctly
        assertCardDisplaysVolunteer(expectedVolunteer, volunteerCardHandle);
    }
}
