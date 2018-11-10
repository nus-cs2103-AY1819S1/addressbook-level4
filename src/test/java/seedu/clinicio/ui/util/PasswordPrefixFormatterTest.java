package seedu.clinicio.ui.util;

import static org.junit.Assert.assertEquals;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_ADAM;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;

import org.junit.Before;
import org.junit.Test;

import seedu.clinicio.logic.Logic;
import seedu.clinicio.logic.LogicManager;
import seedu.clinicio.logic.commands.LoginCommand;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.ui.CommandBox;
import seedu.clinicio.ui.GuiUnitTest;

import guitests.guihandles.CommandBoxHandle;

import javafx.scene.input.KeyCode;

public class PasswordPrefixFormatterTest extends GuiUnitTest {

    private static final String WHITESPACE = " ";

    private PasswordPrefixFormatter formatter;
    private CommandBoxHandle commandBoxHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);

        CommandBox commandBox = new CommandBox(logic);
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        formatter = new PasswordPrefixFormatter();
    }

    @Test
    public void maskPassword() {
        String text = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + VALID_PASSWORD_ADAM;
        String expectedText = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + "-------2";

        // IsHistory -> false, IsBackspace -> false
        commandBoxHandle.run(text);
        assertEquals(expectedText, formatter.maskPassword(commandBoxHandle.getInput(), false, false) + "2");

        // IsHistory -> false, IsBackspace -> true
        guiRobot.type(KeyCode.BACK_SPACE);
        expectedText = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + "------1";
        assertEquals(expectedText, formatter.maskPassword(commandBoxHandle.getInput(), false, true));

        // IsHistory -> true, IsBackspace -> false
        guiRobot.type(KeyCode.UP);
        assertEquals(expectedText, formatter.maskPassword(commandBoxHandle.getInput(), true, false));
    }

    @Test
    public void unmaskPassword() {
        String text = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + "doctor1";
        String expectedText = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + VALID_PASSWORD_ADAM;

        // IsHistory -> false, IsBackspace -> false
        commandBoxHandle.run(text);
        String formattedText = formatter.maskPassword(commandBoxHandle.getInput(), false, false);
        assertEquals(expectedText, formatter.unmaskPassword(formattedText));
    }
}
