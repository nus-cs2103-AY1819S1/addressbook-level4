package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.util.XmlToTxtUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Export data to txt file.
 *
 */
public class ExportTxtCommand extends ExportCommand {

    protected Path exportedFilePath;

    public ExportTxtCommand(Path filePath) {
        super(filePath);
        this.exportedFilePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        requireNonNull(storage);

        try {
            Path tempPath = Paths.get("temp.xml");
            storage.saveAddressBook(model.getAddressBook(), tempPath);
            XmlToTxtUtil.parse(tempPath.toFile(), exportedFilePath.toString());
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

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExportTxtCommand)
                && exportedFilePath.equals(((ExportCommand) other).exportedFilePath);
    }
}
