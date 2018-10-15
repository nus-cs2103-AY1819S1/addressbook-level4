package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.CommandEntryBuilder.COMMAND_STRINGS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.CommandsLogCenter;
import seedu.address.model.logging.CommandEntry;
import seedu.address.model.logging.ExecutedCommand;

public class CommandHistoryTest {
    private static final String TEST_FILE_NAME = "testing7893h.xml";
    private CommandHistory history;

    @Before
    public void setUp() {
        assert(COMMAND_STRINGS.length > 0);
        CommandsLogCenter.init(TEST_FILE_NAME);
        history = new CommandHistory();
    }

    @Test
    public void constructor_withCommandHistory_copiesCommandHistory() {
        final CommandHistory commandHistoryWithA = new CommandHistory();
        commandHistoryWithA.add("a");

        assertEquals(commandHistoryWithA, new CommandHistory(commandHistoryWithA));
    }

    @Test
    public void add() {
        final String validCommand = "clear";
        final String invalidCommand = "adds Bob";

        history.add(validCommand);
        history.add(invalidCommand);
        assertEquals(Arrays.asList(validCommand, invalidCommand), history.getHistory());
    }

    @Test
    public void equals() {
        final CommandHistory commandHistoryWithA = new CommandHistory();
        commandHistoryWithA.add("a");
        final CommandHistory anotherCommandHistoryWithA = new CommandHistory();
        anotherCommandHistoryWithA.add("a");
        final CommandHistory commandHistoryWithB = new CommandHistory();
        commandHistoryWithB.add("b");

        // same object -> returns true
        assertTrue(commandHistoryWithA.equals(commandHistoryWithA));

        // same values -> returns true
        assertTrue(commandHistoryWithA.equals(anotherCommandHistoryWithA));

        // null -> returns false
        assertFalse(commandHistoryWithA.equals(null));

        // different types -> returns false
        assertFalse(commandHistoryWithA.equals(5.0f));

        // different values -> returns false
        assertFalse(commandHistoryWithA.equals(commandHistoryWithB));
    }

    @Test
    public void hashcode() {
        final CommandHistory commandHistoryWithA = new CommandHistory();
        commandHistoryWithA.add("a");
        final CommandHistory anotherCommandHistoryWithA = new CommandHistory();
        anotherCommandHistoryWithA.add("a");
        final CommandHistory commandHistoryWithB = new CommandHistory();
        commandHistoryWithB.add("b");

        // same values -> returns same hashcode
        assertEquals(commandHistoryWithA.hashCode(), anotherCommandHistoryWithA.hashCode());

        // different values -> returns different hashcode
        assertNotEquals(commandHistoryWithA.hashCode(), commandHistoryWithB.hashCode());
    }

    @Test
    public void commandHistoryContinues_withIoExceptionTest() {
        File file = new File(TEST_FILE_NAME);
        file.setWritable(false);
        history.add("delete 1");
    }

    @Test
    public void commandHistoryAdds_multipleCommandWordTest() {
        List<ExecutedCommand> expectedExecutedCommandList = new LinkedList<>();
        for (String commandString : COMMAND_STRINGS) {
            history.add(commandString);
            expectedExecutedCommandList.add(new ExecutedCommand(commandString));
        }
        List<ExecutedCommand> actualExecutedCommandList = new LinkedList<>();
        List<CommandEntry> actualCommandEntryList = history.getCommandEntryList();
        assertEquals(COMMAND_STRINGS.length, actualCommandEntryList.size());
        for (CommandEntry commandEntry : actualCommandEntryList) {
            actualExecutedCommandList.add(commandEntry.getExecutedCommand());
        }
        assertEquals(expectedExecutedCommandList, actualExecutedCommandList);
    }

    @Test
    public void commandHistoryAdds_invalidCommandWordTest() {
        String commandString = "asdk";
        history.add(commandString);
        ExecutedCommand expectedExecutedCommand = new ExecutedCommand(commandString);
        List<CommandEntry> actualCommandEntryList = history.getCommandEntryList();
        assertEquals(1, actualCommandEntryList.size());
        assertEquals(expectedExecutedCommand, actualCommandEntryList.get(0).getExecutedCommand());
    }

    @Test
    public void commandHistoryGetCommandEntry_ioExceptionTest() throws IOException {
        //populate the xml file
        for (String commandString : COMMAND_STRINGS) {
            history.add(commandString);
        }

        //create file problem
        File file = new File(TEST_FILE_NAME);
        file.setWritable(false);
        CommandsLogCenter.init();
        file.setWritable(true);

        //trigger the delete and file recreation
        List<CommandEntry> actualCommandEntryList = history.getCommandEntryList();
        assertEquals(0, actualCommandEntryList.size());

        //Compare file content
        byte[] encoded = Files.readAllBytes(Paths.get(TEST_FILE_NAME));
        String fileData = new String(encoded, CommandsLogCenter.STANDARDIZED_ENCODING);
        assertEquals(CommandsLogCenter.STANDARDIZED_XML_HEADER + "\n" + CommandsLogCenter.LIST_HEADER,
                fileData);
    }

    @Test
    public void commandHistoryGetCommandEntry_jaxbExceptionTest() throws IOException {
        //populate the xml file
        for (String commandString : COMMAND_STRINGS) {
            history.add(commandString);
        }

        //Corrupt the xml file
        File file = new File(TEST_FILE_NAME);
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.append("<asdnjkdg>");
        fileWriter.close();

        //trigger the delete and file recreation
        List<CommandEntry> actualCommandEntryList = history.getCommandEntryList();
        assertEquals(0, actualCommandEntryList.size());

        //Compare file content
        byte[] encoded = Files.readAllBytes(Paths.get(TEST_FILE_NAME));
        String fileData = new String(encoded, CommandsLogCenter.STANDARDIZED_ENCODING);
        assertEquals(CommandsLogCenter.STANDARDIZED_XML_HEADER + "\n" + CommandsLogCenter.LIST_HEADER,
                fileData);
    }
}
