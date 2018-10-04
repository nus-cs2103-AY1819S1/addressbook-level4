package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY_VALUE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Label;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DUE_DATE,
                    PREFIX_PRIORITY_VALUE, PREFIX_DESCRIPTION, PREFIX_LABEL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditTaskDescriptor editTaskDescriptor = new EditCommand.EditTaskDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editTaskDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DUE_DATE).isPresent()) {
            editTaskDescriptor.setDueDate(ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_DUE_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_PRIORITY_VALUE).isPresent()) {
            editTaskDescriptor.setPriorityValue(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_PRIORITY_VALUE)
                .get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editTaskDescriptor.setDescription(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        parseLabelsForEdit(argMultimap.getAllValues(PREFIX_LABEL)).ifPresent(editTaskDescriptor::setLabels);

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> labels} into a {@code Set<Label>} if {@code labels} is non-empty.
     * If {@code labels} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Label>} containing zero labels.
     */
    private Optional<Set<Label>> parseLabelsForEdit(Collection<String> labels) throws ParseException {
        assert labels != null;

        if (labels.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> labelSet = labels.size() == 1 && labels.contains("") ? Collections.emptySet() : labels;
        return Optional.of(ParserUtil.parseLabels(labelSet));
    }

}
