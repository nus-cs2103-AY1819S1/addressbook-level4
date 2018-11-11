package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AdjustCommand;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
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
    public void assertInvalidYearFailure() {
        String expectedMessage = Year.MESSAGE_YEAR_CONSTRAINTS;
        String userInput = "cs4234 10 1 A";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void assertInvalidSemesterFailure() {
        String expectedMessage = Semester.MESSAGE_SEMESTER_CONSTRAINTS;
        String userInput = "cs4234 1 10 A";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void assertInvalidGradeFailure() {
        String expectedMessage = Grade.MESSAGE_GRADE_CONSTRAINTS;
        String userInput = "cs4234 1 1 G";
        assertParseFailure(parser, userInput, expectedMessage);
    }


    @Test
    public void assertWrongNumberArgumentParseFailure() {
        String expectedMessage = "Invalid number of arguments! Number of arguments should be 2, 4";
        String userInput = "1 1 1";
        assertParseFailure(parser, userInput, expectedMessage);

        userInput = "1";
        assertParseFailure(parser, userInput, expectedMessage);

        userInput = "1 1 1 1 1";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
