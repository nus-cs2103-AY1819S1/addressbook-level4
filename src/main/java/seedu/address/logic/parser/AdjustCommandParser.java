package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.argsWithBounds;

import seedu.address.logic.commands.AdjustCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

import java.util.Set;

//@@author jeremiah-ang
/**
 * Parses input arguments and creates a new AdjustCommand object
 */
public class AdjustCommandParser implements Parser<AdjustCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AdjustCommand parse(String args) throws ParseException {
        String[] tokenizedArgs = ParserUtil.tokenize(args);
        argsWithBounds(tokenizedArgs, Set.of(2,4));

        int index = 0;

        Year year = null;
        Semester sem = null;
        Code code;
        Grade grade;
        code = ParserUtil.parseCode(tokenizedArgs[index++]);
        if (tokenizedArgs.length == 4) {
            year = ParserUtil.parseYear(tokenizedArgs[index++]);
            sem = ParserUtil.parseSemester(tokenizedArgs[index++]);
        }
        grade = ParserUtil.parseGrade(tokenizedArgs[index++]);
        return new AdjustCommand(code, year, sem, grade);
    }
}
