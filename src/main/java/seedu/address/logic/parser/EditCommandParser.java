package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.SimpleParseException;
import seedu.address.logic.parser.tokenizer.ArgumentMultimap;
import seedu.address.logic.parser.tokenizer.exceptions.TokenizationException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws SimpleParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws SimpleParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap;
        try {
            argMultimap =
                ArgumentTokenizer
                    .tokenize(args, PREFIX_NAME, PREFIX_PRIORITY, PREFIX_FREQUENCY,
                        PREFIX_DEADLINE, PREFIX_TAG);
        } catch (TokenizationException ime) {
            throw new SimpleParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), ime);
        }
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (SimpleParseException pe) {
            throw new SimpleParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditTaskDescriptor editTaskDescriptor = new EditCommand.EditTaskDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editTaskDescriptor
                    .setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editTaskDescriptor
                    .setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }
        if (argMultimap.getValue(PREFIX_FREQUENCY).isPresent()) {
            editTaskDescriptor
                    .setFrequency(ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQUENCY).get()));
        }
        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            editTaskDescriptor
                    .setDeadline(ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG))
                .ifPresent(editTaskDescriptor::setTags);

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new SimpleParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws SimpleParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet =
                tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
