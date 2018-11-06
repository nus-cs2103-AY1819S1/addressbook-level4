package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_PATH;

import java.nio.file.Path;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;

/**
 * Export existing MeetingBook to user-defined path
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
<<<<<<< HEAD:src/main/java/seedu/address/logic/commands/ExportCommand.java
    public static final String MESSAGE_EXPORT_SUCCESS = "Export process has been successful.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the address book to specific filepath.\n"
=======
    public static final String MESSAGE_EXPORT_SUCCESS = "Export process has been successfully completed.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the MeetingBook to specific filepath. "
>>>>>>> master:src/main/java/seedu/meeting/logic/commands/ExportCommand.java
            + "Parameters: "
            + PREFIX_PATH + "FilePath\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATH + "backup.xml";

    private Path filepath;

    public ExportCommand(Path filePath) {
        this.filepath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.exportMeetingBook(this.filepath);
        return new CommandResult(MESSAGE_EXPORT_SUCCESS);
    }

}
