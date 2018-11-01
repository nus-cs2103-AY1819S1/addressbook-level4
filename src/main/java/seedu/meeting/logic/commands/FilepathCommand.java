package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.meeting.logic.parser.CliSyntax.PREFIX_PATH;

import java.nio.file.Path;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;

/**
 * Change the filepath of MeetingBook of where MeetingBook will be saved.
 */
public class FilepathCommand extends Command {
    public static final String COMMAND_WORD = "filepath";
    public static final String OPTION_SHOW = "--show";

    public static final String MESSAGE_CHANGEPATH_SUCCESS = "MeetingBook now will be saved at: %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": change current saving location of MeetingBook "
            + "to the new specific location.\n"
            + "Parameters: "
            + PREFIX_PATH + "FilePath or " + OPTION_SHOW + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATH + "newHome.xml\n"
            + "Example: " + COMMAND_WORD + " "
            + OPTION_SHOW + " : Show the current storage path.";
    public static final String MESSAGE_SHOWPATH_SUCCESS = "MeetingBook is stored at ./%s.";
    private Path filepath;

    public FilepathCommand(Path filePath) {
        this.filepath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (filepath == null) {
            Path currentPath = model.getMeetingBookFilePath();
            return new CommandResult(String.format(MESSAGE_SHOWPATH_SUCCESS, currentPath.toString()));
        }

        model.changeUserPrefs(filepath);
        return new CommandResult(String.format(MESSAGE_CHANGEPATH_SUCCESS, filepath.toString()));
    }

}
