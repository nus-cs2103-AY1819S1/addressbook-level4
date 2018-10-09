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

    public static final String SHOWING_SHORT_HELP_MESSAGE = "Showing summarized help.";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    public static final String MORE_HELP_FLAG = "more";


    public final boolean isSummarized;
    public final String commandWord;

    /**
     * Creates a command that requests for help based on {@param args}
     */
    public HelpCommand(String[] args) {
        if (args.length == 1 && args[0].isEmpty()) {
            isSummarized = true;
            commandWord = "";
        } else if (args.length == 1 && args[0].equals(MORE_HELP_FLAG)) {
            isSummarized = false;
            commandWord = args[0];
        } else if (args.length == 1 && AllCommandWords.isCommandWord(args[0])) {
            isSummarized = false;
            commandWord = "";
        } else {
            //error
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
}
