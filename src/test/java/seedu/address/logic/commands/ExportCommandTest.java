package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class ExportCommandTest {

    @Test
    public void execute_filePathValid_success() {
        // txt, MacOS/Linux
        String filePath = "validExport.txt";
        ExportCommand command = new ExportTxtCommand(filePath, ExportCommand.FileType.TXT );
        assertEquals(true, command.isValidFilePath());

        // xml, MacOS/Linux
        filePath = "validExport.xml";
        command = new ExportXmlCommand(filePath, ExportCommand.FileType.XML );
        assertEquals(true, command.isValidFilePath());

        // txt, Windows
        filePath = "C:\\validExport.txt";
        command = new ExportTxtCommand(filePath, ExportCommand.FileType.TXT );
        assertEquals(true, command.isValidFilePath());

        // xml, Windows
        filePath = "C:\\validExport.xml";
        command = new ExportXmlCommand(filePath, ExportCommand.FileType.XML );
        assertEquals(true, command.isValidFilePath());
    }

    @Test
    public void execute_filePathInvalid_failure() {
        // txt, MacOS/Linux
        String filePath = "valid$Export.txt";
        ExportCommand command = new ExportTxtCommand(filePath, ExportCommand.FileType.TXT );
        assertEquals(false, command.isValidFilePath());

        // xml, MacOS/Linux
        filePath = "valid&Export.xml";
        command = new ExportXmlCommand(filePath, ExportCommand.FileType.XML );
        assertEquals(false, command.isValidFilePath());

        // txt, Windows
        filePath = "C:\\v/alidExport.txt";
        command = new ExportTxtCommand(filePath, ExportCommand.FileType.TXT );
        assertEquals(false, command.isValidFilePath());

        // xml, Windows
        filePath = "C:\\vali%dExport.xml";
        command = new ExportXmlCommand(filePath, ExportCommand.FileType.XML );
        assertEquals(false, command.isValidFilePath());
    }
}
