package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.template.Template;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class MakeCommand extends Command {

    public static final String COMMAND_WORD = "make";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a resume using stored entries "
            + "matching the currently loaded template and saves it in a file with the specified name.\n"
            + "Will only work if there is a template currently loaded.\n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " sep.txt";

    public static final String MESSAGE_SUCCESS = "Resume successfully generated at: %s";
    public static final String MESSAGE_NO_TEMPLATE_FAILURE = "There is no template loaded.";

    private final Path filename;

    public MakeCommand(Path filename) {
        this.filename = filename;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Optional<Template> loadedTemplate = model.getLoadedTemplate();
        if (!loadedTemplate.isPresent()) {
            return new CommandResult(MESSAGE_NO_TEMPLATE_FAILURE);
        }

        model.generateResume();
        model.saveLastResume(filename);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, filename));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MakeCommand // instanceof handles nulls
                && filename.equals(((MakeCommand) other).filename)); // state check
    }
}
