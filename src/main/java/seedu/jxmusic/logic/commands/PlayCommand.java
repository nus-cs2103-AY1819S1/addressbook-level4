package seedu.jxmusic.logic.commands;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Lists all persons in the jxmusic book to the user.
 */
public class PlayCommand extends Command {

    // todo change to "play p/" when parser can use p/ for parameter
    public static final String COMMAND_PHRASE = "play";

    public static final String MESSAGE_SUCCESS = "Plays successfully"; // todo display unique message for each PlayMode

    private final PlayMode mode;
    private Playlist argPlaylist;
    private Track argTrack;

    public PlayCommand() {
        mode = PlayMode.CONTINUE_FROM_PAUSE;
    }

    public PlayCommand(Playlist playlist) {
        System.out.println("playing playlist");
        argPlaylist = playlist;
        if (argPlaylist == null) {
            mode = PlayMode.DEFAULT_PLAYLIST;
        } else {
            mode = PlayMode.SPECIFIC_PLAYLIST;
        }
    }

    public PlayCommand(Track track) {
        System.out.println("playing track");
        argTrack = track;
        if (argTrack == null) {
            mode = PlayMode.DEFAULT_TRACK;
        } else {
            mode = PlayMode.SPECIFIC_TRACK;
        }
    }

    @Override
    public CommandResult execute(Model model) {
        switch (mode) {
        case CONTINUE_FROM_PAUSE:
            System.out.println("playing from pause");
            player.play();
            break;
        case DEFAULT_PLAYLIST:
            System.out.println("playing default playlist");
            Playlist firstPlaylist = model.getFilteredPlaylistList().get(0);
            player.play(firstPlaylist);
            break;
        case SPECIFIC_PLAYLIST:
            System.out.println("playing specific playlist");
            Playlist specifiedPlaylist = model.getLibrary().getPlaylistList()
                    .filtered(playlist -> playlist.isSamePlaylist(argPlaylist))
                    .get(0);
            player.play(specifiedPlaylist);
            break;
        case DEFAULT_TRACK:
            System.out.println("playing default track");
            Track firstTrack = model.getLibrary().getTracks().stream()
                    .findFirst()
                    .get();
            player.play(firstTrack);
            break;
        case SPECIFIC_TRACK:
            System.out.println("playing specific track");
            Track specifiedTrack = model.getLibrary().getTracks().stream()
                    .filter(track -> track.equals(argTrack))
                    .findFirst()
                    .get();
            player.play(specifiedTrack);
            break;
        default:
            System.out.println("unexpected default case");
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
