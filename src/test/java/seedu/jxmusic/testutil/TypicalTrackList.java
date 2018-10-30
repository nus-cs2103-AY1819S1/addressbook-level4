package seedu.jxmusic.testutil;

import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_BELL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_HAIKEI;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_IHOJIN;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_MARBLES;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_SOS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Track;

/**
 * A utility class containing a list of {@code Track} objects to be used in tests.
 */
public class TypicalTrackList {

    // Track's details found in {@code CommandTestUtil}
    public static final Track SOS = new TrackBuilder().withName(VALID_TRACK_NAME_SOS).build();
    public static final Track BELL = new TrackBuilder().withName(VALID_TRACK_NAME_BELL).build();
    public static final Track MARBLES = new TrackBuilder().withName(VALID_TRACK_NAME_MARBLES).build();
    public static final Track HAIKEI = new TrackBuilder().withName(VALID_TRACK_NAME_HAIKEI).build();
    public static final Track IHOJIN = new TrackBuilder().withName(VALID_TRACK_NAME_IHOJIN).build();

    private TypicalTrackList() {} // prevents instantiation

    /**
     * Returns an {@code Library} with all the typical lists of tracks.
     */
    public static Library getTypicalLibrary() {
        Library library = new Library();
        for (Track track: getTypicalTrackList()) {
            library.addTrack(track);
        }
        return library;
    }

    public static List<Track> getTypicalTrackList() {
        return new ArrayList<>(Arrays.asList(SOS, BELL, MARBLES, HAIKEI, IHOJIN));
    }
}
