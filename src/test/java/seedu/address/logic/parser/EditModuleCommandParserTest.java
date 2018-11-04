package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalModules.DATA_STRUCTURES;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;

import org.junit.Test;

import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

/**
 * Unit testing for {@code EditModuleCommandParser}.
 * <p>
 * Tests for the following:
 * <ul>
 *     <li>Argument should not be null</li>
 *     <li>Number of arguments should be between 3 and 13</li>
 *     <li>Target code cannot be null</li>
 *     <li>
 *         Target year is non-null if and only if target semester is non-null
 *     </li>
 *     <li>One of the new fields should be non-null</li>
 * </ul>
 */
public class EditModuleCommandParserTest {

    /**
     * {@code EditModuleCommandParser} instance used for parsing arguments in
     * {@code EditModuleCommandParserTest}.
     */
    private EditModuleCommandParser parser = new EditModuleCommandParser();

    /**
     * Argument cannot be null.
     */
    @Test
    public void parseNullArgumentFails() throws ParseException {
        Assert.assertThrows(NullPointerException.class, () -> {
            parser.parse(null);
        });
    }

    /**
     * Number of arguments for edit should be more than or equal to 3 and less
     * than or equal to 13.
     */
    @Test
    public void parseInvalidNumOfArgumentFails() {
        String exceptionMsg = "Invalid number of arguments! Number of arguments"
                + " should be more than or equal to 3 and less than or equal to"
                + " 13";

        assertParseFailure(parser, "", exceptionMsg);
        assertParseFailure(parser, "CS1231", exceptionMsg);
        assertParseFailure(parser, "CS1231 1", exceptionMsg);
        assertParseFailure(parser,
                "CS1231 1 1"
                        + " -code CS1232 -year 1 -semester 1 -credit 4 -grade 5"
                        + " extra",
                exceptionMsg);
    }

    /**
     * Target code cannot be null.
     */
    @Test
    public void parseTargetCodeNullFails() {
        assertParseFailure(parser,
                "-code CS1232 -grade A+",
                EditModuleCommandParser.MESSAGE_TARGET_CODE_REQUIRED);
    }

    /**
     * If target year is non-null, target semester should also be non-null.
     * <p>
     * Conversely, if target year is null, target semester should also be null.
     */
    @Test
    public void parseTargetYearTargetSemesterExclusiveNullFails() {
        assertParseFailure(parser,
                "CS1231 1 -code CS1232",
                EditModuleCommandParser.MESSAGE_YEAR_AND_SEMESTER_XOR_NULL);
    }

    /**
     * Edit should lead to changes.
     */
    @Test
    public void parseNoChangesFails() {
        assertParseFailure(parser,
                "CS1231 -grade -year",
                EditModuleCommandParser.MESSAGE_NO_CHANGES);

        assertParseFailure(parser,
                "CS1231 1 1",
                EditModuleCommandParser.MESSAGE_NO_CHANGES);

        assertParseFailure(parser,
                "CS1231 1 1 -grade",
                EditModuleCommandParser.MESSAGE_NO_CHANGES);
    }

    /**
     * Parse in this unit test should pass.
     */
    @Test
    public void parseSuccess() {
        assertParseSuccess(parser,
                "CS2040 -code CS1231",
                new EditModuleCommand(
                        DATA_STRUCTURES.getCode(),
                        null,
                        null,
                        DISCRETE_MATH.getCode(),
                        null,
                        null,
                        null,
                        null));

        assertParseSuccess(parser,
                "CS2040 3 s1 -code CS1231",
                new EditModuleCommand(
                        DATA_STRUCTURES.getCode(),
                        DATA_STRUCTURES.getYear(),
                        DATA_STRUCTURES.getSemester(),
                        DISCRETE_MATH.getCode(),
                        null,
                        null,
                        null,
                        null));
    }
}
