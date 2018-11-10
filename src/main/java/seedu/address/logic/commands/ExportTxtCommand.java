package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.util.XmlToTxtUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Export data to txt file.
 *
 */
public class ExportTxtCommand extends ExportCommand {

    private String exportedFilePath;
    private FileType fileType;

    public ExportTxtCommand(String filePath, FileType fileType) {
        super(filePath);
        this.exportedFilePath = filePath;
        this.fileType = fileType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        requireNonNull(storage);

        if (!isValidTxtFile()) {
            throw new CommandException(String.format(MESSAGE_INVALID_FILE_PATH));
        }

        if (!Files.isWritable(Paths.get(exportedFilePath))) {
            throw new CommandException(String.format(MESSAGE_FILE_PERMISSION_DENIED));
        }

        try {
            Path tempPath = Paths.get("temp.xml");
            storage.saveAddressBook(model.getAddressBook(), tempPath);
            XmlToTxtUtil.parse(tempPath.toFile(), exportedFilePath);
            tempPath.toFile().delete();
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_FAIL_READ_FILE));
        } catch (Exception e) {
            return new CommandResult(String.format(MESSAGE_FAIL_XML_TXT_CONVERSION));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, exportedFilePath));
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    private boolean isValidTxtFile() {
        return isValidFilePath() && exportedFilePath.endsWith(".txt");
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExportTxtCommand)
                && exportedFilePath.equals(((ExportCommand) other).exportedFilePath);
    }
}
