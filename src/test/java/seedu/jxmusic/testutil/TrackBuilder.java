package seedu.jxmusic.testutil;

import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Track;

/**
 * A utility class to help with building track objects.
 */
public class TrackBuilder {

    public static final String DEFAULT_NAME = "SOS Morse Code";

    private Name trackFileName;


    public TrackBuilder() {
        trackFileName = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the TrackBuilder with the data of {@code trackToCopy}.
     */
    public TrackBuilder(Track trackToCopy) {
        trackFileName = new Name(trackToCopy.getFileName());
    }

    /**
     * Sets the {@code trackFileName} of the {@code track} that we are building.
     */
    public TrackBuilder withName(String name) {
        this.trackFileName = new Name(name);
        return this;
    }


    /**
     * Builds the Track
     */
    public Track build() {
        Track track = new Track(trackFileName);
        return track;
    }
}
