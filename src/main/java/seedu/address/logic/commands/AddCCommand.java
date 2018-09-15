package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;

/**
 * Adds a card to the trivia bundle.
 */
public class AddCCommand extends Command {

    public static final String COMMAND_WORD = "addC";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a card to the trivia. "
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "Why is the earth round? "
            + PREFIX_ANSWER + "Because of gravity! "
            + PREFIX_TAG + "Physics "
            + PREFIX_TAG + "Science";

    public static final String MESSAGE_SUCCESS = "New card added: %1$s";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the trivia bundle";

    private final Card toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Card}
     */
    public AddCCommand(Card card) {
        requireNonNull(card);
        toAdd = card;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasCard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        model.addCard(toAdd);
        model.commitTriviaBundle();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCCommand // instanceof handles nulls
                && toAdd.equals(((AddCCommand) other).toAdd));
    }
}
