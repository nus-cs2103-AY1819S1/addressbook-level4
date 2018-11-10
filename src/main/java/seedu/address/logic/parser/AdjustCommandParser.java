package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.argsWithBounds;

import java.util.Set;

import seedu.address.logic.commands.AdjustCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

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
        String inputSuggestion = "\nInput order: c_adjust MODULE_CODE GRADE"
                + "\nExample 1: c_adjust CS2103 A"
                + "\nIf the module code is NOT unique, include the YEAR and SEM: "
                + "c_adjust MODULE_CODE YEAR SEM GRADE"
                + "\nExample 2: c_adjust CS2103 1 1 A";
        argsWithBounds(tokenizedArgs, Set.of(2, 4), inputSuggestion);

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
