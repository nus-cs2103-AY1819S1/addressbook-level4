package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.PREFIX;
import static seedu.address.logic.parser.ParserUtil.parseException;

import java.util.Map;

import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credit;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

/**
 * {@code EditModuleCommandParser} parses input arguments for
 * {@code EditModuleCommand}.
 */
public class EditModuleCommandParser implements Parser<EditModuleCommand> {

    // Constants for ParseException.
    private static final String MESSAGE_TARGET_CODE_REQUIRED = "Target code"
            + " required.";
    private static final String MESSAGE_YEAR_AND_SEMESTER_XOR_NULL = "Year and"
            + " semester should be concurrently specified or not specified.";
    private static final String MESSAGE_NO_CHANGES = "Command does not cause"
            + " any changes. Not enough parameters specified.";

    // Fields for EditModuleCommand.
    private Code targetCode;
    private Year targetYear;
    private Semester targetSemester;
    private Code newCode;
    private Year newYear;
    private Semester newSemester;
    private Credit newCredit;
    private Grade newGrade;

    /**
     * Parses {@code args} in the context of {@code EditModuleCommand} and
     * returns {@code EditModuleCommand} for execution.
     * <p>
     * Throws {@code ParseException} when:
     * <ul>
     *     <li>Target code is not provided.</li>
     *     <li>Target year is provided but target semester is not provided.</li>
     *     <li>Target semester is provided but target year is not provided.</li>
     *     <li>No field is edited.</li>
     *     <li>Unable to parse any field.</li>
     * </ul>
     *
     * @param args argument string that contains all the arguments.
     * @return {@code EditModuleCommand} object for execution
     * @throws ParseException thrown when user input does not conform to the
     * expected format
     */
    public EditModuleCommand parse(String args) throws ParseException {
        // Converts argument string to tokenize argument array.
        String[] argArray = ParserUtil.tokenize(args);
        ParserUtil.validateNumOfArgs(argArray, 3, 13);

        // Parse arguments.
        parseTargetFields(argArray);
        parseNewFields(argArray);

        // Return edit module command for execution.
        return new EditModuleCommand(targetCode, targetYear, targetSemester,
                newCode, newYear, newSemester, newCredit, newGrade);
    }

    /**
     * Parses target fields and checks the validity of the command's format.
     * <p>
     * <ul>
     *     Parses the following target fields:
     *     <li>
     *         {@code targetCode}: required
     *     </li>
     *     <li>
     *         {@code targetYear}: required if {@code targetSemester} is not
     *         null
     *     </li>
     *     <li>
     *         {@code targetSemester}: required if {@code targetYear} is not
     *         null
     *     </li>
     * </ul>
     *
     * @param argArray tokenize argument from the command
     * @throws ParseException thrown if format of command is invalid or any of
     * the target field cannot be parsed
     */
    private void parseTargetFields(String[] argArray) throws ParseException {
        // Process target fields.
        targetCode = argArray[0].startsWith(PREFIX)
                ? null
                : ParserUtil.parseCode(argArray[0]);

        // Throw parse exception if target code is null.
        if (targetCode == null) {
            throw parseException(MESSAGE_TARGET_CODE_REQUIRED);
        }

        targetYear = argArray[1].startsWith(PREFIX)
                ? null
                : ParserUtil.parseYear(argArray[1]);

        if (targetYear == null) {
            targetSemester = null;
        } else {
            targetSemester = argArray[2].startsWith(PREFIX)
                    ? null
                    : ParserUtil.parseSemester(argArray[2]);
        }

        // Throw parse exception when:
        // 1) target year is not null but target semester is null
        // 2) target year is null but target semester is not null
        boolean xorNull = targetYear == null ^ targetSemester == null;
        if (xorNull) {
            throw parseException(MESSAGE_YEAR_AND_SEMESTER_XOR_NULL);
        }
    }

    /**
     * Maps tokenize argument to its key value pair and parses the new fields.
     * <p>
     * All fields are optional but there should be at least one edited field.
     *
     * @param argArray tokenize argument from the command
     * @throws ParseException thrown when field cannot be parsed
     */
    private void parseNewFields(String[] argArray) throws ParseException {
        Map<String, String> argsMap = ParserUtil.mapArgs(argArray);

        newCode = argsMap.containsKey("code")
                ? ParserUtil.parseCode(argsMap.get("code"))
                : null;

        newYear = argsMap.containsKey("year")
                ? ParserUtil.parseYear(argsMap.get("year"))
                : null;

        newSemester = argsMap.containsKey("semester")
                ? ParserUtil.parseSemester(argsMap.get("semester"))
                : null;

        newCredit = argsMap.containsKey("credit")
                ? ParserUtil.parseCredit(argsMap.get("credit"))
                : null;

        newGrade = argsMap.containsKey("grade")
                ? ParserUtil.parseGrade(argsMap.get("grade"))
                : null;

        if (newCode == null && newYear == null && newSemester == null
                && newCredit == null && newGrade == null) {
            throw parseException(MESSAGE_NO_CHANGES);
        }
    }
}
