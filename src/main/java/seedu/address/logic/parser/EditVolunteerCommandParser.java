package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditVolunteerCommand;
import seedu.address.logic.commands.EditVolunteerCommand.EditVolunteerDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditVolunteerCommand object
 */
public class EditVolunteerCommandParser implements Parser<EditVolunteerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditVolunteerCommand
     * and returns an EditVolunteerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditVolunteerCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_VOLUNTEER_NAME, PREFIX_VOLUNTEER_GENDER, PREFIX_VOLUNTEER_BIRTHDAY,
                        PREFIX_VOLUNTEER_PHONE, PREFIX_VOLUNTEER_EMAIL, PREFIX_VOLUNTEER_ADDRESS, PREFIX_VOLUNTEER_TAG);

        Index index;

        try {
            index = ParserVolunteerUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditVolunteerCommand.MESSAGE_USAGE), pe);
        }

        EditVolunteerDescriptor editVolunteerDescriptor = new EditVolunteerDescriptor();
        if (argMultimap.getValue(PREFIX_VOLUNTEER_NAME).isPresent()) {
            editVolunteerDescriptor.setName(ParserVolunteerUtil.parseName(argMultimap.getValue(PREFIX_VOLUNTEER_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_VOLUNTEER_GENDER).isPresent()) {
            editVolunteerDescriptor.setGender(ParserVolunteerUtil.parseGender(argMultimap.getValue(PREFIX_VOLUNTEER_GENDER).get()));
        }
        if (argMultimap.getValue(PREFIX_VOLUNTEER_BIRTHDAY).isPresent()) {
            editVolunteerDescriptor.setBirthday(ParserVolunteerUtil.parseBirthday(argMultimap.getValue(PREFIX_VOLUNTEER_BIRTHDAY).get()));
        }
        if (argMultimap.getValue(PREFIX_VOLUNTEER_PHONE).isPresent()) {
            editVolunteerDescriptor.setPhone(ParserVolunteerUtil.parsePhone(argMultimap.getValue(PREFIX_VOLUNTEER_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_VOLUNTEER_EMAIL).isPresent()) {
            editVolunteerDescriptor.setEmail(ParserVolunteerUtil.parseEmail(argMultimap.getValue(PREFIX_VOLUNTEER_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_VOLUNTEER_ADDRESS).isPresent()) {
            editVolunteerDescriptor.setAddress(ParserVolunteerUtil.parseAddress(argMultimap.getValue(PREFIX_VOLUNTEER_ADDRESS).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_VOLUNTEER_TAG)).ifPresent(editVolunteerDescriptor::setTags);

        if (!editVolunteerDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditVolunteerCommand.MESSAGE_NOT_EDITED);
        }

        return new EditVolunteerCommand(index, editVolunteerDescriptor);
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
        return Optional.of(ParserVolunteerUtil.parseTags(tagSet));
    }

}
