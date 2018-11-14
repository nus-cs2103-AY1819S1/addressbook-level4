package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysOccasion;

import org.junit.Test;

import guitests.guihandles.OccasionCardHandle;
import seedu.address.model.occasion.Occasion;
import seedu.address.testutil.OccasionBuilder;

public class OccasionCardTest extends GuiUnitTest {
    @Test
    public void display() {
        // no tags
        Occasion occasionWithNoTags = new OccasionBuilder().withTags(new String[0]).build();
        OccasionCard occasionCard = new OccasionCard(occasionWithNoTags, 1);
        uiPartRule.setUiPart(occasionCard);
        assertCardDisplay(occasionCard, occasionWithNoTags, 1);

        // with tags
        Occasion occasionWithTags = new OccasionBuilder().build();
        occasionCard = new OccasionCard(occasionWithTags, 2);
        uiPartRule.setUiPart(occasionCard);
        assertCardDisplay(occasionCard, occasionWithTags, 2);
    }

    @Test
    public void equals() {
        Occasion occasion = new OccasionBuilder().build();
        OccasionCard occasionCard = new OccasionCard(occasion, 0);

        // same person, same index -> returns true
        OccasionCard copy = new OccasionCard(occasion, 0);
        assertTrue(occasionCard.equals(copy));

        // same object -> returns true
        assertTrue(occasionCard.equals(occasionCard));

        // null -> returns false
        assertFalse(occasionCard.equals(null));

        // different types -> returns false
        assertFalse(occasionCard.equals(0));

        // different person, same index -> returns false
        Occasion differentOccasion = new OccasionBuilder().withOccasionName("differentName").build();
        assertFalse(occasionCard.equals(new OccasionCard(differentOccasion, 0)));

        // same person, different index -> returns false
        assertFalse(occasionCard.equals(new OccasionCard(occasion, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(OccasionCard occasionCard, Occasion expectedOccasion, int expectedId) {
        guiRobot.pauseForHuman();

        OccasionCardHandle occasionCardHandle = new OccasionCardHandle(occasionCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", occasionCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysOccasion(expectedOccasion, occasionCardHandle);
    }

}
