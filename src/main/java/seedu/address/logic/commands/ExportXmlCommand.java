package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.io.Files;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ExportXmlRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Export data to a xml file.
 */
public class ExportXmlCommand extends ExportCommand {

    private String exportedFilePath;
    private FileType fileType;

    public ExportXmlCommand(String filePath, FileType fileType) {
        super(filePath);
        this.exportedFilePath = filePath;
        this.fileType = fileType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        requireNonNull(storage);
        EventsCenter.getInstance().post(new ExportXmlRequestEvent());

        if (!isValidXmlFile()) {
            throw new CommandException(String.format(MESSAGE_INVALID_XML_FILE_PATH));
        }

        try {
            Path tempPath = Paths.get("temp.xml");
            storage.saveAddressBook(model.getAddressBook(), tempPath);
            Files.copy(tempPath.toFile(), Paths.get(exportedFilePath).toFile());
            tempPath.toFile().delete();
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAIL_READ_FILE));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, exportedFilePath));
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    private boolean isValidXmlFile() {
        return isValidFilePath() && exportedFilePath.endsWith(".xml");
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExportXmlCommand)
                && exportedFilePath.equals(((ExportXmlCommand) other).exportedFilePath);
    }
}
