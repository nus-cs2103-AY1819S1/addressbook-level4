package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBHEADER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEntryInfoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * a parser to parse the editEntryInfo command to return a EditInfoCommand instance.
 */
public class EditEntryInfoCommandParser implements Parser<EditEntryInfoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEntryInfoCommand
     * and returns an EditEntryInfoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEntryInfoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_SUBHEADER, PREFIX_DURATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEntryInfoCommand.MESSAGE_USAGE), pe);
        }

        EditEntryInfoCommand.EditEntryInfoDescriptor editEntryInfoDescriptor =
                new EditEntryInfoCommand.EditEntryInfoDescriptor();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editEntryInfoDescriptor.setTitle(
                    ParserUtil.parseEntryInfoFields(argMultimap.getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_SUBHEADER).isPresent()) {
            editEntryInfoDescriptor.setSubtitle(
                    ParserUtil.parseEntryInfoFields(argMultimap.getValue(PREFIX_SUBHEADER).get()));
        }
        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editEntryInfoDescriptor.setDuration(
                    ParserUtil.parseEntryInfoFields(argMultimap.getValue(PREFIX_DURATION).get()));
        }

        if (!editEntryInfoDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEntryInfoCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEntryInfoCommand(index, editEntryInfoDescriptor);
    }



}
