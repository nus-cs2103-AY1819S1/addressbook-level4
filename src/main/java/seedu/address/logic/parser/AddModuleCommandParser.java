package seedu.address.logic.parser;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import static seedu.address.logic.parser.ParserUtil.argsAreNameValuePair;
import static seedu.address.logic.parser.ParserUtil.argsWithBounds;
import static seedu.address.logic.parser.ParserUtil.targetCodeNotNull;
import static seedu.address.logic.parser.ParserUtil.targetYearNullIffTargetSemesterNull;
import static seedu.address.logic.parser.ParserUtil.validateName;

import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.logic.parser.arguments.AddArgument;
import seedu.address.logic.parser.arguments.DeleteArgument;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credit;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

//@@author alexkmj
/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {
    /**
     * Message that informs that the command is in a wrong format and
     * prints the usage for delete command.
     */
    public static final String MESSAGE_INVALID_FORMAT =
            ParserUtil.MESSAGE_INVALID_FORMAT
                    + "\n"
                    + DeleteModuleCommand.MESSAGE_USAGE;

    /**
     * Immutable map that maps string argument to edit argument enum.
     */
    private static final Map<String, AddArgument> NAME_TO_ARGUMENT_MAP;

    /**
     * Immutable set containing the allowable size of arguments.
     */
    private static final Set<Integer> ALLOWED_ARG_SIZE;

    static {
        Map<String, AddArgument> map = new HashMap<>();
        for (AddArgument instance : AddArgument.values()) {
            map.put(instance.getShortName(), instance);
            map.put(instance.getLongName(), instance);
        }
        NAME_TO_ARGUMENT_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * Populate {@code ALLOWED_ARG_SIZE} with a set of numbers representing the
     * allowed argument sizes.
     */
    static {
        ALLOWED_ARG_SIZE = IntStream.range(8, 11)
                .filter(index -> index % 2 == 0)
                .boxed()
                .collect(collectingAndThen(toSet(), ImmutableSet::copyOf));
    }

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
    public AddModuleCommand parse(String argsInString)
            throws ParseException {
        // Converts argument string to tokenize argument array.
        String[] args = ParserUtil.tokenize(argsInString);

        // Size of argument should be 8 or 10.
        // Arguments should be in name-value pair.
        // Name should be legal.
        // No duplicate name.
        argsWithBounds(args, ALLOWED_ARG_SIZE);
        argsAreNameValuePair(args, MESSAGE_INVALID_FORMAT);
        validateName(args, NAME_TO_ARGUMENT_MAP, MESSAGE_INVALID_FORMAT);

        // Map the object of the parsed value to {@code AddArgument}
        // instance.
        // Code, Year, Semeter, and Credit should not be null.
        EnumMap<AddArgument, Object> argMap = parseValues(args);
        onlyGradeCanBeNull(argMap);
        Module newModule = getModuleWithAddArgMap(argMap);

        // Return delete module command for execution.
        return new AddModuleCommand(newModule);
    }

    /**
     * Parse the value into its relevant object and put it in {@code argMap}.
     *
     * @param args array of name-value pair arguments
     * @throws ParseException thrown when the value cannot be parsed
     */
    private EnumMap<AddArgument, Object> parseValues(String[] args)
            throws ParseException {
        // Initialise argument map.
        EnumMap<AddArgument, Object> argMap =
                new EnumMap<>(AddArgument.class);

        for (int index = 0; index < args.length; index = index + 2) {
            AddArgument name = NAME_TO_ARGUMENT_MAP.get(args[index]);
            Object value = name.getValue(args[index + 1]);
            argMap.put(name, value);
        }

        return argMap;
    }

    private static void onlyGradeCanBeNull(EnumMap<AddArgument, Object> argMap)
            throws ParseException {
        boolean codeIsNull = argMap.get(AddArgument.NEW_CODE) == null;
        boolean yearIsNull = argMap.get(AddArgument.NEW_YEAR) == null;
        boolean semesterIsNull = argMap.get(AddArgument.NEW_SEMESTER) == null;
        boolean creditIsNull = argMap.get(AddArgument.NEW_CREDIT) == null;

        if (codeIsNull || yearIsNull || semesterIsNull || creditIsNull) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }
    }

    /**
     * Creates and returns a module instance based on {@code argMap}.
     *
     * @param argMap add argument map used to create module instance
     * @return module instance based on {@code argMap}
     */
    private static Module getModuleWithAddArgMap(
            EnumMap<AddArgument, Object> argMap) {
        Code code = (Code) argMap.get(AddArgument.NEW_CODE);
        Year year = (Year) argMap.get(AddArgument.NEW_YEAR);
        Semester semester = (Semester) argMap.get(AddArgument.NEW_SEMESTER);
        Credit credit = (Credit) argMap.get(AddArgument.NEW_CREDIT);
        Grade grade = (Grade) argMap.get(AddArgument.NEW_GRADE);

        if (grade == null) {
            return new Module(code, year, semester, credit, null, false);
        }

        return new Module(code, year, semester, credit, grade, true);
    }
}
