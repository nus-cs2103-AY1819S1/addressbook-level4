package seedu.jxmusic.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PlaylistCardHandle;
import guitests.guihandles.PlaylistListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TrackCardHandle;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertPlaylistCardEquals(PlaylistCardHandle expectedCard, PlaylistCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getTracks(), actualCard.getTracks());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertTrackCardEquals(TrackCardHandle expectedCard, TrackCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPlaylist}.
     */
    public static void assertCardDisplaysPlaylist(Playlist expectedPlaylist, PlaylistCardHandle actualCard) {
        assertEquals(expectedPlaylist.getName().nameString, actualCard.getName());
        assertEquals(expectedPlaylist.getTracks().stream().map(track -> track.getFileNameWithoutExtension())
                        .collect(Collectors.toList()), actualCard.getTracks());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTrack}.
     */
    public static void assertCardDisplaysTrack(Track expectedTrack, TrackCardHandle actualCard) {
        assertEquals(expectedTrack.getFileName(), actualCard.getName() + ".mp3");
    }

    /**
     * Asserts that the list in {@code playlistListPanelHandle} displays the details of {@code playlists} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PlaylistListPanelHandle playlistListPanelHandle, Playlist... playlists) {
        for (int i = 0; i < playlists.length; i++) {
            playlistListPanelHandle.navigateToCard(i);
            assertCardDisplaysPlaylist(playlists[i], playlistListPanelHandle.getPlaylistCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code playlistListPanelHandle} displays the details of {@code playlists} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PlaylistListPanelHandle playlistListPanelHandle, List<Playlist> playlists) {
        assertListMatching(playlistListPanelHandle, playlists.toArray(new Playlist[0]));
    }

    /**
     * Asserts the size of the list in {@code playlistListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PlaylistListPanelHandle playlistListPanelHandle, int size) {
        int numberOfPlaylists = playlistListPanelHandle.getListSize();
        assertEquals(size, numberOfPlaylists);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
