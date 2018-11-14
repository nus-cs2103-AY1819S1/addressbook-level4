package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACADEMICYEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.AcademicYearContainsKeywordsPredicate;
import seedu.address.model.module.ModuleCodeContainsKeywordsPredicate;
import seedu.address.model.module.ModuleTitleContainsKeywordsPredicate;
import seedu.address.model.module.SemesterContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindModuleCommand object
 */
public class FindModuleCommandParser implements Parser<FindModuleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindModuleCommand
     * and returns a new FindModuleCommand object of the given parameters.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULECODE, PREFIX_MODULETITLE,
                        PREFIX_ACADEMICYEAR, PREFIX_SEMESTER);
        if (!anyPrefixesPresent(argMultimap, PREFIX_MODULECODE, PREFIX_MODULETITLE, PREFIX_ACADEMICYEAR,
                PREFIX_SEMESTER) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindModuleCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_MODULECODE).isPresent()) {
            String[] moduleCodeKeyWords = argMultimap.getValue(PREFIX_MODULECODE).get().trim().split("\\s+");
            return new FindModuleCommand(new ModuleCodeContainsKeywordsPredicate(Arrays.asList(moduleCodeKeyWords)));
        } else if (argMultimap.getValue(PREFIX_MODULETITLE).isPresent()) {
            String[] moduleTitleKeyWords = argMultimap.getValue(PREFIX_MODULETITLE).get().trim().split("\\s+");
            return new FindModuleCommand(new ModuleTitleContainsKeywordsPredicate(Arrays.asList(moduleTitleKeyWords)));
        } else if (argMultimap.getValue(PREFIX_ACADEMICYEAR).isPresent()) {
            String[] academicYearKeyWords = argMultimap.getValue(PREFIX_ACADEMICYEAR).get().trim().split("\\s+");
            return new FindModuleCommand(
                    new AcademicYearContainsKeywordsPredicate(Arrays.asList(academicYearKeyWords)));
        } else {
            String[] semesterKeyWords = argMultimap.getValue(PREFIX_SEMESTER).get().trim().split("\\s+");
            return new FindModuleCommand(new SemesterContainsKeywordsPredicate(Arrays.asList(semesterKeyWords)));
        }

    }

    /**
     * Returns true if at least one of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
