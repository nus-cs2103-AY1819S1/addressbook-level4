package seedu.jxmusic.player;

import javafx.util.Duration;

/**
 * Playable defines functions that is used by Player
 */
public interface Playable {
    /**
     * Status of Playable
     * {@code PLAYING}, {@code PAUSED}, {@code STOPPED} represents the respective MediaPlayer statuses
     * {@code UNINITIALIZED} means player has yet to play any track/playlist
     * {@code ERROR} represents other MediaPlayer statuses since they are unused in our app
     */
    enum Status {
        PLAYING, PAUSED, STOPPED, UNINITIALIZED, ERROR
    }
    Status getStatus();
    void play(boolean unpause);
    void pause();
    void stop();
    void seek(Duration time);
}
