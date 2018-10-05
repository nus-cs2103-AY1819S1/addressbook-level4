package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.player.JxMusicPlayer;

public class PauseCommand extends Command {

    public static final String COMMAND_WORD = "pause";

    public static final String MESSAGE_SUCCESS = "Pause a track";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        new JxMusicPlayer().pause();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
