package seedu.jxmusic.model;

import java.util.List;

import seedu.jxmusic.commons.util.CollectionUtil;

/**
 * Represents the library in JxMusic
 */
public class Library1 {
    public static final String LIBRARYDIR = "library/";

    // tracks are mp3 files in /library folder
    // so it can change if new file added into folder when program is running
    private List<Track> tracks;

    // playlist1s are from library.json file
    private List<Playlist1> playlist1s;

    public Library1() {

    }

    public Library1(List<Track> tracks, List<Playlist1> playlist1s) {
        CollectionUtil.requireAllNonNull(tracks, playlist1s);
        this.tracks = tracks;
        this.playlist1s = playlist1s;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public List<Playlist1> getPlaylist1s() {
        return playlist1s;
    }

    public void setPlaylist1s(List<Playlist1> playlist1s) {
        this.playlist1s = playlist1s;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
