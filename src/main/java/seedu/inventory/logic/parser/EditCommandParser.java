package seedu.inventory.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.EditCommand;
import seedu.inventory.logic.commands.EditCommand.EditItemDescriptor;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_QUANTITY, PREFIX_SKU,
                        PREFIX_IMAGE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditItemDescriptor editItemDescriptor = new EditCommand.EditItemDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editItemDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editItemDescriptor.setPrice(ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            editItemDescriptor.setQuantity(ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_SKU).isPresent()) {
            editItemDescriptor.setSku(ParserUtil.parseSku(argMultimap.getValue(PREFIX_SKU).get()));
        }
        if (argMultimap.getValue(PREFIX_IMAGE).isPresent()) {
            editItemDescriptor.setImage(ParserUtil.parseImage(argMultimap.getValue(PREFIX_IMAGE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editItemDescriptor::setTags);

        if (!editItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editItemDescriptor);
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
