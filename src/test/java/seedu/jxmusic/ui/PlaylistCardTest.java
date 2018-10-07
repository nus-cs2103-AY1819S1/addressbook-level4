package seedu.jxmusic.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.jxmusic.testutil.PlaylistBuilder;

public class PlaylistCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PlaylistBuilder().withTags(new String[0]).build();
        PlaylistCard playlistCard = new PlaylistCard(personWithNoTags, 1);
        uiPartRule.setUiPart(playlistCard);
        assertCardDisplay(playlistCard, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PlaylistBuilder().build();
        playlistCard = new PlaylistCard(personWithTags, 2);
        uiPartRule.setUiPart(playlistCard);
        assertCardDisplay(playlistCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Person person = new PlaylistBuilder().build();
        PlaylistCard playlistCard = new PlaylistCard(person, 0);

        // same playlist, same index -> returns true
        PlaylistCard copy = new PlaylistCard(person, 0);
        assertTrue(playlistCard.equals(copy));

        // same object -> returns true
        assertTrue(playlistCard.equals(playlistCard));

        // null -> returns false
        assertFalse(playlistCard.equals(null));

        // different types -> returns false
        assertFalse(playlistCard.equals(0));

        // different playlist, same index -> returns false
        Person differentPerson = new PlaylistBuilder().withName("differentName").build();
        assertFalse(playlistCard.equals(new PlaylistCard(differentPerson, 0)));

        // same playlist, different index -> returns false
        assertFalse(playlistCard.equals(new PlaylistCard(person, 1)));
    }

    /**
     * Asserts that {@code playlistCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PlaylistCard playlistCard, Person expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(playlistCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify playlist details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}
