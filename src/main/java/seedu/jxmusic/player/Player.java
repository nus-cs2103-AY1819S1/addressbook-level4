package seedu.jxmusic.player;

import javafx.util.Duration;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * The API of the Player component.
 */
public interface Player {
    void play();
    void play(Playlist playlist);
    void play(Track track);
    void stop();
    void pause();
    void seek(Duration time);
}
