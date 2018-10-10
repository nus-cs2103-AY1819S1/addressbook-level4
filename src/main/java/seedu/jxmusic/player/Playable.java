package seedu.jxmusic.player;

import javafx.util.Duration;

/**
 * Playable defines functions that is used by Player
 */
public interface Playable {
    void play();
    void pause();
    void stop();
    void seek(Duration time);
}
