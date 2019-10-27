package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBHEADER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.entry.ResumeEntry;

/**
 * Adds an entry to ResuMaker.
 */
public class AddEntryCommand extends Command {
    public static final String COMMAND_WORD = "addEntry";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entry to ResuMaker "
            + "Parameters: "
            + PREFIX_CATEGORY + "SECTION TYPE "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_SUBHEADER + "SUBHEADER]"
            + "[" + PREFIX_DURATION + "DURATION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "Experience "
            + PREFIX_TAG + "Java "
            + PREFIX_TITLE + "The Source Enterprise "
            + PREFIX_SUBHEADER + "Java Programmer intern "
            + PREFIX_DURATION + "May 2010 - Aug 2010 ";

    public static final String MESSAGE_SUCCESS = "New entry added: %1$s";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists";

    private final ResumeEntry toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddEntryCommand(ResumeEntry entry) {
        requireNonNull(entry); // if the object is null throw nullpointer exception, else return the object
        toAdd = entry;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.hasEntry(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.addEntry(toAdd);
        model.commitEntryBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEntryCommand // instanceof handles nulls
                && toAdd.equals(((AddEntryCommand) other).toAdd));
    }
}
