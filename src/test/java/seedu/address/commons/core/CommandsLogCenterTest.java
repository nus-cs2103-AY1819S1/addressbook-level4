package seedu.address.commons.core;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.logging.CommandEntry;
import seedu.address.model.logging.ExecutedCommand;
import seedu.address.storage.XmlAdaptedCommandEntry;

/**
 * Contains integration tests with storage and model for {@code CommandsLogCenter}
 */
public class CommandsLogCenterTest {
    private static final CommandEntry[] COMMAND_ENTRIES = {
            new CommandEntry(Instant.now(), new ExecutedCommand("add asdfvf")),
            new CommandEntry(Instant.now(), new ExecutedCommand("delete 1")),
            new CommandEntry(Instant.now(), new ExecutedCommand("help"))};
    private static final String TEST_FILE_NAME = "testing7893h.xml";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        assert(COMMAND_ENTRIES.length > 0);
        CommandsLogCenter.init(TEST_FILE_NAME);
    }

    @Test
    public void logAndRetrieveSuccessTest() throws IOException, JAXBException, IllegalValueException {
        List<CommandEntry> expectedCommandEntries = new LinkedList<>();
        List<XmlAdaptedCommandEntry> expectedXmlAdaptedCommandEntryList = new LinkedList<>();
        for (CommandEntry COMMAND_ENTRY : COMMAND_ENTRIES) {
            CommandsLogCenter.log(COMMAND_ENTRY);
            expectedCommandEntries.add(COMMAND_ENTRY);
            expectedXmlAdaptedCommandEntryList.add(new XmlAdaptedCommandEntry(COMMAND_ENTRY));
        }
        List<CommandEntry> actualCommandEntries = new LinkedList<>();
        List<XmlAdaptedCommandEntry> xmlAdaptedCommandEntryList = CommandsLogCenter.retrieve().getValue();
        for (XmlAdaptedCommandEntry xmlAdaptedCommandEntry : xmlAdaptedCommandEntryList) {
            actualCommandEntries.add(xmlAdaptedCommandEntry.toModelType());
        }
        assertEquals(expectedXmlAdaptedCommandEntryList, xmlAdaptedCommandEntryList);
        assertEquals(expectedCommandEntries, actualCommandEntries);
    }

    @Test
    public void logUnableAccessFileTest() throws IOException, JAXBException {
        thrown.expect(IOException.class);
        File file = new File(TEST_FILE_NAME);
        file.setWritable(false);
        CommandsLogCenter.init();
        try {
            CommandsLogCenter.log(COMMAND_ENTRIES[0]);
        } catch (IOException ie) {
            assertEquals(String.format(CommandsLogCenter.MESSAGE_LOG_INACCESSIBLE, TEST_FILE_NAME), ie.getMessage());
            throw ie;
        }
    }

    @Test
    public void logUnableToWriteToFileTest() throws IOException, JAXBException {
        thrown.expect(IOException.class);
        File file = new File(TEST_FILE_NAME);
        file.setWritable(false);
        try {
            CommandsLogCenter.log(COMMAND_ENTRIES[0]);
        } catch (IOException ie) {
            assertEquals(String.format("%1$s (Access is denied)", TEST_FILE_NAME), ie.getMessage());
            throw ie;
        }
    }

    @Test
    public void retrieveUnableAccessFileTest() throws IOException, JAXBException {
        thrown.expect(IOException.class);
        File file = new File(TEST_FILE_NAME);
        file.setWritable(false);
        CommandsLogCenter.init();
        try {
            CommandsLogCenter.retrieve();
        } catch (IOException ie) {
            assertEquals(String.format(CommandsLogCenter.MESSAGE_LOG_INACCESSIBLE, TEST_FILE_NAME), ie.getMessage());
            throw ie;
        }
    }

    @Test
    public void retrieveJaxbExceptionTest() throws IOException, JAXBException {
        thrown.expect(JAXBException.class);
        //add a CommandEntry first
        CommandsLogCenter.log(COMMAND_ENTRIES[0]);

        //Corrupt the xml file
        File file = new File(TEST_FILE_NAME);
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.append("<asdnjkdg>");
        fileWriter.close();

        CommandsLogCenter.retrieve();
    }
}
