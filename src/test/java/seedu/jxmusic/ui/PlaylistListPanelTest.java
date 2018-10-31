package seedu.jxmusic.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.jxmusic.testutil.EventsUtil.postNow;
import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_SECOND_PLAYLIST;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalPlaylistList;
import static seedu.jxmusic.ui.testutil.GuiTestAssert.assertCardDisplaysPlaylist;
import static seedu.jxmusic.ui.testutil.GuiTestAssert.assertPlaylistCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.PlaylistCardHandle;
import guitests.guihandles.PlaylistListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jxmusic.commons.events.ui.JumpToListRequestEvent;
import seedu.jxmusic.commons.util.FileUtil;
import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.storage.JsonFileStorage;

public class PlaylistListPanelTest extends GuiUnitTest {
    private static final ObservableList<Playlist> TYPICAL_PLAYLISTS =
            FXCollections.observableList(getTypicalPlaylistList());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToListRequestEvent(INDEX_SECOND_PLAYLIST);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private PlaylistListPanelHandle playlistListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_PLAYLISTS);

        for (int i = 0; i < TYPICAL_PLAYLISTS.size(); i++) {
            playlistListPanelHandle.navigateToCard(TYPICAL_PLAYLISTS.get(i));
            Playlist expectedPlaylist = TYPICAL_PLAYLISTS.get(i);
            PlaylistCardHandle actualCard = playlistListPanelHandle.getPlaylistCardHandle(i);

            assertCardDisplaysPlaylist(expectedPlaylist, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_PLAYLISTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PlaylistCardHandle expectedPlaylist = playlistListPanelHandle.getPlaylistCardHandle(
                INDEX_SECOND_PLAYLIST.getZeroBased());
        PlaylistCardHandle selectedPlaylist = playlistListPanelHandle.getHandleToSelectedCard();
        assertPlaylistCardEquals(expectedPlaylist, selectedPlaylist);
    }

    /**
     * Verifies that creating and deleting large number of playlists in {@code PlaylistListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Playlist> backingList = createBackingList(1000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of playlist cards exceeded time limit");
    }

    /**
     * Returns a list of playlists containing {@code playlistCount} playlists that is used to populate the
     * {@code PlaylistListPanel}.
     */
    private ObservableList<Playlist> createBackingList(int playlistCount) throws Exception {
        Path jsonFile = createJsonFileWithPlaylists(playlistCount);
        Library library = JsonFileStorage.loadDataFromFile(jsonFile);
        return FXCollections.observableArrayList(library.getPlaylistList());
    }

    /**
     * Returns a .json file containing {@code playlistCount} playlists.
     * This file will be deleted when the JVM terminates.
     */
    private Path createJsonFileWithPlaylists(int playlistCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n\"playlists\": [\n");
        for (int i = 0; i < playlistCount; i++) {
            builder.append("{\n");
            builder.append("\"name\": \"playlist").append(i).append(" name\",\n");
            builder.append("\"tracks\": [\n" + "\"Marbles.mp3\",\n" + "\"SOS Morse Code.mp3\"\n" + "]\n");
            builder.append("},\n");
        }
        builder.deleteCharAt(builder.length() - 2); // delete last comma
        builder.append("]\n}\n");

        Path manyPlaylistsFile = Paths.get(TEST_DATA_FOLDER + "manyPlaylists.json");
        FileUtil.createFile(manyPlaylistsFile);
        FileUtil.writeToFile(manyPlaylistsFile, builder.toString());
        manyPlaylistsFile.toFile().deleteOnExit();
        return manyPlaylistsFile;
    }

    /**
     * Initializes {@code playlistListPanelHandle} with a {@code PlaylistListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PlaylistListPanel}.
     */
    private void initUi(ObservableList<Playlist> backingList) {
        PlaylistListPanel playlistListPanel = new PlaylistListPanel(backingList);
        uiPartRule.setUiPart(playlistListPanel);

        playlistListPanelHandle = new PlaylistListPanelHandle(getChildNode(playlistListPanel.getRoot(),
                PlaylistListPanelHandle.PLAYLIST_LIST_VIEW_ID));
    }
}
