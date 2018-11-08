package seedu.jxmusic.player;

import java.util.List;
import java.util.stream.Collectors;

import javafx.util.Duration;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Playlist;

/**
 * Playlist1 structure used by Player
 */
public class PlayablePlaylist implements Playable {
    private List<PlayableTrack> playableTrackList;
    private int currentIndex;
    private PlayableTrack current;
    private Playlist playlist;
    public PlayablePlaylist(Playlist playlist) {
        this.playlist = playlist;
        // get list of tracks in Playlist1 parameter and construct PlayableTracks
        // set playableTrackList with list of PlayableTrack
        this.playableTrackList = playlist.getTracks().stream()
                .map(PlayableTrack::new)
                .collect(Collectors.toList());
        for (int i = 0; i < playableTrackList.size() - 1; i++) { // iterate all except last one
            playableTrackList.get(i).setOnEndOfMedia(() -> {
                next();
            });
        }
        // this.playableTrackList = Arrays.asList(new PlayableTrack());
    }
    @Override
    public void play(boolean unpause) {
        System.out.println("playableplaylist play");
        if (current == null) {
            currentIndex = 0;
            current = playableTrackList.get(currentIndex);
        }
        current.play(unpause);
    }

    @Override
    public void pause() {
        System.out.println("playableplaylist pause");
        if (current == null) {
            currentIndex = 0;
            current = playableTrackList.get(currentIndex);
        }
        current.pause();
    }

    @Override
    public void stop() {
        System.out.println("playableplaylist stop");
        current.stop();
        currentIndex = 0;
        current = playableTrackList.get(currentIndex);
    }

    @Override
    public Status getStatus() {
        return current.getStatus();
    }

    @Override
    public void seek(Duration time) throws CommandException {
        System.out.println("playableplaylist seek to " + time.toSeconds() + " second(s)");
        current.seek(time);
    }

    /**
     * Plays next track in the playlist
     */
    public void next() {
        stop();
        current = playableTrackList.get(currentIndex + 1);
        current.play(false);
        currentIndex++;
    }
}
