package seedu.thanepark.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.thanepark.commons.core.EventsCenter;
import seedu.thanepark.commons.events.ui.SuggestCommandEvent;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;

/**
 * Not a text command. Can only be triggered using {tab} in CommandBox.
 * Suggests a list of commands based on prefix matching.
 */
public class SuggestCommand extends Command {

    public static final String MESSAGE_SUGGEST_COMMAND_SUCCESS = "Do you mean the following commands:\n%1$s";

    private final String[] suggestions;

    /**
     * Creates a SuggestCommand to suggest all matching commands.
     */
    public SuggestCommand(String[] suggestions) {
        requireNonNull(suggestions);
        this.suggestions = suggestions;
    }

    /**
     * Combines the commandWords into a standardized format for listing
     * @param commandWords
     * @return
     */
    public static String combineCommandWords(String... commandWords) {
        return String.join(", ", commandWords);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String messageSuccess = String.format(MESSAGE_SUGGEST_COMMAND_SUCCESS, combineCommandWords(suggestions));
        EventsCenter.getInstance().post(new SuggestCommandEvent(suggestions));
        return new CommandResult(messageSuccess);
    }
}
