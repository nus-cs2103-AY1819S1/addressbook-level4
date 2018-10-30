package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MORE_INFO_FLAG = "more";

    public static final String SHOWING_SHORT_HELP_MESSAGE = "Showing summarized help.";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private static final String[] ARGUMENTS_BLANK = {""};
    private final boolean isSummarized;
    private final String commandWord;

    /**
     * Creates a HelpCommand.
     */
    public HelpCommand() {
        this(ARGUMENTS_BLANK);
    }

    /**
     * Creates a HelpCommand that requests for help based on {@param args}.
     */
    public HelpCommand(String[] args) {
        //summarized help
        if (args.length == 1 && args[0].isEmpty()) {
            isSummarized = true;
            commandWord = "";
        //full help
        } else if (args.length == 1 && args[0].equals(MORE_INFO_FLAG)) {
            isSummarized = false;
            commandWord = "";
        //help on specific command
        } else if (args.length == 1 && AllCommandWords.isCommandWord(args[0])) {
            isSummarized = false;
            commandWord = args[0];
        //error
        } else {
            isSummarized = false;
            commandWord = "";
        }
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
