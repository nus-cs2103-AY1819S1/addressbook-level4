package seedu.jxmusic.model;

import static seedu.jxmusic.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.jxmusic.commons.core.index.Index;

/**
 * Represents a Playlist in the jxmusic book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Playlist {

    // Identity fields
    private final Name name;

    // Data fields
    private final List<Track> tracks;
    private int size;

    /**
     * Only name field must be present. List of tracks to be added later.
     */
    public Playlist(Name name) {
        requireAllNonNull(name);
        this.name = name;
        this.tracks = new ArrayList<>();
        this.size = 0;
    }

    /**
     * Constructs a Playlist along with its list of tracks
     * @param name name of the playlist, cannot be null
     * @param tracks list of tracks of the playlist, cannot be null
     */
    public Playlist(Name name, List<Track> tracks) {
        requireAllNonNull(name);
        this.name = name;
        this.tracks = tracks;
        this.size = this.tracks.size();
    }

    public Name getName() {
        return name;
    }

    /**
     * Returns the list of tracks in the playlist
     */
    public List<Track> getTracks() {
        return Collections.unmodifiableList(tracks);
    }

    /**
     * Returns the track of index in the playlist
     */
    public Track get(int index) throws ArrayIndexOutOfBoundsException {
        return Collections.unmodifiableList(tracks).get(index);
    }

    /**
     * Adds a track into the playlist
     * @param track to be added to the playlist, must not be null
     */
    public void addTrack(Track track) {
        if (track == null) {
            throw new NullPointerException("Track must not be null");
        }
        tracks.add(track);
        setSize(getSize() + 1);
    }

    /**
     * Deletes a track from the playlist
     * @param track to be deleted from the playlist, must not be null
     */
    public boolean deleteTrack(Track track) {
        if (track == null) {
            throw new NullPointerException("Track must not be null");
        }
        if (tracks.remove(track)) {
            setSize(getSize() - 1);
            return true;
        }
        return false;
    }

    /**
     * Returns true if both playlists have the same name
     * This defines a weaker notion of equality between two playlists.
     */
    public boolean isSamePlaylist(Playlist otherPlaylist) {
        if (otherPlaylist == this) {
            return true;
        }

        return otherPlaylist != null
                && otherPlaylist.getName().equals(getName());
    }

    /**
     * Returns a copy of this playlist
     */
    public Playlist copy() {
        List<Track> tracks = this.getTracks();
        Name nameCopy = this.getName();
        Playlist copy = new Playlist(nameCopy);
        for (Track track : tracks) {
            copy.addTrack(track);
        }
        return copy;
    }

    /**
     * Checks if playlist has a specific track
     * @param targetTrack Track to check for
     * @return true if track is in playlist
     */
    public boolean hasTrack(Track targetTrack) {
        for (Track track : getTracks()) {
            if (track.getFileName().equalsIgnoreCase(targetTrack.getFileName())) {
                return true;
            }
        }
        return false;
    }

    public Index getTrackIndex(Track targetTrack) {
        int index = 0;
        for (Track track : getTracks()) {
            if (track.equals(targetTrack)) {
                return Index.fromZeroBased(index);
            }
            index++;
        }
        return Index.fromOneBased(0);
    }

    private void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns number of tracks in this playlist
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns true if there are no tracks in this playlist
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Returns true if both playlists have the same name and tracks.
     * This defines a stronger notion of equality between two playlists.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Playlist)) {
            return false;
        }

        Playlist otherPlaylist = (Playlist) other;
        return otherPlaylist.getName().equals(getName())
                && otherPlaylist.getTracks().equals(getTracks());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tracks);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append('\n')
                .append("Tracks: \n");
        tracks.forEach(t -> {
            builder.append(t)
                    .append('\n');
        });
        return builder.toString();
    }

}
