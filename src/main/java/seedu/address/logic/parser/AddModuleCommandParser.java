package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACADEMICYEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleDescriptor;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;


/**
 * AddModuleCommandParser
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModuleCommand
     * and returns a new Module object of the given parameters.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULECODE, PREFIX_MODULETITLE, PREFIX_ACADEMICYEAR,
                        PREFIX_SEMESTER, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE));
        }

        ModuleDescriptor addModuleDescriptor = new ModuleDescriptor();

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULECODE).get());
        addModuleDescriptor.setModuleCode(moduleCode);

        if (argMultimap.getValue(PREFIX_MODULETITLE).isPresent()) {
            ModuleTitle moduleTitle = ParserUtil.parseModuleTitle(argMultimap.getValue(PREFIX_MODULETITLE).get());
            addModuleDescriptor.setModuleTitle(moduleTitle);
        }
        if (argMultimap.getValue(PREFIX_ACADEMICYEAR).isPresent()) {
            AcademicYear academicYear = ParserUtil.parseAcademicYear(argMultimap.getValue(PREFIX_ACADEMICYEAR).get());
            addModuleDescriptor.setAcademicYear(academicYear);
        }
        if (argMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            Semester semester = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());
            addModuleDescriptor.setSemester(semester);
        }
        UniquePersonList students = new UniquePersonList(new ArrayList<>());
        parseTagsForAdd(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(addModuleDescriptor::setTags);

        Module module = new Module(addModuleDescriptor);
        return new AddModuleCommand(module);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForAdd(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
