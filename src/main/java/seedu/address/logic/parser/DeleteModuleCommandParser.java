package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.argsAreNameValuePair;
import static seedu.address.logic.parser.ParserUtil.argsWithBounds;
import static seedu.address.logic.parser.ParserUtil.targetCodeNotNull;
import static seedu.address.logic.parser.ParserUtil.targetYearNullIffTargetSemesterNull;
import static seedu.address.logic.parser.ParserUtil.validateName;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;

import java.util.Map;

import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.logic.parser.arguments.DeleteArgument;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author alexkmj
/**
 * {@code DeleteModuleCommandParser} parses input arguments for
 * {@code DeleteModuleCommand}.
 */
public class DeleteModuleCommandParser implements Parser<DeleteModuleCommand> {
    /**
     * Message that informs that the target code is required and prints the
     * usage for delete command.
     */
    public static final String MESSAGE_TARGET_CODE_REQUIRED =
            ParserUtil.MESSAGE_TARGET_CODE_REQUIRED
                    + "\n"
                    + DeleteModuleCommand.MESSAGE_USAGE;

    /**
     * Message that informs that target year has to be specified if and only if
     * semester is specified, and prints the usage for delete command.
     */
    public static final String MESSAGE_YEAR_AND_SEMESTER_XOR_NULL =
            ParserUtil.MESSAGE_YEAR_AND_SEMESTER_XOR_NULL
                    + "\n"
                    + DeleteModuleCommand.MESSAGE_USAGE;

    /**
     * Message that informs that the command is in a wrong format and
     * prints the usage for delete command.
     */
    public static final String MESSAGE_INVALID_FORMAT =
            ParserUtil.MESSAGE_INVALID_FORMAT
                    + "\n"
                    + DeleteModuleCommand.MESSAGE_USAGE;

    // Immutable map that maps string argument to edit argument enum.
    private static final Map<String, DeleteArgument> NAME_TO_ARGUMENT_MAP;
    static {
        Map<String, DeleteArgument> map = new HashMap<>();
        for (DeleteArgument instance : DeleteArgument.values()) {
            map.put(instance.getShortName(), instance);
            map.put(instance.getLongName(), instance);
        }
        NAME_TO_ARGUMENT_MAP = Collections.unmodifiableMap(map);
    }

    // Map object to argument.
    private EnumMap<DeleteArgument, Object> argMap;

    /**
     * Parses {@code args} in the context of {@code DeleteModuleCommand} and
     * returns {@code DeleteModuleCommand} for execution.
     * <p>
     * Throws {@code ParseException} when:
     * <ul>
     *     <li>Number of argument is not between 2 and 6.</li>
     *     <li>Number of argument is not even.</li>
     *     <li>Argument is not in name-value pair format</li>
     *     <li>Argument contains illegal name</li>
     *     <li>Same name appeared more than once</li>
     *     <li>Target code is not provided.</li>
     *     <li>Target year is provided but target semester is not provided.</li>
     *     <li>Target semester is provided but target year is not provided.</li>
     * </ul>
     *
     * @param argsInString String that contains all the argument
     * @return {@code EditModuleCommand} object for execution
     * @throws ParseException thrown when user input does not conform to the
     * expected format
     */
    public DeleteModuleCommand parse(String argsInString)
            throws ParseException {
        // Setup argument map.
        argMap = new EnumMap<>(DeleteArgument.class);
        argMap.put(DeleteArgument.TARGET_CODE, null);
        argMap.put(DeleteArgument.TARGET_YEAR, null);
        argMap.put(DeleteArgument.TARGET_SEMESTER, null);

        // Converts argument string to tokenize argument array.
        String[] args = ParserUtil.tokenize(argsInString);

        // Size of argument should be between 2 to 6.
        // Size of argument should be even.
        // Arguments should be in name-value pair.
        // Name should be legal.
        // No duplicate name.
        argsWithBounds(args, 2, 6);
        argsAreNameValuePair(args, MESSAGE_INVALID_FORMAT);
        validateName(args, NAME_TO_ARGUMENT_MAP, MESSAGE_INVALID_FORMAT);

        // Parse values.
        parseValues(args);

        // Target code should not be null.
        // Target year is null if and only if target semester is null.
        targetCodeNotNull(argMap.get(DeleteArgument.TARGET_CODE),
                MESSAGE_TARGET_CODE_REQUIRED);
        targetYearNullIffTargetSemesterNull(argMap.get(DeleteArgument.TARGET_YEAR),
                argMap.get(DeleteArgument.TARGET_SEMESTER),
                MESSAGE_YEAR_AND_SEMESTER_XOR_NULL);

        // Return delete module command for execution.
        return new DeleteModuleCommand(argMap);
    }

    /**
     * Parse the value into its relevant object and put it in {@code argMap}.
     *
     * @param args array of name-value pair arguments
     * @throws ParseException thrown when the value cannot be parsed
     */
    private void parseValues(String[] args) throws ParseException {
        for (int index = 0; index < args.length; index = index + 2) {
            DeleteArgument name = NAME_TO_ARGUMENT_MAP.get(args[index]);
            Object value = name.getValue(args[index + 1]);
            argMap.put(name, value);
        }
    }
}
