package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;

import org.junit.Test;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;
import seedu.address.testutil.Assert;

//@@author alexkmj
/**
 * Unit testing for {@code AddModuleCommandParser}.
 * <p>
 * Tests for the following:
 * <ul>
 *     <li>Argument should not be null</li>
 *     <li>Number of arguments should be 8 or 10</li>
 *     <li>Only grade can be null</li>
 * </ul>
 */
public class AddModuleCommandParserTest {
    /**
     * {@code AddModuleCommandParser} instance used for parsing arguments in
     * {@code AddModuleCommandParserTest}.
     */
    private AddModuleCommandParser parser = new AddModuleCommandParser();

    /**
     * Argument cannot be null.
     */
    @Test
    public void parseNullArgumentFails() {
        Assert.assertThrows(NullPointerException.class, () -> {
            parser.parse(null);
        });
    }

    /**
     * Number of arguments for edit should be more than or equal to 4 and less
     * than or equal to 16.
     */
    @Test
    public void parseInvalidNumOfArgumentFails() {
        String exceptionMsg = "Invalid number of arguments! "
                + "Number of arguments should be 8, 10\n";

        assertParseFailure(parser, "", exceptionMsg);
        assertParseFailure(parser, "-m CS1231", exceptionMsg);
        assertParseFailure(parser, "-m CS1231 -y 1", exceptionMsg);
        assertParseFailure(parser, "-m CS1231 -y 1 -s 1", exceptionMsg);
        assertParseFailure(parser,
                "-m CS1231 -y 1 -s 1 -c 4 -g A+ -extra extra",
                exceptionMsg);
    }

    /**
     * Parse in this unit test should pass.
     */
    @Test
    public void parseAllFieldsPresentSuccess() {
        // leading and trailing whitespace
        assertParseSuccess(parser,
                "-m CS1231 -y 1 -s 1 -c 4 -g A+",
                new AddModuleCommand(DISCRETE_MATH));
    }

    /**
     * Parse in this unit test should pass.
     */
    @Test
    public void parseOptionalFieldsMissingSuccess() {
        Module expectedModule = new ModuleBuilder(DISCRETE_MATH)
                .noGrade()
                .withCompleted(false)
                .build();

        // no grade
        assertParseSuccess(parser,
                "-m CS1231 -y 1 -s 1 -c 4",
                new AddModuleCommand(expectedModule));
    }
}
