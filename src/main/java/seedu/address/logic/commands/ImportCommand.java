package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.UniqueCardList;
import seedu.address.model.portation.ImportFile;

/**
 * Import cards from specified location to the trivia bundle.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports cards to the trivia. "
            + "Parameters: "
            + "File path\n"
            + "Example: " + COMMAND_WORD + " "
            + "C:\\Users\\username\\Desktop\\cards.txt";

    public static final String MESSAGE_SUCCESS = "%1$s card(s) imported from: %2$s.";
    public static final String MESSAGE_INVALID_IMPORT_FILE_FORMAT = "Invalid import file format.";
    public static final String MESSAGE_INVALID_IMPORT_FILE_NO_CARDS_FOUND = "No cards found.";
    public static final String MESSAGE_DUPLICATE_CARD = "Some cards already exists in the trivia bundle.";
    private final ImportFile importFile;

    /**
     * Creates an ImportCommand with the file name {@code fileName} to import the cards {@code cards}
     */
    public ImportCommand(ImportFile file) {
        requireNonNull(file);
        importFile = file;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!importFile.isFileValid()) {
            throw new CommandException(MESSAGE_INVALID_IMPORT_FILE_FORMAT);
        }

        UniqueCardList cardsToImport = importFile.parseFileToCards();

        if (cardsToImport.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_IMPORT_FILE_NO_CARDS_FOUND);
        }

        if (model.haveAnyCard(cardsToImport)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        model.addMultipleCards(cardsToImport);
        model.commitTriviaBundle();

        return new CommandResult(String.format(MESSAGE_SUCCESS, cardsToImport.size(), importFile.getFileName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && importFile.equals(((ImportCommand) other).importFile));
    }
}
