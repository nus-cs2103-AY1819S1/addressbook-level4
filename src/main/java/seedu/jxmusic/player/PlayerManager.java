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
        System.out.println("jxmusicplayer play from pause");
        assert currentlyPlaying != null;
        currentlyPlaying.play(true);
    }

    @Override
    public void play(Playlist playlist) {
        System.out.println("jxmusicplayer play playlist");
        currentlyPlaying = new PlayablePlaylist(playlist);
        currentlyPlaying.play(false);
    }

    @Override
    public void play(Track track) {
        System.out.println("jxmusicplayer play track");
        currentlyPlaying = new PlayableTrack(track);
        currentlyPlaying.play(false);
    }

    @Override
    public void pause() {
        System.out.println("jxmusicplayer pause");
        currentlyPlaying.pause();
    }

    @Override
    public void stop() {
        System.out.println("jxmusicplayer stop");
        currentlyPlaying.stop();
    }

    @Override
    public void seek(Duration time) {
        System.out.println("jxmusicplayer seek to " + time.toSeconds() + " second(s)");
        currentlyPlaying.seek(time);
    }
}
