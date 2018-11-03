package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACADEMICYEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleDescriptor;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditModuleCommand object
 */
public class EditModuleCommandParser implements Parser<EditModuleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditModuleCommand
     * and returns an EditModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULECODE, PREFIX_MODULETITLE, PREFIX_ACADEMICYEAR,
                        PREFIX_SEMESTER, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditModuleCommand.MESSAGE_USAGE), pe);
        }

        ModuleDescriptor editModuleDescriptor = new ModuleDescriptor();
        if (argMultimap.getValue(PREFIX_MODULECODE).isPresent()) {
            editModuleDescriptor.setModuleCode(ParserUtil.parseModuleCode(
                    argMultimap.getValue(PREFIX_MODULECODE).get()));
        }
        if (argMultimap.getValue(PREFIX_MODULETITLE).isPresent()) {
            editModuleDescriptor.setModuleTitle(ParserUtil.parseModuleTitle(
                    argMultimap.getValue(PREFIX_MODULETITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_ACADEMICYEAR).isPresent()) {
            editModuleDescriptor.setAcademicYear(ParserUtil.parseAcademicYear(
                    argMultimap.getValue(PREFIX_ACADEMICYEAR).get()));
        }
        if (argMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            editModuleDescriptor.setSemester(ParserUtil.parseSemester(
                    argMultimap.getValue(PREFIX_SEMESTER).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editModuleDescriptor::setTags);

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditModuleCommand.MESSAGE_NOT_EDITED);
        }

        return new EditModuleCommand(index, editModuleDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
