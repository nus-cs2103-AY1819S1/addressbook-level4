package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AdjustCommand;
import seedu.address.model.module.Module;
import seedu.address.testutil.TypicalModules;

public class AdjustCommandParserTest {

    private AdjustCommandParser parser = new AdjustCommandParser();

    @Test
    public void parseValidCommandSuccess() {
        Module adjustedModule = TypicalModules.DATA_STRUCTURES;
        assertAdjustParseFullArgumentSuccess(adjustedModule);
        assertAdjustParseCodeOnlySuccess(adjustedModule);
    }

    /**
     * Asserts that parse will be successful given all valid parameters
     * @param expectedModule
     */
    private void assertAdjustParseFullArgumentSuccess(Module expectedModule) {

        String userInput = expectedModule.getCode().value
                + " " + expectedModule.getYear().value
                + " " + expectedModule.getSemester().value
                + " " + expectedModule.getGrade().value;
        AdjustCommand expectedCommand = new AdjustCommand(
                expectedModule.getCode(), expectedModule.getYear(),
                expectedModule.getSemester(), expectedModule.getGrade());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Asserts that parse will be successful given code only
     * @param expectedModule
     */
    private void assertAdjustParseCodeOnlySuccess(Module expectedModule) {
        String userInput = expectedModule.getCode().value
                + " " + expectedModule.getGrade().value;
        AdjustCommand expectedCommand = new AdjustCommand(
                expectedModule.getCode(), null, null, expectedModule.getGrade());
        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void assertWrongNumberArgumentParseFailure() {
        String inputSuggestion = "\nInput order: c_adjust MODULE_CODE GRADE"
                + "\nExample 1: c_adjust CS2103 A"
                + "\nIf the module code is NOT unique, include the YEAR and SEM: "
                + "c_adjust MODULE_CODE YEAR SEM GRADE"
                + "\nExample 2: c_adjust CS2103 1 1 A";
        String expectedMessage = "Invalid number of arguments! Number of arguments should be 2, 4"
                + "\n"
                + inputSuggestion;
        String userInput = "1 1 1";
        assertParseFailure(parser, userInput, expectedMessage);

        userInput = "1";
        assertParseFailure(parser, userInput, expectedMessage);

        userInput = "1 1 1 1 1";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
