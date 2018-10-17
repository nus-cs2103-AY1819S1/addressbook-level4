package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.learnvocabulary.model.Model.PREDICATE_SHOW_ALL_WORDS;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;

/**
 * Lists all words in LearnVocabulary to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all words";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredWordList(PREDICATE_SHOW_ALL_WORDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
