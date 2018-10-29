package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import javax.imageio.ImageIO;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.FilmEvent;
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
        int count = 0;

        try {
            File[] fileList = dir.listFiles();
            for (File file : fileList) {
                if (file.isFile()) {
                    // only list if is image
                    if (ImageIO.read(file) != null) {
                        fileNames.append(file.getName());
                        fileNames.append("   \n");
                    }
                } else if (file.isDirectory()) {
                    fileNames.append(file.getName());
                    fileNames.append("   \n");
                }
            }

            if (fileNames.toString().isEmpty()) {
                fileNames.append("No images or folders to display!");
            } else {
                EventsCenter.getInstance().post(new FilmEvent(model.returnPreviewImageList()));
            }

            return new CommandResult(fileNames.toString());
        } catch (Exception ex) {
            return new CommandResult(MESSAGE_FAILURE);
        }

    }
}
