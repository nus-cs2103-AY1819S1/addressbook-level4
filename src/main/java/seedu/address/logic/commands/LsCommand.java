package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_DIR;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_DIR;

import java.io.File;
import javax.activation.MimetypesFileTypeMap;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.UpdateFilmReelEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author chivent

/**
 * Lists all files in current directory.
 */
public class LsCommand extends Command {

    public static final String COMMAND_WORD = "ls";

    public static final String MESSAGE_FAILURE = "Unable to list files in current directory";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        StringBuffer fileNames = new StringBuffer();

        File dir = new File(model.getCurrDirectory().toString());

        try {
            File[] fileList = dir.listFiles();
            for (File file : fileList) {
                if (file.isFile()) {

                    String mimetype = new MimetypesFileTypeMap().getContentType(file);
                    // only list if is image
                    if ((mimetype.split("/")[0]).equals("image")) {
                        fileNames.append(file.getName());
                        fileNames.append("   \n");
                    }

                } else if (file.isDirectory()) {
                    fileNames.append(file.getName());
                    fileNames.append("   \n");
                }
            }

            if (fileNames.toString().isEmpty()) {
                fileNames.append(MESSAGE_EMPTY_DIR);
            } else {
                EventsCenter.getInstance().post(new UpdateFilmReelEvent(model.getDirectoryImageList()));
            }

            return new CommandResult(fileNames.toString());
        } catch (Exception ex) {
            if (ex instanceof NullPointerException) {
                return new CommandResult(MESSAGE_INVALID_FILE_DIR);
            }
            return new CommandResult(ex.getMessage());
        }

    }
}
