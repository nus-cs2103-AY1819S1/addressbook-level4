package seedu.jxmusic.player;

import java.util.logging.Logger;

import javafx.util.Duration;
import seedu.jxmusic.commons.core.ComponentManager;
import seedu.jxmusic.commons.core.LogsCenter;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * The actual implemented player to be used by Logic component
 */
public class PlayerManager extends ComponentManager implements Player {
    private static final Logger logger = LogsCenter.getLogger(PlayerManager.class);
    private static PlayerManager instance;
    private static Playable currentlyPlaying;

    private PlayerManager() {}

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

    @Override
    public void play() {
        logger.info("Continue from pause");
        if (currentlyPlaying != null) {
            currentlyPlaying.play(true);
        }
    }

    @Override
    public void play(Playlist playlist) {
        logger.info("Play playlist " + playlist.getName());
        if (currentlyPlaying != null) {
            currentlyPlaying.stop();
        }
        currentlyPlaying = new PlayablePlaylist(playlist);
        currentlyPlaying.play(false);
    }

    @Override
    public void play(Track track) {
        logger.info("Play track " + track.getFileNameWithoutExtension());
        if (currentlyPlaying != null) {
            currentlyPlaying.stop();
        }
        currentlyPlaying = new PlayableTrack(track);
        currentlyPlaying.play(false);
    }

    @Override
    public void pause() {
        logger.info("Pause");
        if (currentlyPlaying != null) {
            currentlyPlaying.pause();
        }
    }

    @Override
    public void stop() {
        logger.info("Stop");
        if (currentlyPlaying != null) {
            currentlyPlaying.stop();
        }
    }

    @Override
    public void seek(Duration time) {
        logger.info("Seek to " + time.toSeconds() + " second(s)");
        currentlyPlaying.seek(time);
    }

    @Override
    public Playable.Status getStatus() {
        if (currentlyPlaying == null) {
            return Playable.Status.UNINITIALIZED;
        }
        return currentlyPlaying.getStatus();
    }

}
