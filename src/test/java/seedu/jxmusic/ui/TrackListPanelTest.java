package seedu.jxmusic.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.jxmusic.testutil.EventsUtil.postNow;
import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_SECOND_TRACK;
import static seedu.jxmusic.testutil.TypicalTrackList.getTypicalTrackList;
import static seedu.jxmusic.ui.testutil.GuiTestAssert.assertCardDisplaysTrack;
import static seedu.jxmusic.ui.testutil.GuiTestAssert.assertTrackCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

import guitests.guihandles.TrackCardHandle;
import guitests.guihandles.TrackListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jxmusic.commons.events.ui.JumpToListRequestEvent;
import seedu.jxmusic.commons.util.FileUtil;
import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.storage.JsonFileStorage;

public class TrackListPanelTest extends GuiUnitTest {
    private static final ObservableList<Track> TYPICAL_TRACKLIST =
            FXCollections.observableList(getTypicalTrackList());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToListRequestEvent(INDEX_SECOND_TRACK);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private TrackListPanelHandle trackListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_TRACKLIST);

        for (int i = 0; i < TYPICAL_TRACKLIST.size(); i++) {
            trackListPanelHandle.navigateToCard(TYPICAL_TRACKLIST.get(i));
            Track expectedTrack = TYPICAL_TRACKLIST.get(i);
            TrackCardHandle actualCard = trackListPanelHandle.getTrackCardHandle(i);

            assertCardDisplaysTrack(expectedTrack, actualCard);
            assertEquals(Integer.toString(i + 1) + ".", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_TRACKLIST);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        TrackCardHandle expectedTrack = trackListPanelHandle.getTrackCardHandle(
                INDEX_SECOND_TRACK.getZeroBased());
        TrackCardHandle selectedTrack = trackListPanelHandle.getHandleToSelectedCard();
        assertTrackCardEquals(expectedTrack, selectedTrack);
    }

    /**
     * Verifies that creating and deleting large number of tracks in {@code TrackListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Track> backingList = createBackingList(1000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of playlist cards exceeded time limit");
    }

    /**
     * Returns a list of tracks containing {@code trackCount} tracks that is used to populate the
     * {@code TrackListPanel}.
     */
    private ObservableList<Track> createBackingList(int trackCount) throws Exception {
        ObservableList<Track> result = FXCollections.observableArrayList(new ArrayList<>());
        for (int i = 0; i < trackCount; i++) {
            result.add(new Track(new Name("Marbles")));
        }
//        Path jsonFile = createJsonFileWithTracks(trackCount);
//        Library library = JsonFileStorage.loadDataFromFile(jsonFile);
//        return library.getObservableTrackList());
        return result;
    }

    /**
     * Returns a .json file containing {@code trackCount} tracks.
     * This file will be deleted when the JVM terminates.
     */
    private Path createJsonFileWithTracks(int trackCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n\"tracks\": [\n");
        for (int i = 0; i < trackCount; i++) {
            builder.append("{\n");
            builder.append("\"name\": \"Marbles\"\n");
            builder.append("},\n");
        }
        builder.deleteCharAt(builder.length() - 2); // delete last comma
        builder.append("]\n}\n");

        Path manyTracksFile = Paths.get(TEST_DATA_FOLDER + "manyTracks.json");
        FileUtil.createFile(manyTracksFile);
        FileUtil.writeToFile(manyTracksFile, builder.toString());
        manyTracksFile.toFile().deleteOnExit();
        return manyTracksFile;
    }

    /**
     * Initializes {@code trackListPanelHandle} with a {@code TrackListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code TrackListPanel}.
     */
    private void initUi(ObservableList<Track> backingList) {
        TrackListPanel trackListPanel = new TrackListPanel(backingList);
        uiPartRule.setUiPart(trackListPanel);

        trackListPanelHandle = new TrackListPanelHandle(getChildNode(trackListPanel.getRoot(),
                TrackListPanelHandle.TRACK_LIST_VIEW_ID));
    }

}
