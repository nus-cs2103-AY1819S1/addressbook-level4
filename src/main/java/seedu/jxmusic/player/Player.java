package seedu.jxmusic.player;

import javafx.util.Duration;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * The API of the Player component.
 */
public interface Player {
    /**
     * Continues playing from a pause/stop
     */
    void play();

    /**
     * Plays a playlist
     */
    void play(Playlist playlist);

    /**
     * Plays a track
     */
    void play(Track track);

    /**
     * Stops the playing playlist/track
     */
    void stop();


    /**
     * Pauses the playing track
     */
    void pause();


    /**
     * Seeks to the specified {@code time} in the playing track
     */
    void seek(Duration time);


    /**
     * Returns the status of the player
     */
    Playable.Status getStatus();
}
