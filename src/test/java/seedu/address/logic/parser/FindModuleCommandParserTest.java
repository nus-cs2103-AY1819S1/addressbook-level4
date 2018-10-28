package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindModuleCommand;
import seedu.address.model.module.AcademicYearContainsKeywordsPredicate;
import seedu.address.model.module.ModuleCodeContainsKeywordsPredicate;
import seedu.address.model.module.ModuleTitleContainsKeywordsPredicate;
import seedu.address.model.module.SemesterContainsKeywordsPredicate;

public class FindModuleCommandParserTest {
    private FindModuleCommandParser parser = new FindModuleCommandParser();


    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindModuleCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsFindModuleCommand() {
        // no leading and trailing whitespaces
        FindModuleCommand expectedFindModuleCommand =
                new FindModuleCommand(new ModuleCodeContainsKeywordsPredicate(Arrays.asList("CS1101S", "CS1231")));
        assertParseSuccess(parser, " mc/CS1101S CS1231", expectedFindModuleCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " mc/ \n CS1101S \n \t CS1231  \t", expectedFindModuleCommand);

        expectedFindModuleCommand = new FindModuleCommand(
                new ModuleTitleContainsKeywordsPredicate(Arrays.asList("Methodology", "Discrete")));
        assertParseSuccess(parser, " mt/Methodology Discrete", expectedFindModuleCommand);
        assertParseSuccess(parser, " mt/ \n Methodology \n \t Discrete  \t", expectedFindModuleCommand);

        expectedFindModuleCommand = new FindModuleCommand(
                new AcademicYearContainsKeywordsPredicate(Arrays.asList("1819", "1920")));
        assertParseSuccess(parser, " ay/1819 1920", expectedFindModuleCommand);
        assertParseSuccess(parser, " ay/ \n 1819 \n \t 1920  \t", expectedFindModuleCommand);

        expectedFindModuleCommand = new FindModuleCommand(
                new SemesterContainsKeywordsPredicate(Arrays.asList("1", "2")));
        assertParseSuccess(parser, " sem/1 2", expectedFindModuleCommand);
        assertParseSuccess(parser, " sem/ \n 1 \n \t 2  \t", expectedFindModuleCommand);
    }
}
