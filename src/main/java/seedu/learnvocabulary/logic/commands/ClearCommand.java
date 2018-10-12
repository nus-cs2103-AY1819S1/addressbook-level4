package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.Model;

/**
 * Clears the learnvocabulary book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "LearnVocabulary has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new LearnVocabulary());
        model.commitLearnVocabulary();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
