package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_MEANING;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Word;

/**
 * Adds a word to LearnVocabulary.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a word to LearnVocabulary. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_MEANING + "MEANING"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Rainstorm "
            + PREFIX_MEANING + "a storm with heavy rain. "
            + PREFIX_TAG + "weather "
            + PREFIX_TAG + "mustLearn";

    public static final String MESSAGE_SUCCESS = "New word added: %1$s";
    public static final String MESSAGE_DUPLICATE_WORD = "This word already exists in LearnVocabulary";

    //@@author Harryqu123
    public static final String MESSAGE_NO_GROUP = "The group typed does not exist.";
    //@@author
    private final Word toAdd;


    /**
     * Creates an AddCommand to add the specified {@code Word}
     */
    public AddCommand(Word word) {
        requireNonNull(word);
        toAdd = word;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasWord(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WORD);
        }
        //@@author Harryqu123
        if (!model.hasTag(toAdd.getTags())) {
            throw new CommandException(MESSAGE_NO_GROUP);
        }
        //@@author
        model.addWord(toAdd);
        model.commitLearnVocabulary();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
