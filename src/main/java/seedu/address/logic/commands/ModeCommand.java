package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class ModeCommand extends Command {
    public static final String FLAT_MODE = "flat";
    public static final String DECREASING_MODE = "decreasing";

    public static final String COMMAND_WORD = "mode";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the game mode.\n"
            + "Parameters: \"" + FLAT_MODE + "\" or \"" + DECREASING_MODE + "\"\n"
            + "Example: " + COMMAND_WORD + " " + FLAT_MODE;

    public static final String MESSAGE_MODE_CHANGE_SUCCESS = "Game mode successfully changed!";

    private final String newGameModeName;

    public ModeCommand(String newGameModeName) {
        this.newGameModeName = newGameModeName;
    }

    @Override
    public CommandResult executePrimitive(Model model, CommandHistory history) {
        requireNonNull(model);

        model.updateGameMode(newGameModeName);
        model.commitTaskManager();
        return new CommandResult(MESSAGE_MODE_CHANGE_SUCCESS);
    }
}
