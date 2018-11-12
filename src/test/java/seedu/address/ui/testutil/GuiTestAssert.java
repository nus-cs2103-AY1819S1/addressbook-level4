package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.VolunteerCardHandle;
import guitests.guihandles.VolunteerListPanelHandle;
import seedu.address.model.volunteer.Volunteer;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(VolunteerCardHandle expectedCard, VolunteerCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedVolunteer}.
     */
    public static void assertCardDisplaysVolunteer(Volunteer expectedVolunteer, VolunteerCardHandle actualCard) {
        assertEquals(expectedVolunteer.getName().fullName, actualCard.getName());
        assertEquals(expectedVolunteer.getPhone().value, actualCard.getPhone());
        assertEquals(expectedVolunteer.getEmail().value, actualCard.getEmail());
        assertEquals(expectedVolunteer.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code volunteerListPanelHandle} displays the details of {@code volunteers} correctly
     * and in the correct order.
     */
    public static void assertListMatching(VolunteerListPanelHandle volunteerListPanelHandle, Volunteer... volunteers) {
        for (int i = 0; i < volunteers.length; i++) {
            volunteerListPanelHandle.navigateToCard(i);
            assertCardDisplaysVolunteer(volunteers[i], volunteerListPanelHandle.getVolunteerCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code volunteerListPanelHandle} displays the details of {@code volunteers}
     * correctly and in the correct order.
     */
    public static void assertListMatching(VolunteerListPanelHandle volunteerListPanelHandle,
                                          List<Volunteer> volunteers) {
        assertListMatching(volunteerListPanelHandle, volunteers.toArray(new Volunteer[0]));
    }

    /**
     * Asserts the size of the list in {@code volunteerListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(VolunteerListPanelHandle volunteerListPanelHandle, int size) {
        int numberOfPeople = volunteerListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
