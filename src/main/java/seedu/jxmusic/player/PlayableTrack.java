package seedu.jxmusic.player;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Track;

/**
 * Playlist1 structure used by Player
 */
public class PlayableTrack implements Playable {
    private MediaPlayer mediaPlayer;
    private Media media;
    private String fileName;
    private Track track;

    public PlayableTrack(Track track) {
        this.track = track;
        media = new Media(track.getFile().toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        try {
            mediaPlayer.setOnReady(() -> {
                System.out.println("ready");
            });
            mediaPlayer.setOnEndOfMedia(() -> {
                System.out.println("end of media");
            });
        } catch (MediaException ex) {
            if (ex.getType() == MediaException.Type.UNKNOWN) {
                throw new NullPointerException(System.getProperty("os.name") + System.getProperty("os.version"));
            }
        }
    }

    public String getTrackName() {
        return track.getFileNameWithoutExtension();
    }

    @Override
    public void play(boolean unpause) {
        if (!unpause) {
            mediaPlayer.setStartTime(new Duration(0));
        }
        mediaPlayer.play();
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public Status getStatus() {
        switch (mediaPlayer.getStatus()) {
        case PLAYING: return Status.PLAYING;
        case PAUSED: return Status.PAUSED;
        case STOPPED: return Status.STOPPED;
        default: return Status.ERROR;
        }
    }

    @Override
    public void seek(Duration time) throws CommandException {
        Duration trackDuration = media.getDuration();
        if (time.compareTo(trackDuration) > 0) {
            throw new CommandException("Required time is beyond track's duration");
        }
        System.out.println("playabletrack seek to " + time.toSeconds() + " second(s)");
        mediaPlayer.seek(time);
    }

    public void setOnEndOfMedia(Runnable runnable) {
        mediaPlayer.setOnEndOfMedia(runnable);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
