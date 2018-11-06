package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.CdCommand;
import seedu.address.logic.commands.ConvertCommand;
import seedu.address.logic.commands.LsCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = LsCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private static final String CD_COMMAND_THAT_SUCCEEDS = CdCommand.COMMAND_WORD;
    private static final String UNDO_COMMAND_THAT_FAILS = UndoCommand.COMMAND_WORD;
    private static final String CONVERT_COMMAND_THAT_FAILS = ConvertCommand.COMMAND_WORD;

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    private CommandBoxHandle commandBoxHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);
        UserPrefs prefs = new UserPrefs();

        CommandBox commandBox = new CommandBox(logic, prefs);
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_handleKeyPress() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    @Test
    public void handleKeyPress_startingWithUp() {
        // empty history
        assertInputHistory(KeyCode.UP, "");
        assertInputHistory(
                KeyCode.DOWN, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");

        // two commands (latest command is failure)
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        //assertInputHistory(KeyCode.UP, thirdCommand);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
    }

    @Test
    public void handleKeyPress_startingWithDown() {
        // empty history
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);

        // two commands
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, thirdCommand);
    }

    @Test
    public void handleKeyPress_wrongCommandWordWithTab() {
        // empty commandBox
        assertInputHistory(KeyCode.TAB, "");
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, "");

        // ls command
        commandBoxHandle.setText(COMMAND_THAT_SUCCEEDS + " ");
        assertInputHistory(KeyCode.TAB, COMMAND_THAT_SUCCEEDS + " ");
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_SUCCEEDS + " ");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS + " ");

        // undo command
        commandBoxHandle.setText(UNDO_COMMAND_THAT_FAILS + " ");
        assertInputHistory(KeyCode.TAB, UNDO_COMMAND_THAT_FAILS + " ");
        assertInputHistory(KeyCode.DOWN, UNDO_COMMAND_THAT_FAILS + " ");
        assertInputHistory(KeyCode.UP, UNDO_COMMAND_THAT_FAILS + " ");

        // convert command
        commandBoxHandle.setText(CONVERT_COMMAND_THAT_FAILS + " ");
        assertInputHistory(KeyCode.TAB, CONVERT_COMMAND_THAT_FAILS + " ");
        assertInputHistory(KeyCode.DOWN, CONVERT_COMMAND_THAT_FAILS + " ");
        assertInputHistory(KeyCode.UP, CONVERT_COMMAND_THAT_FAILS + " ");
    }

    @Test
    public void handleKeyPress_cdCommandWordWithTab() {
        // cd commands to get current directory with one input
        commandBoxHandle.setText(CD_COMMAND_THAT_SUCCEEDS + " Desk");
        assertInputHistory(KeyCode.TAB, CD_COMMAND_THAT_SUCCEEDS + " Desktop/");
        assertInputHistory(KeyCode.DOWN, CD_COMMAND_THAT_SUCCEEDS + " Desktop/");
        assertInputHistory(KeyCode.UP, CD_COMMAND_THAT_SUCCEEDS + " Desktop/");

        String os = System.getProperty("os.name").toLowerCase();

        /*if (os.contains("win")) {
            // cd commands to change drive on windows
            commandBoxHandle.setText(CD_COMMAND_THAT_SUCCEEDS + " C://Us");
            assertInputHistory(KeyCode.TAB, CD_COMMAND_THAT_SUCCEEDS + " C://Users/");
            assertInputHistory(KeyCode.DOWN, CD_COMMAND_THAT_SUCCEEDS + " C://Users/");
            assertInputHistory(KeyCode.UP, CD_COMMAND_THAT_SUCCEEDS + " C://Users/");
        }

        if (os.contains("mac")) {
            // cd commands to change drive on mac
            commandBoxHandle.setText(CD_COMMAND_THAT_SUCCEEDS + " /Vol");
            assertInputHistory(KeyCode.TAB, CD_COMMAND_THAT_SUCCEEDS + " /Volume/");
            assertInputHistory(KeyCode.DOWN, CD_COMMAND_THAT_SUCCEEDS + " /Volume/");
            assertInputHistory(KeyCode.UP, CD_COMMAND_THAT_SUCCEEDS + " /Volume/");
        }*/
    }

    /**
     * Runs a command that fails, then verifies that <br>
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertInputHistory(KeyCode keycode, String expectedCommand) {
        guiRobot.push(keycode);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }
}
