package seedu.thanepark.commons.core;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.thanepark.commons.exceptions.IllegalValueException;
import seedu.thanepark.model.logging.CommandEntry;
import seedu.thanepark.storage.XmlAdaptedCommandEntry;
import seedu.thanepark.testutil.CommandEntryBuilder;

/**
 * Contains integration tests with storage and model for {@code CommandsLogCenter}
 */
public class CommandsLogCenterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private File file;
    private String logfilePathString;

    @Before
    public void setup() throws IOException {
        assert(CommandEntryBuilder.COMMAND_ENTRIES.length > 0);

        CommandsLogCenter.init();
        logfilePathString = CommandsLogCenter.getFilePathString(CommandsLogCenter.LOG_FILE);
        file = new File(logfilePathString);
        assert(file.setWritable(true));
        assert(file.setReadable(true));
    }

    @Test
    public void logAndRetrieveSuccessTest() throws IOException, JAXBException, IllegalValueException {
        List<CommandEntry> expectedCommandEntries = new LinkedList<>();
        List<XmlAdaptedCommandEntry> expectedXmlAdaptedCommandEntryList = new LinkedList<>();
        for (CommandEntry commandEntry : CommandEntryBuilder.COMMAND_ENTRIES) {
            CommandsLogCenter.log(commandEntry);
            expectedCommandEntries.add(commandEntry);
            expectedXmlAdaptedCommandEntryList.add(new XmlAdaptedCommandEntry(commandEntry));
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
        file.setWritable(false);
        CommandsLogCenter.init();
        try {
            CommandsLogCenter.log(CommandEntryBuilder.COMMAND_ENTRIES[0]);
        } catch (IOException ie) {
            assertEquals(String.format(CommandsLogCenter.MESSAGE_LOG_INACCESSIBLE, logfilePathString), ie.getMessage());
            throw ie;
        } finally {
            assert(file.setWritable(true));
            assert(file.delete());
        }
    }

    @Test
    public void logUnableToWriteToFileTest() throws IOException, JAXBException {
        thrown.expect(IOException.class);
        file.setWritable(false);
        try {
            CommandsLogCenter.log(CommandEntryBuilder.COMMAND_ENTRIES[0]);
        } catch (IOException ie) {
            assertTrue(ie.getMessage().contains("denied"));
            throw ie;
        } finally {
            assert(file.setWritable(true));
            assert(file.delete());
        }
    }

    @Test
    public void retrieveUnableAccessFileTest() throws IOException, JAXBException {
        thrown.expect(IOException.class);

        file.setWritable(false);
        try {
            CommandsLogCenter.init();
            CommandsLogCenter.retrieve();
        } catch (IOException ie) {
            assertEquals(String.format(CommandsLogCenter.MESSAGE_LOG_INACCESSIBLE, logfilePathString), ie.getMessage());
            throw ie;
        } finally {
            assert(file.setWritable(true));
            assert(file.delete());
        }
    }

    @Test
    public void retrieveJaxbExceptionTest() throws IOException, JAXBException {
        thrown.expect(JAXBException.class);
        //add a CommandEntry first
        CommandsLogCenter.init();
        CommandsLogCenter.log(CommandEntryBuilder.COMMAND_ENTRIES[0]);

        //Corrupt the xml file
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.append("<asdnjkdg>");
        fileWriter.close();

        try {
            CommandsLogCenter.retrieve();
        } finally {
            assert(file.setWritable(true));
            assert(file.delete());
        }
    }
}
