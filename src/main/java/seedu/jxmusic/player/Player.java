package seedu.jxmusic.player;

import javafx.util.Duration;

/**
 * The API of the Player component.
 */
public interface Player {
    void play(); // todo take in Playlist1 model as parameter
    // void play(Track track);
    void stop();
    void pause();
    void seek(Duration time);
}
