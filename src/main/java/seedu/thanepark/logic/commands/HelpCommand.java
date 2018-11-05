package seedu.thanepark.logic.commands;

import seedu.thanepark.commons.core.EventsCenter;
import seedu.thanepark.commons.events.ui.ShowHelpRequestEvent;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows summarized command usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_SHORT_HELP_MESSAGE = "Showing summarized command syntax.\n"
            + "For full and accurate help use \"help more\". For info on \"add\", type \"help add\"";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private final boolean isSummarized;
    private final String commandWord;

    /**
     * Creates a HelpCommand.
     */
    public HelpCommand(boolean isSummarized, String commandWord) {
        this.isSummarized = isSummarized;
        this.commandWord = commandWord;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent(isSummarized, commandWord));
        if (isSummarized) {
            return new CommandResult(SHOWING_SHORT_HELP_MESSAGE);
        } else {
            return new CommandResult(SHOWING_HELP_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand // instanceof handles nulls
                && isSummarized == ((HelpCommand) other).isSummarized
                && commandWord.equals(((HelpCommand) other).commandWord)); // state check
    }
}
