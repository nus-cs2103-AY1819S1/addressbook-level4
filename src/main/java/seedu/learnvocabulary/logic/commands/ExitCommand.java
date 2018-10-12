package seedu.learnvocabulary.logic.commands;

import seedu.learnvocabulary.commons.core.EventsCenter;
import seedu.learnvocabulary.commons.events.ui.ExitAppRequestEvent;
import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting LearnVocabulary as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
