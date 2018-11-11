package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Word;

/**
 * Learns a word from Dictionary.com into LearnVocabulary.
 */
public class LearnCommand extends Command {

    public static final String COMMAND_WORD = "learn";

    public static final String MESSAGE_NO_GROUP = "The group typed does not exist.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches Dictionary.com for given word. "
            + "Parameters: "
            + "WORD "
            + "Example: " + COMMAND_WORD + " "
            + "Rainstorm ";

    public static final String MESSAGE_SUCCESS = "New word learnt: %1$s\n";
    public static final String MESSAGE_DUPLICATE_WORD = "You have already listed this word inside LearnVocabulary!";

    private final Word toAdd;

    /**
     * Creates LearnCommand to add the specified {@code Word}
     */
    public LearnCommand(Word word) {
        requireNonNull(word);
        toAdd = word;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasWord(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WORD);
        }

        model.addWord(toAdd);
        model.commitLearnVocabulary();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LearnCommand // instanceof handles nulls
                && toAdd.equals(((LearnCommand) other).toAdd));
    }
}
