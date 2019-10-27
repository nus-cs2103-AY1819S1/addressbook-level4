package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.TemplateLoadedEvent;
import seedu.address.commons.events.storage.TemplateLoadingExceptionEvent;
import seedu.address.commons.exceptions.InvalidTemplateFileException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Loads a template from file.
 */
public class LoadTemplateCommand extends Command {

    public static final String COMMAND_WORD = "loadtemplate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads a template file. "
        + "Parameters: "
        + "FILEPATH\n"
        + "Example: " + COMMAND_WORD + " "
        + "template1.txt";

    public static final String MESSAGE_SUCCESS = "Successful load from %1$s.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File %1$s not found.";
    public static final String MESSAGE_INVALID_FILE_FORMAT = "File %1$s has invalid format.\n"
            + "Specified file should consist only of lines of the format: "
            + "CATEGORY_TILE:~CATEGORY_TAG:[TAG_GROUP]... "
            + "with no extra newlines or spaces,\n"
            + "with each TAG_GROUP of the format: "
            + "TAG[&TAG]...";

    private static final Logger logger = LogsCenter.getLogger(LoadTemplateCommand.class);

    private final Path filePath;
    private boolean isSuccessful;
    private Exception exception;

    /**
     * Creates a LoadTemplateCommand to load the specified {@code Template}
     */
    public LoadTemplateCommand(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
        EventsCenter.getInstance().registerHandler(this);
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.loadTemplate(filePath);

        // as events are handled sequentially, the listeners will set isSuccessful before the function continues
        // executing
        if (!isSuccessful) {
            if (exception instanceof InvalidTemplateFileException) {
                throw new CommandException(String.format(MESSAGE_INVALID_FILE_FORMAT, filePath));
            } else {
                throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, filePath));
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LoadTemplateCommand // instanceof handles nulls
            && filePath.equals(((LoadTemplateCommand) other).filePath));
    }

    @Subscribe
    public void handleTemplateLoadedEvent(TemplateLoadedEvent event) {
        isSuccessful = true;
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Template loading succeeded, "
                + "loadtemplate result updated"));
    }

    @Subscribe
    public void handleTemplateLoadingExceptionEvent(TemplateLoadingExceptionEvent event) {
        isSuccessful = false;
        exception = event.exception;
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Template loading failed, "
                + "loadtemplate result updated"));
    }
}

