package seedu.jxmusic.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.player.Playable;

/**
 * Plays a track/playlist or continue playing from a pause/stop
 */
public class PlayCommand extends Command {

    public static final String COMMAND_PHRASE = "play";

    public static final String MESSAGE_SUCCESS = "Plays successfully";

    public static final String MESSAGE_NOT_PAUSED = "No track paused/stopped";
    public static final String MESSAGE_NO_TRACK = "There is no track";
    public static final String MESSAGE_NO_PLAYLIST = "There is no playlist";
    public static final String MESSAGE_TRACK_NOT_FOUND = "Track not found";
    public static final String MESSAGE_PLAYLIST_NOT_FOUND = "Playlist not found";
    public static final String MESSAGE_PLAYLIST_EMPTY = "The playlist has no track";

    public static final String MESSAGE_FAILURE = "Failed to play";

    private final PlayMode mode;
    private Playlist argPlaylist;
    private Track argTrack;

    public PlayCommand() {
        mode = PlayMode.CONTINUE_FROM_PAUSE;
    }

    public PlayCommand(Playlist playlist) {
        argPlaylist = playlist;
        if (argPlaylist == null) {
            mode = PlayMode.DEFAULT_PLAYLIST;
        } else {
            mode = PlayMode.SPECIFIC_PLAYLIST;
        }
    }

    public PlayCommand(Track track) {
        argTrack = track;
        if (argTrack == null) {
            mode = PlayMode.DEFAULT_TRACK;
        } else {
            mode = PlayMode.SPECIFIC_TRACK;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        switch (mode) {
        case CONTINUE_FROM_PAUSE:
            if (player.getStatus() == Playable.Status.UNINITIALIZED) {
                throw new CommandException(MESSAGE_NOT_PAUSED);
            }
            player.play();
            break;
        case DEFAULT_PLAYLIST:
            if (player.getStatus() != Playable.Status.UNINITIALIZED) {
                player.stop();
            }
            if (model.getLibrary().getPlaylistList().isEmpty()) {
                throw new CommandException(MESSAGE_NO_PLAYLIST);
            }
            Playlist firstPlaylist = model.getLibrary().getPlaylistList().get(0);
            if (firstPlaylist.getTracks().isEmpty()) {
                throw new CommandException(MESSAGE_PLAYLIST_EMPTY);
            }
            player.play(firstPlaylist);
            break;
        case SPECIFIC_PLAYLIST:
            if (player.getStatus() != Playable.Status.UNINITIALIZED) {
                player.stop();
            }
            if (model.getLibrary().getPlaylistList().isEmpty()) {
                throw new CommandException(MESSAGE_NO_PLAYLIST);
            }
            List<Playlist> filteredPlaylists = model.getLibrary().getPlaylistList()
                    .filtered(playlist -> playlist.isSamePlaylist(argPlaylist));
            if (filteredPlaylists.isEmpty()) {
                throw new CommandException(MESSAGE_PLAYLIST_NOT_FOUND);
            }
            Playlist specifiedPlaylist = filteredPlaylists.get(0);
            if (specifiedPlaylist.getTracks().isEmpty()) {
                throw new CommandException(MESSAGE_PLAYLIST_EMPTY);
            }
            player.play(specifiedPlaylist);
            break;
        case DEFAULT_TRACK:
            if (player.getStatus() != Playable.Status.UNINITIALIZED) {
                player.stop();
            }
            if (model.getLibrary().getTracks().isEmpty()) {
                throw new CommandException(MESSAGE_NO_TRACK);
            }
            Track firstTrack = model.getLibrary().getTracks().stream()
                    .findFirst()
                    .get();
            player.play(firstTrack);
            break;
        case SPECIFIC_TRACK:
            if (player.getStatus() != Playable.Status.UNINITIALIZED) {
                player.stop();
            }
            if (model.getLibrary().getTracks().isEmpty()) {
                throw new CommandException(MESSAGE_NO_TRACK);
            }
            Optional<Track> filteredTrack = model.getLibrary().getTracks().stream()
                    .filter(track -> track.equals(argTrack))
                    .findFirst();
            if (!filteredTrack.isPresent()) {
                throw new CommandException(MESSAGE_TRACK_NOT_FOUND);
            }
            Track specifiedTrack = filteredTrack.get();
            player.play(specifiedTrack);
            break;
        default:
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Specifies the purpose of the PlayCommand
     */
    private enum PlayMode {
        CONTINUE_FROM_PAUSE,
        DEFAULT_PLAYLIST,
        SPECIFIC_PLAYLIST,
        DEFAULT_TRACK,
        SPECIFIC_TRACK
    }
}
